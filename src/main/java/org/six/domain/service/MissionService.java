package org.six.domain.service;

import org.six.domain.model.Mission;
import org.six.domain.model.Rocket;


public interface MissionService {
    void addNewMission(Mission mission);

    void changeMissionStatus(Mission missionWithNewStatus);

    void changeMissionStatusIfNeeded(Rocket rocketWithNewStatus);

    void changeMissionStatusIfNeeded(String missionName);
}


