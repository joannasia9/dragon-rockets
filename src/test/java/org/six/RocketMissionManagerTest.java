package org.six;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.MissionDTO;
import org.six.application.dto.MissionSummaryDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewMissionService;
import org.six.application.service.AddNewRocketService;
import org.six.application.service.AssignRocketsToMissionService;
import org.six.application.service.ChangeMissionStatusService;
import org.six.application.service.ChangeRocketStatusService;
import org.six.application.service.MissionsSummaryService;
import org.six.application.service.RemoveRocketAssignmentService;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.RocketStatus;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RocketMissionManagerTest {
    private AddNewRocketService addNewRocketService;
    private ChangeRocketStatusService changeRocketStatusService;
    private RocketMissionManager missionManager;
    private AddNewMissionService addNewMissionService;
    private ChangeMissionStatusService changeMissionStatusService;
    private RemoveRocketAssignmentService removeRocketAssignmentService;
    private AssignRocketsToMissionService assignRocketsToMissionService;
    private MissionsSummaryService missionsSummaryService;

    @BeforeEach
    void setUp() {
        this.addNewRocketService = Mockito.mock(AddNewRocketService.class);
        this.changeRocketStatusService = Mockito.mock(ChangeRocketStatusService.class);
        this.addNewMissionService = Mockito.mock(AddNewMissionService.class);
        this.changeMissionStatusService = Mockito.mock(ChangeMissionStatusService.class);
        this.removeRocketAssignmentService = Mockito.mock(RemoveRocketAssignmentService.class);
        this.assignRocketsToMissionService = Mockito.mock(AssignRocketsToMissionService.class);
        this.missionsSummaryService = Mockito.mock(MissionsSummaryService.class);

        this.missionManager = new RocketMissionManager(
                this.addNewRocketService,
                this.changeRocketStatusService,
                this.addNewMissionService,
                this.changeMissionStatusService,
                this.assignRocketsToMissionService,
                this.removeRocketAssignmentService,
                this.missionsSummaryService
        );
    }

    @Test
    void shouldProcessAddNewRocketAction() {
        // given
        var rocket = new SimpleRocketDTO("R-1");

        // when
        missionManager.addNewRocket(rocket);

        // then
        Mockito.verify(addNewRocketService).execute(rocket);
    }

    @Test
    void shouldProcessChangeRocketStatusAction() {
        // given
        var rocket = new RocketDTO("R-1", RocketStatus.IN_REPAIR);

        // when
        missionManager.changeRocketStatus(rocket);

        // then
        Mockito.verify(changeRocketStatusService).execute(rocket);
        Mockito.verify(changeMissionStatusService).execute(rocket);
    }

    @Test
    void shouldProcessAddNewMissionAction() {
        // given
        var mission = new NewMissionDTO("W-1");

        // when
        missionManager.addNewMission(mission);

        // then
        Mockito.verify(addNewMissionService).execute(mission);
    }

    @Test
    void shouldProcessChangeMissionStatusAction() {
        // given
        var missionWithNewStatus = new MissionDTO("M-1", MissionStatus.IN_PROGRESS);

        // when
        missionManager.changeMissionStatus(missionWithNewStatus);

        // then
        Mockito.verify(changeMissionStatusService).execute(missionWithNewStatus);
        Mockito.verify(removeRocketAssignmentService).execute(missionWithNewStatus);
    }

    @Test
    void shouldProcessRocketToMissionAssignmentAction() {
        // given
        var rocket = new SimpleRocketDTO("R-1");
        var rocketsToMissionAssignmentDTO = new RocketsToMissionAssignmentDTO("M-1", Set.of(rocket));

        // when
        missionManager.assignRocketsToMission(rocketsToMissionAssignmentDTO);

        // then
        Mockito.verify(assignRocketsToMissionService).execute(rocketsToMissionAssignmentDTO);
    }

    @Test
    void shouldProcessSummaryRetrievalAction() {
        // given
        var summary = List.of(
                new MissionSummaryDTO(
                        "M-1",
                        MissionStatus.ENDED, 1,
                        Collections.emptyList()
                )
        );
        when(this.missionsSummaryService.getSummary()).thenReturn(summary);
        // when
        var result = missionManager.getCurrentMissionsSummary();

        // then
        Mockito.verify(missionsSummaryService).getSummary();
        assertEquals(summary, result);
    }
}
