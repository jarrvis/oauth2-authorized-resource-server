package com.oauth.authorized.resource.api.converters;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Mapper factory. Should stay untouched. It's only to have it registered as a bean
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@Component
public class DataMapperFactory {

    @Bean
    public MapperFactory getMapperFactory (){
        return new DefaultMapperFactory.Builder().build();
    }
}
