package org.six.domain.exception;

public class RocketAlreadyAssignedException extends RuntimeException {
    public RocketAlreadyAssignedException(String rocketName) {
        super("Rocket with provided name: %s already assigned to a mission.".formatted(rocketName));
    }

}
