package com.oauth.authorized.resource.api.controller;

import com.google.common.primitives.Ints;
import com.oauth.authorized.resource.api.exception.NotAcceptableException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

/**
 * Abstract base controller handling common rest api controllers actions e.g collection sorting and pagination query preparation
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
abstract class BaseController {


    Optional<PageRequest> getPageRequestOptional(final String limit, final String sortBy, final Class clazz) {

        Optional<Sort> sort = Optional.empty();
        Optional<Integer> resultsLimit = Optional.empty();

        if (clazz != null && StringUtils.isNotEmpty(sortBy)) {
            final boolean reverseSort = sortBy.startsWith("-");
            final String sortByField = StringUtils.removeStart(sortBy,"-");
            sort = Optional.ofNullable(Arrays.stream(clazz.getDeclaredFields()).anyMatch(field -> field.getName().equals(sortByField)) ?
                    new Sort(reverseSort ? Sort.Direction.ASC : Sort.Direction.DESC, sortByField) : null);

            if (!sort.isPresent())
                throw new NotAcceptableException("Invalid sortBy parameter.");
        }

        if (StringUtils.isNotEmpty(limit)) {
            resultsLimit = Optional.ofNullable(Ints.tryParse(limit));
            if (!resultsLimit.isPresent())
                throw new NotAcceptableException("Invalid limit parameter.");
        }

        return Optional.ofNullable(sort.isPresent() && resultsLimit.isPresent() ?
                PageRequest.of(0, resultsLimit.get(), sort.get()) :
                (sort.isPresent() || resultsLimit.isPresent() ?
                        (sort.isPresent() ?
                                PageRequest.of(0, Integer.MAX_VALUE, sort.get()) :
                                PageRequest.of(0, resultsLimit.get())
                        ) : null));
    }

}