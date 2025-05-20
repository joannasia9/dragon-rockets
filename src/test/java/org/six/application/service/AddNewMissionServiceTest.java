package org.six.application.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.NewMissionDTO;
import org.six.application.mappers.MissionMapper;
import org.six.domain.model.Mission;
import org.six.domain.service.MissionService;

import static org.mockito.Mockito.when;

public class AddNewMissionServiceTest {

    private MissionMapper missionMapper;
    private MissionService missionService;
    private AddNewMissionService addNewMissionService;

    @BeforeEach
    void setUp() {
        this.missionMapper = Mockito.mock(MissionMapper.class);
        this.missionService = Mockito.mock(MissionService.class);
        this.addNewMissionService = new AddNewMissionService(this.missionMapper, this.missionService);
    }

    @Test
    void shouldProperlyProcessExecuteWithNoExceptionThrown() {
        // given
        var newMission = new NewMissionDTO("M-1");
        var newMissionDomain = Mission.withDefaultStatus(newMission.name());

        when(this.missionMapper.toDomain(newMission)).thenReturn(newMissionDomain);

        // when
        addNewMissionService.execute(newMission);

        // then
        Mockito.verify(missionService).addNewMission(newMissionDomain);
    }
}