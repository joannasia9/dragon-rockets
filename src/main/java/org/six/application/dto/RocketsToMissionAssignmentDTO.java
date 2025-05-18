package org.six.application.dto;


import java.util.Set;

public record RocketsToMissionAssignmentDTO(
        String missionName,
        Set<SimpleRocketDTO> rockets
) {
}
