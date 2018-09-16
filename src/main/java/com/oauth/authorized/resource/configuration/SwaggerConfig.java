package com.oauth.authorized.resource.configuration;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Main configuration class to enable the Swagger UI frontend. It registers all supported controllers and describes the
 * API.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@Configuration
@EnableSwagger2
//@ComponentScan(basePackageClasses = { UserController.class})
public class SwaggerConfig {

    // Swagger UI
    public static final String SWAGGER_UI_ENDPOINT = "/swagger-ui.html";
    public static final String SWAGGER_RESOURCES_ENDPOINT = "/swagger-resources";
    public static final String SWAGGER_API_DOCS_ENDPOINT = "/v2/api-docs";

    public static final String API_DEV_CONTACT_MAIL = "panda.easyparking@gmail.com";

    @Value("${info.build.version}")
    private String apiVersion;
    @Value("${info.build.name}")
    private String apiTitle;
    @Value("${info.build.artifact}")
    private String apiGroupName;
    @Value("${appconfig.clientId}")
    private String clientId;
    @Value("${appconfig.clientSecret}")
    private String clientSecret;

    private List<String> securedPaths = newArrayList("/api/user", "/api/user/all");


    @Bean
    public Docket easyParkingApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName(apiGroupName).useDefaultResponseMessages(false)
                .apiInfo(apiInfo()).select().paths(paths()).build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private SecurityScheme securityScheme() {
        List<GrantType> grantTypes = newArrayList();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant("/oauth/token");
        grantTypes.add(passwordCredentialsGrant);

        return new OAuthBuilder().name("spring_oauth")
                .grantTypes(grantTypes)
                .scopes(Arrays.asList(scopes()))
                .build();
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(path ->
                        securedPaths.stream().anyMatch(securedPath -> PathSelectors.regex(securedPath).apply(path))
                )
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("easy_parking", "Access foo API") };
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration
                ("client", "secret", "", "", "Bearer access token", ApiKeyVehicle.HEADER, HttpHeaders.AUTHORIZATION,"");
    }

    @SuppressWarnings("unchecked")
    private Predicate<String> paths() {
        return or( regex("/.*user.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiTitle)
                .description(
                        "The Easy Parking API Service provides authorization, authentication and access to resources for product customers and partners."
                                + "It is the interface to the Easy Parking running on mobile and in the browser.")
                .contact(new Contact(apiTitle, null, API_DEV_CONTACT_MAIL))
                .termsOfServiceUrl("")
                .version(apiVersion)
                .build();
    }

}
