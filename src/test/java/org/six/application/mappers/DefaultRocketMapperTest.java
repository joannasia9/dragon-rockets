package org.six.application.mappers;

import org.junit.jupiter.api.Test;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultRocketMapperTest {
    private final RocketMapper rocketMapper = new DefaultRocketMapper();

    @Test
    void shouldProperlyMapDTORocketToDomain() {
        // given
        var rocket = new RocketDTO("R-1", RocketStatus.ON_GROUND);

        // when
        var result = rocketMapper.toDomain(rocket);

        // then
        assertEquals(rocket.name(), result.name());
        assertEquals(rocket.status(), result.status());
    }

    @Test
    void shouldProperlyMapDTOSimpleRocketToDomain() {
        // given
        var rocket = new SimpleRocketDTO("R-1");

        // when
        var result = rocketMapper.toDomain(rocket);

        // then
        assertEquals(rocket.name(), result.name());
        assertEquals(RocketStatus.ON_GROUND, result.status());
    }

    @Test
    void shouldProperlyMapDomainRocketToDTO() {
        // given
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);

        // when
        var result = rocketMapper.toDto(rocket);

        // then
        assertEquals(rocket.name(), result.name());
        assertEquals(rocket.status(), result.status());
    }
}
