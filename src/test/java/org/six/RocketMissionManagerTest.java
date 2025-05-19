package org.six;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewRocketService;
import org.six.application.service.ChangeRocketStatusService;
import org.six.domain.model.RocketStatus;

public class RocketMissionManagerTest {
    private AddNewRocketService addNewRocketService;
    private ChangeRocketStatusService changeRocketStatusService;
    private RocketMissionManager missionManager;

    @BeforeEach
    void setUp() {
        this.addNewRocketService = Mockito.mock(AddNewRocketService.class);
        this.changeRocketStatusService = Mockito.mock(ChangeRocketStatusService.class);
        this.missionManager = new RocketMissionManager(
                this.addNewRocketService,
                this.changeRocketStatusService
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
    }

    @Test
    void shouldProcessAddNewMissionAction() {
        // TODO: Implement
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
