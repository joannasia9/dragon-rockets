package org.six.insfrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.infrastructure.repository.InMemoryRocketRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryRocketRepositoryTest {
    private InMemoryRocketRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new InMemoryRocketRepository();
    }

    @Test
    void shouldInsertNewRocketWhenDoesNotExistYet() {
        // given
        var rocket = Rocket.withDefaultStatus("R-1");

        // when:
        repository.insert(rocket);

        // then:
        var existingRockets = repository.findAll();
        assertEquals(1, existingRockets.size());
        assertEquals(rocket.name(), existingRockets.getFirst().name());
        assertEquals(rocket.status(), existingRockets.getFirst().status());
    }

    @Test
    void shouldNotInsertNewRocketWhenRocketWithItsNameAlreadyExists() {
        // given
        var initialRocket = Rocket.withDefaultStatus("R-1");
        repository.insert(initialRocket);

        var newRocket = new Rocket("R-1", RocketStatus.IN_REPAIR);

        // when:
        repository.insert(newRocket);

        // then:
        var existingRocket = repository.findByName(newRocket.name());
        assertTrue(existingRocket.isPresent());
        assertEquals(initialRocket.name(), existingRocket.get().name());
        assertEquals(initialRocket.status(), existingRocket.get().status());
        assertNotEquals(newRocket.status(), existingRocket.get().status());
    }

    @Test
    void shouldNotInsertNewRocketDuringUpdateIfRocketWithGivenNameDoesNotExist() {
        // given
        var rocket = Rocket.withDefaultStatus("R-1");

        // when:
        repository.update(rocket);

        // then:
        var existingRocket = repository.findByName(rocket.name());
        assertTrue(existingRocket.isEmpty());
    }

    @Test
    void shouldUpdateRocketStatusIfRocketWithGivenNameExists() {
        // given
        var initialRocket = Rocket.withDefaultStatus("R-1");
        repository.insert(initialRocket);

        var rocketWithNewStatus = new Rocket("R-1", RocketStatus.IN_REPAIR);

        // when:
        repository.update(rocketWithNewStatus);

        // then:
        var existingRocket = repository.findByName(rocketWithNewStatus.name());
        assertTrue(existingRocket.isPresent());
        assertEquals(initialRocket.name(), existingRocket.get().name());
        assertNotEquals(initialRocket.status(), existingRocket.get().status());
        assertEquals(rocketWithNewStatus.status(), existingRocket.get().status());
    }
}
