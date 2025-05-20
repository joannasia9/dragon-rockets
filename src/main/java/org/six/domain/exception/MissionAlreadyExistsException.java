package org.six.domain.exception;

public class MissionAlreadyExistsException extends RuntimeException {
    public MissionAlreadyExistsException(String missionName) {
        super("Mission with provided name: %s already exists.".formatted(missionName));
    }
}
