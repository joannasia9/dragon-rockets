package org.six.domain.exception;

import org.six.domain.model.MissionStatus;

public class MissionStatusChangeNotAllowedException extends RuntimeException {
    public MissionStatusChangeNotAllowedException(String missionName, MissionStatus newStatus) {
        super("Status update to %s for mission: %s is not permitted.".formatted(newStatus, missionName));
    }
}
