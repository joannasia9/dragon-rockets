package org.six.domain.service;

import org.six.domain.model.Mission;
import org.six.domain.model.Rocket;

import java.util.Set;

public interface RocketAssignmentService {
    void removeRocketsAssignmentForMissionIfNeeded(Mission mission);

    void assignRocketsToMission(String missionName, Set<Rocket> rocketsToBeAssigned);
}

