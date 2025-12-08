package com.souhail.ogc_nice_api.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(Long id) {
        super("Team with id " + id + " not found");
    }
}
