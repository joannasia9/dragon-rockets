package org.six.application.mappers;

import org.six.application.dto.RocketDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.domain.model.Rocket;

public interface RocketMapper {
    Rocket toDomain(RocketDTO dto);

    Rocket toDomain(SimpleRocketDTO dto);

    RocketDTO toDto(Rocket domain);
}

class DefaultRocketMapper implements RocketMapper {
    @Override
    public Rocket toDomain(RocketDTO dto) {
        return new Rocket(dto.name(), dto.status());
    }

    @Override
    public Rocket toDomain(SimpleRocketDTO dto) {
        return Rocket.withDefaultStatus(dto.name());
    }

    @Override
    public RocketDTO toDto(Rocket domain) {
        return new RocketDTO(domain.name(), domain.status());
    }
}
