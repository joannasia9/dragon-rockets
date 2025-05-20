package org.six.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.exception.MissionNotExistsException;
import org.six.domain.exception.RocketAlreadyAssignedException;
import org.six.domain.exception.RocketNotExistsException;
import org.six.domain.exception.RocketToMissionAssignmentNotAllowedException;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.infrastructure.repository.InMemoryMissionRepository;
import org.six.infrastructure.repository.InMemoryRocketAssignmentRepository;
import org.six.infrastructure.repository.InMemoryRocketRepository;
import org.six.port.repository.MissionRepository;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RocketAssignmentServiceImplTest {
    private MissionRepository missionRepository;
    private RocketToMissionAssignmentRepository rocketToMissionAssignmentRepository;
    private RocketRepository rocketRepository;
    private RocketAssignmentService rocketAssignmentService;

    @BeforeEach
    void setUp() {
        this.missionRepository = new InMemoryMissionRepository();
        this.rocketRepository = new InMemoryRocketRepository();
        this.rocketToMissionAssignmentRepository = new InMemoryRocketAssignmentRepository();
        this.rocketAssignmentService = new DefaultRocketAssignmentService(
                this.missionRepository,
                this. rocketRepository,
                this.rocketToMissionAssignmentRepository
        );
    }

    @Test
    void shouldAssignRocketsToAMission() {
        // given
        var missionName = "M-1";
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);
        missionRepository.insert(Mission.withDefaultStatus(missionName));
        rocketRepository.insert(rocket);

        // when
        rocketAssignmentService.assignRocketsToMission(missionName, Set.of(rocket));

        // then
        assertTrue(rocketToMissionAssignmentRepository.isAssignedToMission(rocket.name()));
    }

    @Test
    void shouldNotAssignRocketsToNonExistingMission() {
        // given
        var missionName = "M-1";
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);

        // when + then
        assertThrows(MissionNotExistsException.class, () -> rocketAssignmentService.assignRocketsToMission(missionName, Set.of(rocket)));
    }

    @Test
    void shouldNotAssignNonExistingRocketsToExistingMission() {
        // given
        var missionName = "M-1";
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);
        missionRepository.insert(Mission.withDefaultStatus(missionName));

        // when + then
        assertThrows(RocketNotExistsException.class, () -> rocketAssignmentService.assignRocketsToMission(missionName, Set.of(rocket)));
    }

    @Test
    void shouldNotAssignAlreadyAssignedRockets() {
        // given
        var missionName = "M-1";
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);
        missionRepository.insert(Mission.withDefaultStatus(missionName));
        rocketToMissionAssignmentRepository.insertAssignment(rocket.name(), missionName);

        // when + then
        assertThrows(RocketAlreadyAssignedException.class, () -> rocketAssignmentService.assignRocketsToMission(missionName, Set.of(rocket)));
    }

    @Test
    void shouldNotAssignRocketsToTheFinishedMission() {
        // given
        var missionName = "M-1";
        var rocket = new Rocket("R-1", RocketStatus.ON_GROUND);
        missionRepository.insert(new Mission(missionName, MissionStatus.ENDED));
        rocketRepository.insert(rocket);

        // when + then
        assertThrows(RocketToMissionAssignmentNotAllowedException.class, () -> rocketAssignmentService.assignRocketsToMission(missionName, Set.of(rocket)));
    }
}
