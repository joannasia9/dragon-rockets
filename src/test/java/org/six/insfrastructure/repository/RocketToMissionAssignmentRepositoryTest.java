package org.six.insfrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.infrastructure.repository.InMemoryRocketAssignmentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RocketToMissionAssignmentRepositoryTest {
    private InMemoryRocketAssignmentRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new InMemoryRocketAssignmentRepository();
    }

    @Test
    void shouldInsertAssignmentIfNotExistsYet() {
        // given
        var rocketName = "R-1";
        var missionName = "M-1";

        // when:
        repository.insertAssignment(rocketName, missionName);

        // then:
        var assignedRockets = repository.findRocketsFor(missionName);
        assertEquals(1, assignedRockets.size());
        assertEquals(rocketName, assignedRockets.getFirst());

        var assignedMission = repository.findMissionFor(rocketName);
        assertTrue(assignedMission.isPresent());
        assertEquals(missionName, assignedMission.get());
    }

    @Test
    void shouldNotInsertNewAssignmentIfAlreadyExists() {
        // given
        var rocketName = "R-1";
        var missionName = "M-1";
        var anotherMissionName = "M-2";
        repository.insertAssignment(rocketName, missionName);

        // when:
        repository.insertAssignment(rocketName, anotherMissionName);

        // then:
        var assignedRockets = repository.findRocketsFor(anotherMissionName);
        assertEquals(0, assignedRockets.size());

        var assignedMission = repository.findMissionFor(rocketName);
        assertTrue(assignedMission.isPresent());
        assertEquals(missionName, assignedMission.get());
    }

    @Test
    void shouldProperlySpecifyIfRocketWithAssignedMissionIsAssignedToMission() {
        // given
        var rocketName = "R-1";
        var missionName = "M-1";
        repository.insertAssignment(rocketName, missionName);

        // when:
        assertTrue(repository.isAssignedToMission(rocketName));
    }

    @Test
    void shouldProperlySpecifyIfRocketWithoutAssignedMissionIsNotAssignedToMission() {
        // given
        var missionName = "M-1";

        // when:
        assertFalse(repository.isAssignedToMission(missionName));
    }

    @Test
    void shouldProperlyRemoveExistingMissionAssignment() {
        // given
        var rocketName = "R-1";
        var missionName = "M-1";
        repository.insertAssignment(rocketName, missionName);
        assertTrue(repository.isAssignedToMission(rocketName));

        // when:
        repository.removeRocketsAssignedTo(missionName);

        // then:
        assertFalse(repository.isAssignedToMission(missionName));
    }

    @Test
    void shouldIgnoreNonExistingMissionAssignmentDuringRemoval() {
        // given
        var missionName = "M-1";

        // when:
        repository.removeRocketsAssignedTo(missionName);
    }
}
