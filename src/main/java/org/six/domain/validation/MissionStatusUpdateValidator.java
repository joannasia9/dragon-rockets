package org.six.domain.validation;

import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.RocketStatus;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import java.util.List;
import java.util.Optional;

public class MissionStatusUpdateValidator {
    private final RocketRepository rocketRepository;
    private final RocketToMissionAssignmentRepository assignmentRepository;


    public MissionStatusUpdateValidator(RocketRepository rocketRepository,
                                        RocketToMissionAssignmentRepository assignmentRepository) {
        this.rocketRepository = rocketRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public boolean isValidUpdate(Mission currentMission, MissionStatus newStatus) {
        if (List.of(currentMission.status(), MissionStatus.ENDED).contains(newStatus))
            return true;

        var assignedRockets = assignmentRepository.findRocketsFor(currentMission.name())
                .stream()
                .map(rocketRepository::findByName)
                .flatMap(Optional::stream)
                .toList();
        var hasAnyRocketInRepair = assignedRockets.stream().anyMatch(r -> r.status() == RocketStatus.IN_REPAIR);

        return switch (newStatus) {
            case MissionStatus.SCHEDULED -> assignedRockets.isEmpty();
            case MissionStatus.IN_PROGRESS -> !assignedRockets.isEmpty() && !hasAnyRocketInRepair;
            case MissionStatus.PENDING -> !assignedRockets.isEmpty() && hasAnyRocketInRepair;
            default -> false;
        };
    }
}
