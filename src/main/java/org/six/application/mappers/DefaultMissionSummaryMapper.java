package org.six.application.mappers;

import org.six.application.dto.MissionSummaryDTO;
import org.six.domain.model.MissionSummary;


public class DefaultMissionSummaryMapper implements MissionSummaryMapper {
    private final RocketMapper rocketMapper;

    public DefaultMissionSummaryMapper(RocketMapper rocketMapper) {
        this.rocketMapper = rocketMapper;
    }

    @Override
    public MissionSummaryDTO toDto(MissionSummary domain) {
        var rocketsDto = domain.rockets().stream()
                .map(rocketMapper::toDto)
                .toList();
        return new MissionSummaryDTO(
                domain.missionName(),
                domain.missionStatus(),
                domain.numberOfRocketsAssigned(),
                rocketsDto);
    }
}
