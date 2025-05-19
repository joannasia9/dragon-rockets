package org.six.application.mappers;

import org.six.application.dto.MissionDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.domain.model.Mission;

import java.util.ArrayList;

public interface MissionMapper {
    Mission toDomain(MissionDTO dto);

    Mission toDomain(NewMissionDTO dto);

    MissionDTO toDto(Mission domain);
}

class DefaultMissionMapper implements MissionMapper {
    @Override
    public Mission toDomain(MissionDTO dto) {
        return new Mission(
                dto.name(),
                dto.status(),
                new ArrayList<>()
        );
    }

    @Override
    public Mission toDomain(NewMissionDTO dto) {
        return Mission.withDefaultStatus(dto.name());
    }

    @Override
    public MissionDTO toDto(Mission domain) {
        return new MissionDTO(domain.name(), domain.status());
    }
}