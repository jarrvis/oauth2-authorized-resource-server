package com.oauth.authorized.resource.api.converters;

import com.oauth.authorized.resource.api.dto.*;
import com.oauth.authorized.resource.model.domain.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Mapper facade. Here custom mappers are registered
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
@Component
public class DataMapperFacade {

    @Resource
    private MapperFactory mapperFactory;

    @Bean
    public MapperFacade createMapperFacade() {
        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));   //since LocalDateTime has no zero-arg constructor

        mapperFactory.classMap(User.class, UserDto.class)
                .mapNulls(false)
                .mapNullsInReverse(false)
                .exclude("password")
                .byDefault()
                .register();

        return mapperFactory.getMapperFacade();
    }
}
