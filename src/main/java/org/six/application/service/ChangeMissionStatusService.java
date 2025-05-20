package org.six.application.service;

import org.six.application.dto.MissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.mappers.MissionMapper;
import org.six.application.mappers.RocketMapper;
import org.six.domain.service.MissionService;

public class ChangeMissionStatusService {
    private final MissionMapper missionMapper;
    private final MissionService missionService;
    private final RocketMapper rocketMapper;

    public ChangeMissionStatusService(MissionMapper missionMapper,
                                      MissionService missionService,
                                      RocketMapper rocketMapper) {
        this.missionMapper = missionMapper;
        this.missionService = missionService;
        this.rocketMapper = rocketMapper;
    }

    public void execute(MissionDTO missionWithNewStatus) {
        var mission = missionMapper.toDomain(missionWithNewStatus);
        missionService.changeMissionStatus(mission);
    }

    public void execute(RocketDTO rocketWithNewStatus) {
        var rocket = rocketMapper.toDomain(rocketWithNewStatus);
        missionService.changeMissionStatusIfNeeded(rocket);
    }

    public void execute(String missionName) {
        missionService.changeMissionStatusIfNeeded(missionName);
    }
}
