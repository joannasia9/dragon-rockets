package org.six.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.exception.RocketAlreadyExistsException;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.infrastructure.repository.InMemoryRocketRepository;
import org.six.port.repository.RocketRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RocketServiceImplTest {
    private RocketRepository rocketRepository;
    private RocketService rocketService;

    @BeforeEach
    void setUp() {
        this.rocketRepository = new InMemoryRocketRepository();
        this.rocketService = new DefaultRocketService(this.rocketRepository);
    }

    @Test
    void shouldAddNewRocketWithValidNameAndStatusAndNoMissionAssignment() {
        // given
        var newRocket = Rocket.withDefaultStatus("R-1");

        //when
        assertDoesNotThrow(() -> rocketService.addNewRocket(newRocket));

        //then
        var createdRocket = rocketRepository.findByName(newRocket.name());
        assertTrue(createdRocket.isPresent());
        assertEquals(newRocket.name(), createdRocket.get().name());
        assertEquals(newRocket.status(), createdRocket.get().status());
    }

    @Test
    void shouldNotCreateRocketWithBlankName() {
        // TODO: Implement
    }

    @Test
    void shouldNotCreateAlreadyExistingRocket() {
        // given
        var newRocket = Rocket.withDefaultStatus("R-1");
        rocketService.addNewRocket(newRocket);

        //when + then
        assertThrows(RocketAlreadyExistsException.class, () -> rocketService.addNewRocket(newRocket));
    }

    @Test
    void shouldChangeRocketStatusFromOnGroundToInSpace() {
        // given
        var newRocket = Rocket.withDefaultStatus("R-1");
        rocketService.addNewRocket(newRocket);

        var rocketWithNewStatus = new Rocket(newRocket.name(), RocketStatus.IN_SPACE);

        //when
        assertDoesNotThrow(() -> rocketService.changeRocketStatus(rocketWithNewStatus));

        //then
        var updatedRocket = rocketRepository.findByName(newRocket.name());
        assertTrue(updatedRocket.isPresent());
        assertEquals(newRocket.name(), updatedRocket.get().name());
        assertEquals(rocketWithNewStatus.status(), updatedRocket.get().status());
    }

    @Test
    void shouldChangeRocketStatusFromOnGroundToInRepair() {
        // given
        var newRocket = Rocket.withDefaultStatus("R-1");
        rocketService.addNewRocket(newRocket);

        var rocketWithNewStatus = new Rocket(newRocket.name(), RocketStatus.IN_REPAIR);

        //when
        assertDoesNotThrow(() -> rocketService.changeRocketStatus(rocketWithNewStatus));

        //then
        var updatedRocket = rocketRepository.findByName(newRocket.name());
        assertTrue(updatedRocket.isPresent());
        assertEquals(newRocket.name(), updatedRocket.get().name());
        assertEquals(rocketWithNewStatus.status(), updatedRocket.get().status());
    }

    @Test
    void shouldChangeRocketStatusFromInSpaceToInRepair() {
        // given
        var newRocket = new Rocket("R-1", RocketStatus.IN_SPACE);
        rocketRepository.insert(newRocket);

        var rocketWithNewStatus = new Rocket(newRocket.name(), RocketStatus.IN_REPAIR);

        //when
        assertDoesNotThrow(() -> rocketService.changeRocketStatus(rocketWithNewStatus));

        //then
        var updatedRocket = rocketRepository.findByName(newRocket.name());
        assertTrue(updatedRocket.isPresent());
        assertEquals(newRocket.name(), updatedRocket.get().name());
        assertEquals(rocketWithNewStatus.status(), updatedRocket.get().status());
    }

    @Test
    void shouldNotChangeRocketStatusToOnGroundWhenAnyMissionAssigned() {
        // TODO: Implement
    }

    @Test
    void shouldNotChangeRocketStatusToInSpaceWhenNoneMissionAssigned() {
        // TODO: Implement
    }
}

