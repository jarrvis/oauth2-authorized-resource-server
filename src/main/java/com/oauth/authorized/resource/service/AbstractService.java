package com.oauth.authorized.resource.service;

import com.oauth.authorized.resource.configuration.ApplicationConfigurationProperties;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract base service handling common services actions and to inject common beans for services
 *
 * @author Michał Kaliszewski
 *
 */
public abstract class AbstractService {

    ApplicationConfigurationProperties configurationProperties;

    MapperFacade mapperFacade;

    @Autowired
    public void setConfigurationProperties(ApplicationConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    @Autowired
    public void setMapperFacade(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }
}
