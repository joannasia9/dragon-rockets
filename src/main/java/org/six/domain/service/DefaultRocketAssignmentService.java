package org.six.domain.service;

import org.six.domain.exception.MissionNotExistsException;
import org.six.domain.exception.RocketAlreadyAssignedException;
import org.six.domain.exception.RocketNotExistsException;
import org.six.domain.exception.RocketToMissionAssignmentNotAllowedException;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.Rocket;
import org.six.port.repository.MissionRepository;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import java.util.Set;

public class DefaultRocketAssignmentService implements RocketAssignmentService {

    private final MissionRepository missionRepository;
    private final RocketRepository rocketRepository;
    private final RocketToMissionAssignmentRepository assignmentRepository;

    public DefaultRocketAssignmentService(MissionRepository missionRepository,
                                          RocketRepository rocketRepository, RocketToMissionAssignmentRepository assignmentRepository) {
        this.missionRepository = missionRepository;
        this.rocketRepository = rocketRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public void removeRocketsAssignmentForMissionIfNeeded(Mission mission) {
        if (mission.status() == MissionStatus.ENDED)
            assignmentRepository.removeRocketsAssignedTo(mission.name());
    }

    @Override
    public void assignRocketsToMission(String missionName, Set<Rocket> rocketsToBeAssigned) {
        var mission = missionRepository.findByName(missionName)
                .orElseThrow(() -> new MissionNotExistsException(missionName));

        if (mission.status() == MissionStatus.ENDED)
            throw new RocketToMissionAssignmentNotAllowedException(missionName);

        rocketsToBeAssigned.forEach(rocket -> {
            if (assignmentRepository.isAssignedToMission(rocket.name()))
                throw new RocketAlreadyAssignedException(rocket.name());

            if (!rocketRepository.existsByName(rocket.name()))
                throw new RocketNotExistsException(rocket.name());

            assignmentRepository.insertAssignment(rocket.name(), mission.name());
        });
    }
}
