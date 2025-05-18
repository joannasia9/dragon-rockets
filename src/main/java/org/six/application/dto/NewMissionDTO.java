package org.six.application.dto;

import java.util.List;

public record NewMissionDTO(
        String name,
        List<SimpleRocketDTO> rockets
) {
}
