package org.six.insfrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.infrastructure.repository.InMemoryMissionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryMissionRepositoryTest {
    private InMemoryMissionRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new InMemoryMissionRepository();
    }

    @Test
    void shouldInsertNewMissionWhenDoesNotExistYet() {
        // given
        var mission = Mission.withDefaultStatus("M-1");

        // when:
        repository.insert(mission);

        // then:
        var existingMissions = repository.findAll();
        assertEquals(1, existingMissions.size());
        assertEquals(mission.name(), existingMissions.getFirst().name());
        assertEquals(mission.status(), existingMissions.getFirst().status());
    }

    @Test
    void shouldNotInsertNewMissionWhenMissionWithItsNameAlreadyExists() {
        // given
        var initialMission = Mission.withDefaultStatus("M-1");
        repository.insert(initialMission);

        var newMission = new Mission("M-1", MissionStatus.PENDING);

        // when:
        repository.insert(newMission);

        // then:
        var existingMission = repository.findByName(newMission.name());
        assertTrue(existingMission.isPresent());
        assertEquals(initialMission.name(), existingMission.get().name());
        assertEquals(initialMission.status(), existingMission.get().status());
        assertNotEquals(newMission.status(), existingMission.get().status());
    }

    @Test
    void shouldNotInsertNewMissionDuringUpdateIfMissionWithGivenNameDoesNotExist() {
        // given
        var mission = Mission.withDefaultStatus("M-1");

        // when:
        repository.update(mission);

        // then:
        var existingMission = repository.findByName(mission.name());
        assertTrue(existingMission.isEmpty());
    }

    @Test
    void shouldUpdateMissionStatusIfMissionWithGivenNameExists() {
        // given
        var initialMission = Mission.withDefaultStatus("M-1");
        repository.insert(initialMission);

        var missionWithNewStatus = new Mission("M-1", MissionStatus.IN_PROGRESS);

        // when:
        repository.update(missionWithNewStatus);

        // then:
        var existingMission = repository.findByName(missionWithNewStatus.name());
        assertTrue(existingMission.isPresent());
        assertEquals(initialMission.name(), existingMission.get().name());
        assertNotEquals(initialMission.status(), existingMission.get().status());
        assertEquals(missionWithNewStatus.status(), existingMission.get().status());
    }
}
