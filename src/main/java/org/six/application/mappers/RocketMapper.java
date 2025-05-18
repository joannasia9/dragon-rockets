package org.six.application.mappers;

import org.six.application.dto.RocketDTO;
import org.six.domain.model.Rocket;

public interface RocketMapper {
    Rocket toDomain(RocketDTO dto);

    RocketDTO toDto(Rocket domain);
}

class DefaultRocketMapper implements RocketMapper {
    @Override
    public Rocket toDomain(RocketDTO dto) {
        return null;
    }

    @Override
    public RocketDTO toDto(Rocket domain) {
        return null;
    }
}
