package org.six.domain.service;

import org.six.domain.model.Mission;
import org.six.domain.model.MissionSummary;
import org.six.port.repository.MissionRepository;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DefaultMissionSummaryService implements MissionSummaryService {
    private final RocketRepository rocketRepository;
    private final MissionRepository missionRepository;
    private final RocketToMissionAssignmentRepository assignmentRepository;

    public DefaultMissionSummaryService(RocketRepository rocketRepository, MissionRepository missionRepository, RocketToMissionAssignmentRepository assignmentRepository) {
        this.rocketRepository = rocketRepository;
        this.missionRepository = missionRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public List<MissionSummary> getCurrentMissionsSummary() {
        return missionRepository.findAll()
                .stream()
                .map(this::generateSummary)
                .sorted(
                        Comparator
                                .comparingInt(MissionSummary::numberOfRocketsAssigned)
                                .reversed()
                                .thenComparing(MissionSummary::missionName, Comparator.reverseOrder())
                )
                .toList();
    }

    private MissionSummary generateSummary(Mission mission) {
        var rockets = assignmentRepository.findRocketsFor(mission.name())
                .stream()
                .map(rocketRepository::findByName)
                .flatMap(Optional::stream)
                .toList();
        return new MissionSummary(
                mission.name(),
                mission.status(),
                rockets.size(),
                rockets
        );
    }
}
