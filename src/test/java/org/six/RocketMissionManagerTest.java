package org.six;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewMissionService;
import org.six.application.service.AddNewRocketService;
import org.six.application.service.ChangeRocketStatusService;
import org.six.domain.model.RocketStatus;

public class RocketMissionManagerTest {
    private AddNewRocketService addNewRocketService;
    private ChangeRocketStatusService changeRocketStatusService;
    private RocketMissionManager missionManager;
    private AddNewMissionService addNewMissionService;

    @BeforeEach
    void setUp() {
        this.addNewRocketService = Mockito.mock(AddNewRocketService.class);
        this.changeRocketStatusService = Mockito.mock(ChangeRocketStatusService.class);
        this.addNewMissionService = Mockito.mock(AddNewMissionService.class);
        this.missionManager = new RocketMissionManager(
                this.addNewRocketService,
                this.changeRocketStatusService,
                this.addNewMissionService);
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
        // TODO: Implement
    }

    @Test
    void shouldProcessRocketToMissionAssignmentAction() {
        // TODO: Implement
    }

}
