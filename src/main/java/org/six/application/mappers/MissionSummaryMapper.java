package org.six.application.mappers;

import org.six.application.dto.MissionSummaryDTO;
import org.six.domain.model.MissionSummary;

public interface MissionSummaryMapper {
    MissionSummaryDTO toDto(MissionSummary domain);
}

class DefaultMissionSummaryMapper implements MissionSummaryMapper {

    @Override
    public MissionSummaryDTO toDto(MissionSummary domain) {
        return null;
    }
}
