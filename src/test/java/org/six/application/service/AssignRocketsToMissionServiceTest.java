package org.six.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.mappers.RocketMapper;
import org.six.domain.model.Rocket;
import org.six.domain.service.RocketAssignmentService;
import org.six.domain.service.RocketService;

import java.util.Set;

import static org.mockito.Mockito.when;

public class AssignRocketsToMissionServiceTest {

    private RocketMapper rocketMapper;
    private RocketAssignmentService rocketAssignmentService;
    private AssignRocketsToMissionService assignRocketsToMissionService;

    @BeforeEach
    void setUp() {
        this.rocketMapper = Mockito.mock(RocketMapper.class);
        this.rocketAssignmentService = Mockito.mock(RocketAssignmentService.class);
        this.assignRocketsToMissionService = new AssignRocketsToMissionService(this.rocketMapper, this.rocketAssignmentService);
    }

    @Test
    void shouldProperlyProcessExecuteWithNoExceptionThrown() {
        // given
        var rocket = new SimpleRocketDTO("R-1");
        var rocketDomain = Rocket.withDefaultStatus(rocket.name());

        var assignmentDTO = new RocketsToMissionAssignmentDTO("M-1", Set.of(rocket));

        when(this.rocketMapper.toDomain(rocket)).thenReturn(rocketDomain);

        // when
        assignRocketsToMissionService.execute(assignmentDTO);

        // then
        Mockito.verify(rocketAssignmentService).assignRocketsToMission(assignmentDTO.missionName(), Set.of(rocketDomain));
    }
}
