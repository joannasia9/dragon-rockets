package org.six.application.mappers;

import org.junit.jupiter.api.Test;
import org.six.application.dto.MissionDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(result.rockets().isEmpty());
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
        assertTrue(result.rockets().isEmpty());
    }

    @Test
    void shouldProperlyMapDomainMissionToDTO() {
        //given
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);
        var mission = new Mission(
                "M-1",
                MissionStatus.SCHEDULED,
                List.of(rocket)
        );

        //when
        var result = missionMapper.toDto(mission);

        //then
        assertEquals(mission.name(), result.name());
        assertEquals(mission.status(), result.status());
        assertEquals(1, mission.rockets().size());

        var resultRocket = mission.rockets().getFirst();
        assertEquals(rocket.name(), resultRocket.name());
        assertEquals(rocket.status(), resultRocket.status());
    }
}
