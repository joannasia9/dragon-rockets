package org.six.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.mappers.RocketMapper;
import org.six.domain.model.Rocket;
import org.six.domain.service.RocketService;

import static org.mockito.Mockito.when;

public class AddNewRocketServiceTest {
    private RocketMapper rocketMapper;
    private RocketService rocketService;
    private AddNewRocketService addNewRocketService;

    @BeforeEach
    void setUp() {
        this.rocketMapper = Mockito.mock(RocketMapper.class);
        this.rocketService = Mockito.mock(RocketService.class);
        this.addNewRocketService = new AddNewRocketService(this.rocketMapper, this.rocketService);
    }

    @Test
    void shouldProperlyProcessExecuteWithNoExceptionThrown() {
        // given
        var newRocket = new SimpleRocketDTO("R-1");
        var newRocketDomain = Rocket.withDefaultStatus(newRocket.name());

        when(this.rocketMapper.toDomain(newRocket)).thenReturn(newRocketDomain);

        // when
        addNewRocketService.execute(newRocket);

        // then
        Mockito.verify(rocketService).addNewRocket(newRocketDomain);
    }
}
