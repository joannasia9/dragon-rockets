package org.six.application.mappers;

import org.six.application.dto.MissionDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.domain.model.Mission;


public interface MissionMapper {
    Mission toDomain(MissionDTO dto);

    Mission toDomain(NewMissionDTO dto);

    MissionDTO toDto(Mission domain);
}
