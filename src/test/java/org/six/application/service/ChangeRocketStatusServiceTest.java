package org.six.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.RocketDTO;
import org.six.application.mappers.RocketMapper;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.domain.service.RocketService;

import static org.mockito.Mockito.when;

public class ChangeRocketStatusServiceTest {
    private RocketMapper rocketMapper;
    private RocketService rocketService;
    private ChangeRocketStatusService changeRocketStatusService;

    @BeforeEach
    void setUp() {
        this.rocketMapper = Mockito.mock(RocketMapper.class);
        this.rocketService = Mockito.mock(RocketService.class);
        this.changeRocketStatusService = new ChangeRocketStatusService(this.rocketMapper, this.rocketService);
    }

    @Test
    void shouldProperlyProcessExecuteWithNoExceptionThrown() {
        // given
        var rocketWithNewStatus = new RocketDTO("R-1", RocketStatus.IN_REPAIR);
        var rocketWithNewStatusDomain = new Rocket(rocketWithNewStatus.name(), rocketWithNewStatus.status());

        when(this.rocketMapper.toDomain(rocketWithNewStatus)).thenReturn(rocketWithNewStatusDomain);

        // when
        changeRocketStatusService.execute(rocketWithNewStatus);

        // then
        Mockito.verify(rocketService).changeRocketStatus(rocketWithNewStatusDomain);
    }
}
