package org.six.domain.exception;

public class MissionNotExistsException extends RuntimeException {
    public MissionNotExistsException(String missionName) {
        super("Mission with provided name: %s does not exist.".formatted(missionName));
    }

}
