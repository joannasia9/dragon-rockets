package org.six.application.mappers;

import org.six.application.dto.RocketDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.domain.model.Rocket;

public interface RocketMapper {
    Rocket toDomain(RocketDTO dto);

    Rocket toDomain(SimpleRocketDTO dto);

    RocketDTO toDto(Rocket domain);
}

