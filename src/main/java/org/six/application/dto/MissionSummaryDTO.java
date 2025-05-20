package org.six.application.dto;

import org.six.domain.model.MissionStatus;

import java.util.List;

public record MissionSummaryDTO(
        String missionName,
        MissionStatus missionStatus,
        int numberOfRocketsAssigned,
        List<RocketDTO> rockets
) {
}
