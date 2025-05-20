package org.six.domain.exception;

import org.six.domain.model.RocketStatus;

public class RocketStatusChangeNotAllowedException extends RuntimeException {
    public RocketStatusChangeNotAllowedException(String rocketName, RocketStatus newStatus) {
        super("Status update to %s for rocket: %s is not permitted.".formatted(newStatus, rocketName));
    }
}
