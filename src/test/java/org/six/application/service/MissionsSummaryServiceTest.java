package org.six.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.six.application.dto.MissionSummaryDTO;
import org.six.application.mappers.MissionSummaryMapper;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.MissionSummary;
import org.six.domain.service.MissionSummaryService;

import java.util.Collections;

import static org.mockito.Mockito.when;

public class MissionsSummaryServiceTest {
    private MissionSummaryMapper summaryMapper;
    private MissionSummaryService missionSummaryService;
    private MissionsSummaryService missionsSummaryService;

    @BeforeEach
    void setUp() {
        this.summaryMapper = Mockito.mock(MissionSummaryMapper.class);
        this.missionSummaryService = Mockito.mock(MissionSummaryService.class);
        this.missionsSummaryService = new MissionsSummaryService(this.summaryMapper, this.missionSummaryService);
    }

    @Test
    void shouldProperlyProcessSummaryRetrievalWithNoExceptionThrown() {
        // given
        var rocketWithNewStatusDto = new MissionSummaryDTO(
                "M-1",
                MissionStatus.SCHEDULED,
                0,
                Collections.emptyList());
        var rocketWithNewStatusDomain = new MissionSummary(
                "M-1",
                MissionStatus.SCHEDULED,
                0,
                Collections.emptyList());

        when(this.summaryMapper.toDto(rocketWithNewStatusDomain)).thenReturn(rocketWithNewStatusDto);

        // when
        missionsSummaryService.getSummary();

        // then
        Mockito.verify(missionSummaryService).getCurrentMissionsSummary();
    }
}
