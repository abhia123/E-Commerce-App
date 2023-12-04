package com.bikkadit.electronic.store.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {

        super(message);
    }

}
