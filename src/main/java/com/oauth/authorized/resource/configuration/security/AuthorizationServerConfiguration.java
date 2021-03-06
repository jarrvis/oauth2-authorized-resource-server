package com.oauth.authorized.resource.configuration.security;

import com.oauth.authorized.resource.configuration.ApplicationConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Authorization server configuration.
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final ApplicationConfigurationProperties properties;

    public AuthorizationServerConfiguration(
                                            JwtAccessTokenConverter jwtAccessTokenConverter,
                                            ApplicationConfigurationProperties properties,
                                            PasswordEncoder passwordEncoder,
                                            AuthenticationManager authenticationManager) {

        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.properties = properties;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new JwtTokenStore(jwtAccessTokenConverter))
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(properties.getClientId())
                .secret(passwordEncoder.encode(properties.getClientSecret()))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write");
    }

}
