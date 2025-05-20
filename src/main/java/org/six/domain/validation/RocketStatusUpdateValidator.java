package org.six.domain.validation;

import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.port.repository.RocketToMissionAssignmentRepository;

public class RocketStatusUpdateValidator {
    private final RocketToMissionAssignmentRepository rocketToMissionAssignmentRepository;

    public RocketStatusUpdateValidator(RocketToMissionAssignmentRepository rocketToMissionAssignmentRepository) {
        this.rocketToMissionAssignmentRepository = rocketToMissionAssignmentRepository;
    }

    public boolean isValidUpdate(Rocket rocketWithNewStatus) {
        var isAssignedToAMission = rocketToMissionAssignmentRepository.isAssignedToMission(rocketWithNewStatus.name());
        var newStatusOnGround = rocketWithNewStatus.status() == RocketStatus.ON_GROUND;
        var newStatusInSpace = rocketWithNewStatus.status() == RocketStatus.IN_SPACE;

        return (!newStatusOnGround || !isAssignedToAMission) && (isAssignedToAMission || !newStatusInSpace);
    }
}
