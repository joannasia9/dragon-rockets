package org.six.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.MissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.mappers.MissionMapper;
import org.six.application.mappers.RocketMapper;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.domain.service.MissionService;

import static org.mockito.Mockito.when;

public class ChangeMissionStatusServiceTest {
    private MissionMapper missionMapper;
    private MissionService missionService;
    private RocketMapper rocketMapper;
    private ChangeMissionStatusService changeMissionStatusService;

    @BeforeEach
    void setUp() {
        this.missionMapper = Mockito.mock(MissionMapper.class);
        this.missionService = Mockito.mock(MissionService.class);
        this.rocketMapper = Mockito.mock(RocketMapper.class);
        this.changeMissionStatusService = new ChangeMissionStatusService(this.missionMapper, this.missionService, this.rocketMapper);
    }

    @Test
    void shouldProperlyProcessExecuteForMissionWithNoExceptionThrown() {
        // given
        var missionWithNewStatus = new MissionDTO("M-1", MissionStatus.IN_PROGRESS);
        var missionWithNewStatusDomain = new Mission(missionWithNewStatus.name(), missionWithNewStatus.status());

        when(this.missionMapper.toDomain(missionWithNewStatus)).thenReturn(missionWithNewStatusDomain);

        // when
        changeMissionStatusService.execute(missionWithNewStatus);

        // then
        Mockito.verify(missionService).changeMissionStatus(missionWithNewStatusDomain);
    }

    @Test
    void shouldProperlyProcessExecuteForRocketWithNoExceptionThrown() {
        // given
        var rocketWithNewStatus = new RocketDTO("R-1", RocketStatus.IN_SPACE);
        var rocketWithNewStatusDomain = new Rocket(rocketWithNewStatus.name(), rocketWithNewStatus.status());

        when(this.rocketMapper.toDomain(rocketWithNewStatus)).thenReturn(rocketWithNewStatusDomain);

        // when
        changeMissionStatusService.execute(rocketWithNewStatus);

        // then
        Mockito.verify(missionService).changeMissionStatusIfNeeded(rocketWithNewStatusDomain);
    }
}
