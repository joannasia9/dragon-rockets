package org.six.application.mappers;

import org.junit.jupiter.api.Test;
import org.six.application.dto.MissionDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultMissionMapperTest {
    private final MissionMapper missionMapper = new DefaultMissionMapper();

    @Test
    void shouldProperlyMapDTOMissionToDomain() {
        //given
        var mission = new MissionDTO("M-1", MissionStatus.SCHEDULED);

        //when
        var result = missionMapper.toDomain(mission);

        //then
        assertEquals(mission.name(), result.name());
        assertEquals(mission.status(), result.status());
    }

    @Test
    void shouldProperlyMapDTONewMissionToDomain() {
        //given
        var mission = new NewMissionDTO("M-1");

        //when
        var result = missionMapper.toDomain(mission);

        //then
        assertEquals(mission.name(), result.name());
        assertEquals(MissionStatus.SCHEDULED, result.status());
    }

    @Test
    void shouldProperlyMapDomainMissionToDTO() {
        //given
        var mission = new Mission(
                "M-1",
                MissionStatus.SCHEDULED
        );

        //when
        var result = missionMapper.toDto(mission);

        //then
        assertEquals(mission.name(), result.name());
        assertEquals(mission.status(), result.status());
    }
}
