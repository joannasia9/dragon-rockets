package org.six.domain.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.exception.MissionAlreadyExistsException;
import org.six.domain.exception.MissionNotExistsException;
import org.six.domain.exception.MissionStatusChangeNotAllowedException;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.domain.validation.MissionStatusUpdateValidator;
import org.six.infrastructure.repository.InMemoryMissionRepository;
import org.six.infrastructure.repository.InMemoryRocketAssignmentRepository;
import org.six.infrastructure.repository.InMemoryRocketRepository;
import org.six.port.repository.MissionRepository;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MissionServiceImplTest {
    private MissionRepository missionRepository;
    private MissionService missionService;

    private RocketRepository rocketRepository;
    private RocketToMissionAssignmentRepository assignmentRepository;

    @BeforeEach
    void setUp() {
        this.missionRepository = new InMemoryMissionRepository();
        this.rocketRepository = new InMemoryRocketRepository();
        this.assignmentRepository = new InMemoryRocketAssignmentRepository();
        var missionStatusUpdateValidator = new MissionStatusUpdateValidator(
                this.rocketRepository,
                this.assignmentRepository
        );
        this.missionService = new DefaultMissionService(
                this.missionRepository,
                rocketRepository, this.assignmentRepository,
                missionStatusUpdateValidator
        );
    }

    @Test
    void shouldAddNewMissionWithValidNameAndStatus() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");

        // when
        assertDoesNotThrow(() -> missionService.addNewMission(newMission));

        // then
        var createdMission = missionRepository.findByName(newMission.name());
        assertTrue(createdMission.isPresent());
        assertEquals(newMission.name(), createdMission.get().name());
        assertEquals(newMission.status(), createdMission.get().status());
    }

    @Test
    void shouldNotCreateAlreadyExistingMission() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");
        missionService.addNewMission(newMission);

        // when + then
        assertThrows(MissionAlreadyExistsException.class, () -> missionService.addNewMission(newMission));
    }

    @Test
    void shouldChangeMissionStatusFromScheduledToPendingWhenRocketInRepairAssigned() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");
        missionService.addNewMission(newMission);

        assignRocketInStatusToMission(newMission.name(), RocketStatus.IN_REPAIR);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.PENDING);

        // when + then
        assertThrows(MissionStatusChangeNotAllowedException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));
    }

    @Test
    void shouldNotChangeMissionStatusFromScheduledToInProgressWhenNoRocketsAssigned() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");
        missionService.addNewMission(newMission);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.IN_PROGRESS);

        // when
        assertThrows(MissionStatusChangeNotAllowedException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));
    }

    @Test
    void shouldChangeMissionStatusFromInProgressToPendingWhenAnyRocketInRepairAssigned() {
        // given
        var newMission = new Mission("M-1", MissionStatus.IN_PROGRESS);
        missionService.addNewMission(newMission);
        assignRocketInStatusToMission(newMission.name(), RocketStatus.IN_REPAIR);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.PENDING);

        // when
        missionService.changeMissionStatus(missionWithNewStatus);

        // then
        var updatedMission = missionRepository.findByName(missionWithNewStatus.name());
        assertTrue(updatedMission.isPresent());
        assertEquals(missionWithNewStatus.name(), updatedMission.get().name());
        assertEquals(missionWithNewStatus.status(), updatedMission.get().status());
    }

    @Test
    void shouldChangeMissionStatusFromAnyStatusToEnded() {
        for (MissionStatus status : MissionStatus.values()) {
            // given
            var missionName = "M-1" + status;
            var newMission = new Mission(missionName, status);
            missionService.addNewMission(newMission);

            var missionWithNewStatus = new Mission(missionName, MissionStatus.ENDED);

            // when
            missionService.changeMissionStatus(missionWithNewStatus);

            // then
            var updatedMission = missionRepository.findByName(missionName);
            assertTrue(updatedMission.isPresent());
            assertEquals(missionName, updatedMission.get().name());
            assertEquals(missionWithNewStatus.status(), updatedMission.get().status());
        }
    }

    @Test
    void shouldNotChangeStatusOfNonExistingMission() {
        // given
        var missionWithNewStatus = new Mission("M-1", MissionStatus.PENDING);

        // when + then
        assertThrows(MissionNotExistsException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));
    }

    @Test
    void shouldNotChangeStatusToPendingIfNoRocketsAssigned() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");
        missionService.addNewMission(newMission);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.PENDING);

        // when
        assertThrows(MissionStatusChangeNotAllowedException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));
    }

    @Test
    void shouldNotChangeStatusToPendingIfNoneOfAssignedRocketsAreInRepairStatus() {
        // given
        var newMission = new Mission("M-1", MissionStatus.IN_PROGRESS);
        missionService.addNewMission(newMission);
        assignRocketInStatusToMission(newMission.name(), RocketStatus.IN_SPACE);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.PENDING);

        // when + then
        assertThrows(MissionStatusChangeNotAllowedException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));
    }

    @Test
    void shouldNotChangeStatusToInProgressIfNoRocketsAssigned() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");
        missionService.addNewMission(newMission);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.IN_PROGRESS);

        // when
        assertThrows(MissionStatusChangeNotAllowedException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));
    }

    @Test
    void shouldNotChangeStatusToInProgressIfAnyOfAssignedRocketsAreInRepairStatus() {
        // given
        var newMission = Mission.withDefaultStatus("M-1");
        missionService.addNewMission(newMission);

        assignRocketInStatusToMission(newMission.name(), RocketStatus.IN_REPAIR);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.IN_PROGRESS);

        // when + then
        assertThrows(MissionStatusChangeNotAllowedException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));

    }

    @Test
    void shouldNotChangeStatusToScheduledIfAnyRocketsAssigned() {
        // given
        var newMission = new Mission("M-1", MissionStatus.IN_PROGRESS);
        missionService.addNewMission(newMission);

        assignRocketInStatusToMission(newMission.name(), RocketStatus.IN_REPAIR);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.SCHEDULED);

        // when + then
        assertThrows(MissionStatusChangeNotAllowedException.class, () -> missionService.changeMissionStatus(missionWithNewStatus));

    }

    private void assignRocketInStatusToMission(String missionName, RocketStatus status) {
        var rocketInRepair = new Rocket("R-1", status);
        rocketRepository.insert(rocketInRepair);
        assignmentRepository.insertAssignment(rocketInRepair.name(), missionName);
    }
}
