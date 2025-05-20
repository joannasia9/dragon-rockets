package org.six.application.service;

import org.six.application.dto.MissionSummaryDTO;
import org.six.application.mappers.MissionSummaryMapper;
import org.six.domain.service.MissionSummaryService;

import java.util.List;

public class MissionsSummaryService {
    private final MissionSummaryMapper missionSummaryMapper;
    private final MissionSummaryService missionSummaryService;

    public MissionsSummaryService(MissionSummaryMapper missionSummaryMapper,
                                  MissionSummaryService missionSummaryService) {
        this.missionSummaryMapper = missionSummaryMapper;
        this.missionSummaryService = missionSummaryService;
    }

    public List<MissionSummaryDTO> getSummary() {
        return missionSummaryService.getCurrentMissionsSummary()
                .stream()
                .map(missionSummaryMapper::toDto)
                .toList();
    }
}
