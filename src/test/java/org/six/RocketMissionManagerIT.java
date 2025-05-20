package org.six;

import org.junit.jupiter.api.Test;
import org.six.application.dto.MissionDTO;
import org.six.application.dto.MissionSummaryDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.RocketStatus;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RocketMissionManagerIT {
    private final RocketMissionManager manager = RocketMissionManagerFactory.create();

    private static final String MISSION_1_NAME = "Mars";
    private static final String MISSION_2_NAME = "Luna1";
    private static final String MISSION_3_NAME = "Double Landing";
    private static final String MISSION_4_NAME = "Transit";
    private static final String MISSION_5_NAME = "Luna2";
    private static final String MISSION_6_NAME = "Vertical Landing";

    private static final String ROCKET_1_NAME = "Dragon 1";
    private static final String ROCKET_2_NAME = "Dragon 2";
    private static final String ROCKET_3_NAME = "Red Dragon";
    private static final String ROCKET_4_NAME = "Dragon XL";
    private static final String ROCKET_5_NAME = "Falcon Heavy";


    @Test
    void shouldExtractProperSummaryForProvidedData() {
        // given
        var expectedSummary = List.of(
                new MissionSummaryDTO(MISSION_4_NAME, MissionStatus.IN_PROGRESS, 3,
                        List.of(new RocketDTO(ROCKET_3_NAME, RocketStatus.ON_GROUND),
                                new RocketDTO(ROCKET_4_NAME, RocketStatus.IN_SPACE),
                                new RocketDTO(ROCKET_5_NAME, RocketStatus.IN_SPACE)
                        )
                ),
                new MissionSummaryDTO(MISSION_2_NAME, MissionStatus.IN_PROGRESS, 2,
                        List.of(new RocketDTO(ROCKET_1_NAME, RocketStatus.ON_GROUND),
                                new RocketDTO(ROCKET_2_NAME, RocketStatus.ON_GROUND)
                        )
                ),
                new MissionSummaryDTO(MISSION_6_NAME, MissionStatus.ENDED, 0, Collections.emptyList()),
                new MissionSummaryDTO(MISSION_1_NAME, MissionStatus.SCHEDULED, 0, Collections.emptyList()),
                new MissionSummaryDTO(MISSION_5_NAME, MissionStatus.SCHEDULED, 0, Collections.emptyList()),
                new MissionSummaryDTO(MISSION_3_NAME, MissionStatus.ENDED, 0, Collections.emptyList())
        );

        createMissions();
        createRockets();
        assignRocketsToMissions();
        updateMissionsStatuses();

        // when
        var resultSummary = manager.getCurrentMissionsSummary();

        // then
        for (int i = 0; i < expectedSummary.size(); i++) {
            var expected = expectedSummary.get(i);
            var result = resultSummary.get(i);

            assertEquals(expected.missionName(), result.missionName());
            assertEquals(expected.numberOfRocketsAssigned(), result.numberOfRocketsAssigned());
            assertEquals(expected.missionStatus(), result.missionStatus());
            assertEquals(expected.rockets().size(), result.rockets().size());
            assertTrue(expected.rockets().containsAll(result.rockets()));
        }
    }

    private void createMissions() {
        manager.addNewMission(new NewMissionDTO(MISSION_1_NAME));
        manager.addNewMission(new NewMissionDTO(MISSION_2_NAME));
        manager.addNewMission(new NewMissionDTO(MISSION_3_NAME));
        manager.addNewMission(new NewMissionDTO(MISSION_4_NAME));
        manager.addNewMission(new NewMissionDTO(MISSION_5_NAME));
        manager.addNewMission(new NewMissionDTO(MISSION_6_NAME));
    }

    private void createRockets() {
        manager.addNewRocket(new SimpleRocketDTO(ROCKET_1_NAME));
        manager.addNewRocket(new SimpleRocketDTO(ROCKET_2_NAME));
        manager.addNewRocket(new SimpleRocketDTO(ROCKET_3_NAME));
        manager.addNewRocket(new SimpleRocketDTO(ROCKET_4_NAME));
        manager.addNewRocket(new SimpleRocketDTO(ROCKET_5_NAME));
    }

    private void assignRocketsToMissions() {
        //• Luna1 – Pending – Dragons: 2 <- cannot be pending when none rockets are "In repair"
        //o Dragon 1 – On ground
        //o Dragon 2 – On ground
        manager.assignRocketsToMission(new RocketsToMissionAssignmentDTO(
                MISSION_2_NAME,
                Set.of(
                        new SimpleRocketDTO(ROCKET_1_NAME),
                        new SimpleRocketDTO(ROCKET_2_NAME)
                )
        ));

        //• Transit – In progress – Dragons: 3
        //o Red Dragon – On ground
        //o Dragon XL – In space
        //o Falcon Heavy – In space
        manager.assignRocketsToMission(new RocketsToMissionAssignmentDTO(
                MISSION_4_NAME,
                Set.of(
                        new SimpleRocketDTO(ROCKET_3_NAME),
                        new SimpleRocketDTO(ROCKET_4_NAME),
                        new SimpleRocketDTO(ROCKET_5_NAME)
                )
        ));
        manager.changeRocketStatus(new RocketDTO(ROCKET_4_NAME, RocketStatus.IN_SPACE));
        manager.changeRocketStatus(new RocketDTO(ROCKET_5_NAME, RocketStatus.IN_SPACE));
    }

    void updateMissionsStatuses() {
        // • Double Landing – Ended – Dragons: 0
        // • Vertical Landing – Ended – Dragons: 0
        manager.changeMissionStatus(new MissionDTO(MISSION_3_NAME, MissionStatus.ENDED));
        manager.changeMissionStatus(new MissionDTO(MISSION_6_NAME, MissionStatus.ENDED));
    }
}
