package org.six.application.service;

import org.six.application.dto.MissionDTO;
import org.six.application.mappers.MissionMapper;
import org.six.domain.service.RocketAssignmentService;

public class RemoveRocketAssignmentService {
    private final MissionMapper missionMapper;
    private final RocketAssignmentService rocketAssignmentService;

    public RemoveRocketAssignmentService(MissionMapper missionMapper,
                                         RocketAssignmentService rocketAssignmentService) {
        this.missionMapper = missionMapper;
        this.rocketAssignmentService = rocketAssignmentService;
    }

    public void execute(MissionDTO missionWithNewStatus) {
        var mission = missionMapper.toDomain(missionWithNewStatus);
        rocketAssignmentService.removeRocketsAssignmentForMissionIfNeeded(mission);
    }
}
