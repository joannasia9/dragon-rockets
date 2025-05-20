package org.six.application.dto;


import org.six.domain.model.RocketStatus;

public record RocketDTO(
        String name,
        RocketStatus status
) {
}
