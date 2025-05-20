package org.six.domain.exception;

public class RocketToMissionAssignmentNotAllowedException extends RuntimeException {
    public RocketToMissionAssignmentNotAllowedException(String missionName) {
        super("Rocket assignment to mission %s is not allowed.".formatted(missionName));
    }
}
