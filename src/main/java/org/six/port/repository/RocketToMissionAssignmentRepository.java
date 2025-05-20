package org.six.port.repository;

import java.util.List;
import java.util.Optional;

public interface RocketToMissionAssignmentRepository {
    boolean isAssignedToMission(String rocketName);

    Optional<String> findMissionFor(String rocketName);

    List<String> findRocketsFor(String missionName);

    void insertAssignment(String rocketName, String missionName);
    void removeRocketsAssignedTo(String missionName);
}
