package org.six.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.MissionSummary;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.infrastructure.repository.InMemoryMissionRepository;
import org.six.infrastructure.repository.InMemoryRocketAssignmentRepository;
import org.six.infrastructure.repository.InMemoryRocketRepository;
import org.six.port.repository.MissionRepository;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MissionSummaryServiceImplTest {
    private MissionSummaryService missionSummaryService;
    private RocketRepository rocketRepository;
    private MissionRepository missionRepository;
    private RocketToMissionAssignmentRepository assignmentRepository;

    @BeforeEach
    void setUp() {
        this.rocketRepository = new InMemoryRocketRepository();
        this.missionRepository = new InMemoryMissionRepository();
        this.assignmentRepository = new InMemoryRocketAssignmentRepository();
        this.missionSummaryService = new DefaultMissionSummaryService(
                this.rocketRepository,
                this.missionRepository,
                this.assignmentRepository
        );
    }

    @Test
    void shouldGetValidSummaryWhenMissionsDataInOrderAlready() {
        // given
        var rocket = new Rocket("R-1", RocketStatus.IN_SPACE);
        var mission1 = new Mission("M-1", MissionStatus.IN_PROGRESS);
        var mission2 = new Mission("M-2", MissionStatus.SCHEDULED);

        var expectedSummary = List.of(
                new MissionSummary(mission1.name(), mission1.status(), 1, List.of(rocket)),
                new MissionSummary(mission2.name(), mission2.status(), 0, Collections.emptyList())
        );

        rocketRepository.insert(rocket);
        missionRepository.insert(mission1);
        missionRepository.insert(mission2);
        assignmentRepository.insertAssignment(rocket.name(), mission1.name());

        // when
        var resultSummary = missionSummaryService.getCurrentMissionsSummary();

        // then
        assertEquals(2, resultSummary.size());
        assertEquals(expectedSummary, resultSummary);
    }

    @Test
    void shouldGetValidSummaryWhenMissionsDataNotSortedYet() {
        // given
        var rocket1 = new Rocket("R-1", RocketStatus.IN_SPACE);
        var rocket2 = new Rocket("R-2", RocketStatus.IN_SPACE);

        var missionA = new Mission("M-A", MissionStatus.SCHEDULED);
        var missionB = new Mission("M-B", MissionStatus.IN_PROGRESS);

        var expectedSummary = List.of(
                new MissionSummary(missionA.name(), missionA.status(), 1, List.of(rocket1)),
                new MissionSummary(missionB.name(), missionB.status(), 1, List.of(rocket2))
        );

        rocketRepository.insert(rocket1);
        rocketRepository.insert(rocket2);
        missionRepository.insert(missionB);
        missionRepository.insert(missionA);
        assignmentRepository.insertAssignment(rocket1.name(), missionA.name());
        assignmentRepository.insertAssignment(rocket2.name(), missionB.name());

        // when
        var resultSummary = missionSummaryService.getCurrentMissionsSummary();

        // then
        assertEquals(2, resultSummary.size());
        assertEquals(expectedSummary, resultSummary);
    }

    @Test
    void shouldGetEmptySummaryWhenNoMissionConfiguredYet() {
        // when
        var resultSummary = missionSummaryService.getCurrentMissionsSummary();

        // then
        assertTrue(resultSummary.isEmpty());
    }
}
