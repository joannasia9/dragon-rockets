package org.six.domain.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.exception.MissionAlreadyExistsException;
import org.six.domain.model.Mission;
import org.six.infrastructure.repository.InMemoryMissionRepository;
import org.six.port.repository.MissionRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MissionServiceImplTest {
    private MissionRepository missionRepository;
    private MissionService missionService;

    @BeforeEach
    void setUp() {
        this.missionRepository = new InMemoryMissionRepository();
        this.missionService = new DefaultMissionService(this.missionRepository);
    }

    @Test
    void shouldAddNewMissionWithValidNameAndStatus() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");

        //when
        assertDoesNotThrow(() -> missionService.addNewMission(newMission));

        //then
        var createdMission = missionRepository.findByName(newMission.name());
        assertTrue(createdMission.isPresent());
        assertEquals(newMission.name(), createdMission.get().name());
        assertEquals(newMission.status(), createdMission.get().status());
        assertEquals(newMission.rockets(), createdMission.get().rockets());
    }

    @Test
    void shouldNotCreateMissionWithBlankName() {
        // TODO: Implement
    }

    @Test
    void shouldNotCreateAlreadyExistingMission() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");
        missionService.addNewMission(newMission);

        //when + then
        assertThrows(MissionAlreadyExistsException.class, () -> missionService.addNewMission(newMission));
    }

    @Test
    void shouldNotCreateMissionWithDuplicatedRockets() {
        // TODO: Implement - ignore as new mission is created with empty rockets pool
        //  and then rockets are assigned one by one
    }

    @Test
    void shouldChangeMissionStatusFromScheduledToPending() {
        // TODO: Implement
    }

    @Test
    void shouldChangeMissionStatusFromPendingToInProgress() {
        // TODO: Implement
    }

    @Test
    void shouldChangeMissionStatusFromInProgressToPending() {
        // TODO: Implement
    }

    @Test
    void shouldChangeMissionStatusFromInProgressToEnded() {
        // TODO: Implement
    }

    @Test
    void shouldChangeMissionStatusFromPendingToEnded() {
        // TODO: Implement
    }

    @Test
    void shouldNotChangeStatusOfNonExistingMission() {
        // TODO: Implement
    }

    @Test
    void shouldNotChangeStatusToPendingIfNoRocketsAssigned() {
        // TODO: Implement
    }

    @Test
    void shouldNotChangeStatusToPendingIfNoneOfAssignedRocketsAreInRepairStatus() {
        // TODO: Implement
    }

    @Test
    void shouldNotChangeStatusToInProgressIfNoRocketsAssigned() {
        // TODO: Implement
    }

    @Test
    void shouldNotChangeStatusToInProgressIfAnyOfAssignedRocketsAreInRepairStatus() {
        // TODO: Implement
    }

    @Test
    void shouldNotChangeStatusToScheduledIfAnyRocketsAssigned() {
        // TODO: Implement
    }
}
