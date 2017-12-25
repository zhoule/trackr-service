package com.xplusz.trackr.exceptions;

/**
 *
 * Base class exception for entities not found. Throw this exception when there is no specific "not found" exception for the
 * desire entity.
 *
 */
@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName) {
        super("Could not find " + entityName);
    }
}
