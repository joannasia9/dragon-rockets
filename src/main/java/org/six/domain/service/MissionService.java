package org.six.domain.service;

import org.six.domain.model.MissionStatus;

public interface MissionService {
    void addNewMission(String missionName, String... rockets);

    void changeMissionStatus(String missionName, MissionStatus newStatus);
}
