package org.six.domain.service;


import org.junit.jupiter.api.Test;

public class MissionServiceImplTest {
    @Test
    void shouldAddNewMissionWithValidNameAndStatus() {
        // TODO: Implement
    }

    @Test
    void shouldNotCreateMissionWithBlankName() {
        // TODO: Implement
    }

    @Test
    void shouldNotCreateAlreadyExistingMission() {
        // TODO: Implement
    }

    @Test
    void shouldNotCreateMissionWithDuplicatedRockets() {
        // TODO: Implement
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
