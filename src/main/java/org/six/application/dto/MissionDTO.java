package org.six.application.dto;

import org.six.domain.model.MissionStatus;

public record MissionDTO(
        String name,
        MissionStatus status
) {
}
