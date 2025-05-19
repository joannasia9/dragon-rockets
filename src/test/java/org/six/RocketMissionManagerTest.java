package org.six;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewRocketService;

public class RocketMissionManagerTest {
    private AddNewRocketService addNewRocketService;
    private RocketMissionManager missionManager;

    @BeforeEach
    void setUp() {
        this.addNewRocketService = Mockito.mock(AddNewRocketService.class);
        this.missionManager = new RocketMissionManager(addNewRocketService);
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
        // TODO: Implement
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
