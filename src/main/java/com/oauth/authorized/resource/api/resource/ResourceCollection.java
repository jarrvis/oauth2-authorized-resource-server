package com.oauth.authorized.resource.api.resource;

import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;

/**
 * Base resource containing common resource data
 *
 * @author Michał Kaliszewski (hititfr0mthe6ack@gmail.com)
 *
 */
public class ResourceCollection<T> extends ResourceSupport {

    private Collection<T> data;

    public ResourceCollection(Collection<T> data) {
        this.data = data;
    }

    public Collection<T> getData() {
        return data;
    }
    
}
