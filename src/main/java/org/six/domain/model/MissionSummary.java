package org.six.domain.model;

import java.util.List;

public record MissionSummary(
        String missionName,
        MissionStatus missionStatus,
        int numberOfRocketsAssigned,
        List<Rocket> rockets
) {
}
