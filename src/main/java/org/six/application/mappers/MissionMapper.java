package org.six.application.mappers;

import org.six.application.dto.MissionDTO;
import org.six.domain.model.Mission;

public interface MissionMapper {
    Mission toDomain(MissionDTO dto);

    MissionDTO toDto(Mission domain);
}

class DefaultMissionMapper implements MissionMapper {
    @Override
    public Mission toDomain(MissionDTO dto) {
        return null;
    }

    @Override
    public MissionDTO toDto(Mission domain) {
        return null;
    }
}