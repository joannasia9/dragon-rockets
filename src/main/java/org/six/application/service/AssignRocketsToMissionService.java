package org.six.application.service;

import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.mappers.RocketMapper;
import org.six.domain.service.RocketAssignmentService;

import java.util.stream.Collectors;

public class AssignRocketsToMissionService {
    private final RocketMapper rocketMapper;
    private final RocketAssignmentService rocketAssignmentService;

    public AssignRocketsToMissionService(RocketMapper rocketMapper,
                                         RocketAssignmentService rocketAssignmentService) {
        this.rocketMapper = rocketMapper;
        this.rocketAssignmentService = rocketAssignmentService;
    }

    public void execute(RocketsToMissionAssignmentDTO assignment) {
        var rockets = assignment.rockets()
                .stream()
                .map(rocketMapper::toDomain)
                .collect(Collectors.toSet());

        rocketAssignmentService.assignRocketsToMission(assignment.missionName(), rockets);
    }
}
