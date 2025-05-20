package org.six.domain.exception;

public class RocketAlreadyExistsException extends RuntimeException {
    public RocketAlreadyExistsException(String rocketName) {
        super("Rocket with provided name: %s already exists.".formatted(rocketName));
    }
}
