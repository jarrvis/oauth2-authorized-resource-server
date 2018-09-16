package com.oauth.authorized.resource.configuration;

import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSConfigurationException;
import com.thetransactioncompany.cors.CORSFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.Properties;

@Configuration
@Slf4j
public class FilterConfig {

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public CORSFilter corsFilter() {

        Properties properties = new Properties();

        properties.setProperty("cors.allowGenericHttpRequests", "true");
        properties.setProperty("cors.allowOrigin", "*");
        properties.setProperty("cors.supportedMethods", "true");
        properties.setProperty("cors.GET, HEAD, POST, DELETE, OPTIONS", "true");
        properties.setProperty("cors.supportedHeaders", "*");
        properties.setProperty("cors.supportsCredentials", "true");
        properties.setProperty("cors.maxAge", "3600");


        try {
            return new CORSFilter(
                    new CORSConfiguration(properties)
            );
        } catch (CORSConfigurationException e) {
            log.error("Error initializing CORSFilter", e);

            return null;
        }
    }

}
