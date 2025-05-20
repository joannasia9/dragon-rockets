package org.six.domain.service;

import org.six.domain.exception.MissionAlreadyExistsException;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.port.repository.MissionRepository;

public interface MissionService {
    void addNewMission(Mission mission);

    void changeMissionStatus(String missionName, MissionStatus newStatus);
}

class DefaultMissionService implements MissionService {
    private final MissionRepository missionRepository;

    DefaultMissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }


    @Override
    public void addNewMission(Mission mission) {
        if (missionRepository.existsByName(mission.name())) {
            throw new MissionAlreadyExistsException(mission.name());
        }
        missionRepository.insert(mission);
    }

    @Override
    public void changeMissionStatus(String missionName, MissionStatus newStatus) {
        // TODO: Implement
    }
}
