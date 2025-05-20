package org.six.application.service;

import org.six.application.dto.NewMissionDTO;
import org.six.application.mappers.MissionMapper;
import org.six.domain.service.MissionService;

public class AddNewMissionService {
    private final MissionMapper missionMapper;
    private final MissionService missionService;

    public AddNewMissionService(MissionMapper missionMapper,
                                MissionService missionService) {
        this.missionMapper = missionMapper;
        this.missionService = missionService;
    }

    public void execute(NewMissionDTO mission) {
        var newMission = missionMapper.toDomain(mission);
        missionService.addNewMission(newMission);
    }
}