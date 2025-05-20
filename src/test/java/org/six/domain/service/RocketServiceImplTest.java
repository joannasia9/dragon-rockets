package org.six.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.exception.RocketAlreadyExistsException;
import org.six.domain.exception.RocketStatusChangeNotAllowedException;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.domain.validation.RocketStatusUpdateValidator;
import org.six.infrastructure.repository.InMemoryRocketAssignmentRepository;
import org.six.infrastructure.repository.InMemoryRocketRepository;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RocketServiceImplTest {
    private RocketRepository rocketRepository;
    private RocketToMissionAssignmentRepository rocketToMissionAssignmentRepository;
    private RocketService rocketService;

    @BeforeEach
    void setUp() {
        this.rocketRepository = new InMemoryRocketRepository();
        this.rocketToMissionAssignmentRepository = new InMemoryRocketAssignmentRepository();
        this.rocketService = new DefaultRocketService(
                new RocketStatusUpdateValidator(this.rocketToMissionAssignmentRepository),
                this.rocketRepository
        );
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
    void shouldNotCreateAlreadyExistingRocket() {
        // given
        var newRocket = Rocket.withDefaultStatus("R-1");
        rocketService.addNewRocket(newRocket);

        //when + then
        assertThrows(RocketAlreadyExistsException.class, () -> rocketService.addNewRocket(newRocket));
    }

    @Test
    void shouldChangeRocketStatusFromOnGroundToInSpaceIfAssignedToAMission() {
        // given
        var newRocket = Rocket.withDefaultStatus("R-1");
        rocketService.addNewRocket(newRocket);
        rocketToMissionAssignmentRepository.insertAssignment(newRocket.name(), "M-1");

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
        // given
        var rocketWithNewStatus = new Rocket("R-1", RocketStatus.ON_GROUND);

        this.rocketRepository.insert(rocketWithNewStatus);
        this.rocketToMissionAssignmentRepository.insertAssignment(rocketWithNewStatus.name(), "M-1");

        // when
        assertThrows(RocketStatusChangeNotAllowedException.class, () -> rocketService.changeRocketStatus(rocketWithNewStatus));
    }

    @Test
    void shouldNotChangeRocketStatusToInSpaceWhenNoneMissionAssigned() {
        // given
        var rocketWithNewStatus = new Rocket("R-1", RocketStatus.IN_SPACE);
        this.rocketRepository.insert(rocketWithNewStatus);

        // when
        assertThrows(RocketStatusChangeNotAllowedException.class, () -> rocketService.changeRocketStatus(rocketWithNewStatus));
    }
}

