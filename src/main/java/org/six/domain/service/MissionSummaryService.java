package org.six.domain.service;

import org.six.domain.model.MissionSummary;

import java.util.List;

public interface MissionSummaryService {
    List<MissionSummary> getCurrentMissionsSummary();
}
