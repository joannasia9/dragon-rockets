package org.six.application.mappers;

import org.junit.jupiter.api.Test;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.MissionSummary;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultMissionSummaryMapperTest {
    private final RocketMapper rocketMapper = new DefaultRocketMapper();
    private final MissionSummaryMapper missionSummaryMapper = new DefaultMissionSummaryMapper(rocketMapper);

    @Test
    void shouldProperlyMapDomainSummaryToDTO() {
        // given
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);
        var missionSummary = new MissionSummary(
                "M-1",
                MissionStatus.ENDED,
                1,
                List.of(rocket)
        );

        // when
        var result = missionSummaryMapper.toDto(missionSummary);

        // then
        assertEquals(missionSummary.missionName(), result.missionName());
        assertEquals(missionSummary.missionStatus(), result.missionStatus());
        assertEquals(missionSummary.numberOfRocketsAssigned(), result.numberOfRocketsAssigned());
        assertEquals(missionSummary.rockets().size(), result.rockets().size());

        var resultRocket = missionSummary.rockets().getFirst();
        assertEquals(rocket.name(), resultRocket.name());
        assertEquals(rocket.status(), resultRocket.status());
    }
}
