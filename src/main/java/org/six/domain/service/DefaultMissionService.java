package org.six.domain.service;

import org.six.domain.exception.MissionAlreadyExistsException;
import org.six.domain.exception.MissionNotExistsException;
import org.six.domain.exception.MissionStatusChangeNotAllowedException;
import org.six.domain.model.Mission;
import org.six.domain.model.MissionStatus;
import org.six.domain.model.Rocket;
import org.six.domain.model.RocketStatus;
import org.six.domain.validation.MissionStatusUpdateValidator;
import org.six.port.repository.MissionRepository;
import org.six.port.repository.RocketRepository;
import org.six.port.repository.RocketToMissionAssignmentRepository;

import java.util.Optional;

public final class DefaultMissionService implements MissionService {
    private final MissionRepository missionRepository;
    private final RocketRepository rocketRepository;
    private final RocketToMissionAssignmentRepository assignmentRepository;
    private final MissionStatusUpdateValidator missionStatusUpdateValidator;

    public DefaultMissionService(MissionRepository missionRepository,
                                 RocketRepository rocketRepository, RocketToMissionAssignmentRepository assignmentRepository,
                                 MissionStatusUpdateValidator missionStatusUpdateValidator) {
        this.missionRepository = missionRepository;
        this.rocketRepository = rocketRepository;
        this.assignmentRepository = assignmentRepository;
        this.missionStatusUpdateValidator = missionStatusUpdateValidator;
    }


    @Override
    public void addNewMission(Mission mission) {
        if (missionRepository.existsByName(mission.name())) {
            throw new MissionAlreadyExistsException(mission.name());
        }
        missionRepository.insert(mission);
    }

    @Override
    public void changeMissionStatus(Mission missionWithNewStatus) {
        var existingMission = missionRepository.findByName(missionWithNewStatus.name())
                .orElseThrow(() -> new MissionNotExistsException(missionWithNewStatus.name()));

        if (!missionStatusUpdateValidator.isValidUpdate(existingMission, missionWithNewStatus.status()))
            throw new MissionStatusChangeNotAllowedException(missionWithNewStatus.name(), missionWithNewStatus.status());

        missionRepository.update(missionWithNewStatus);
    }

    @Override
    public void changeMissionStatusIfNeeded(Rocket rocketWithNewStatus) {
        assignmentRepository.findMissionFor(rocketWithNewStatus.name())
                .flatMap(missionRepository::findByName)
                .ifPresent(
                        assignedMission -> updateMissionStatusIfPossible(assignedMission, newPotentialMissionStatus(rocketWithNewStatus))
                );
    }

    @Override
    public void changeMissionStatusIfNeeded(String missionName) {
        var mission = missionRepository.findByName(missionName)
                .orElseThrow(() -> new MissionNotExistsException(missionName));

        if (mission.status() == MissionStatus.ENDED)
            return;

        var rocketsAssignedToMission = assignmentRepository.findRocketsFor(mission.name())
                .stream()
                .map(rocketRepository::findByName)
                .flatMap(Optional::stream)
                .toList();
        var anyInRepairStatus = rocketsAssignedToMission.stream()
                .map(Rocket::status)
                .anyMatch(rs -> rs == RocketStatus.IN_REPAIR);

        if (!rocketsAssignedToMission.isEmpty()) {
            var newStatus = anyInRepairStatus ? MissionStatus.PENDING : MissionStatus.IN_PROGRESS;
            missionRepository.update(new Mission(mission.name(), newStatus));
        } else {
            missionRepository.update(Mission.withDefaultStatus(mission.name()));
        }
    }

    MissionStatus newPotentialMissionStatus(Rocket rocketWithNewStatus) {
        if (rocketWithNewStatus.status() == RocketStatus.IN_REPAIR)
            return MissionStatus.PENDING;
        return MissionStatus.IN_PROGRESS;
    }

    void updateMissionStatusIfPossible(Mission currentMission, MissionStatus newPotentialStatus) {
        if (missionStatusUpdateValidator.isValidUpdate(currentMission, newPotentialStatus)) {
            missionRepository.update(new Mission(currentMission.name(), newPotentialStatus));
        }
    }
}
