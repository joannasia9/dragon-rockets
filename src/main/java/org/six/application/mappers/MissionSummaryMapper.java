package org.six.application.mappers;

import org.six.application.dto.MissionSummaryDTO;
import org.six.domain.model.MissionSummary;

public interface MissionSummaryMapper {
    MissionSummaryDTO toDto(MissionSummary domain);
}

