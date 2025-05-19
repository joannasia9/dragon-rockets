package org.six.domain.exception;

public class RocketNotExistsException extends RuntimeException {
    public RocketNotExistsException(String rocketName) {
        super("Rocket with provided name: %s does not exist.".formatted(rocketName));
    }
}
