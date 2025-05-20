package org.six;

import org.six.application.dto.MissionDTO;
import org.six.application.dto.MissionSummaryDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewMissionService;
import org.six.application.service.AddNewRocketService;
import org.six.application.service.AssignRocketsToMissionService;
import org.six.application.service.ChangeMissionStatusService;
import org.six.application.service.ChangeRocketStatusService;
import org.six.application.service.MissionsSummaryService;
import org.six.application.service.RemoveRocketAssignmentService;

import java.util.List;

public class RocketMissionManager {
    private final AddNewRocketService addNewRocketService;
    private final ChangeRocketStatusService changeRocketStatusService;
    private final AddNewMissionService addNewMissionService;
    private final ChangeMissionStatusService changeMissionStatusService;
    private final AssignRocketsToMissionService assignRocketsToMissionService;
    private final RemoveRocketAssignmentService removeRocketAssignmentService;
    private final MissionsSummaryService missionsSummaryService;

    public RocketMissionManager(AddNewRocketService addNewRocketService,
                                ChangeRocketStatusService changeRocketStatusService,
                                AddNewMissionService addNewMissionService,
                                ChangeMissionStatusService changeMissionStatusService,
                                AssignRocketsToMissionService assignRocketsToMissionService,
                                RemoveRocketAssignmentService removeRocketAssignmentService,
                                MissionsSummaryService missionsSummaryService) {
        this.addNewRocketService = addNewRocketService;
        this.changeRocketStatusService = changeRocketStatusService;
        this.addNewMissionService = addNewMissionService;
        this.changeMissionStatusService = changeMissionStatusService;
        this.assignRocketsToMissionService = assignRocketsToMissionService;
        this.removeRocketAssignmentService = removeRocketAssignmentService;
        this.missionsSummaryService = missionsSummaryService;
    }

    void addNewRocket(SimpleRocketDTO rocket) {
        addNewRocketService.execute(rocket);
    }

    void changeRocketStatus(RocketDTO rocketWithNewStatus) {
        changeRocketStatusService.execute(rocketWithNewStatus);
        changeMissionStatusService.execute(rocketWithNewStatus);
    }

    void addNewMission(NewMissionDTO mission) {
        addNewMissionService.execute(mission);
    }

    void changeMissionStatus(MissionDTO missionWithNewStatus) {
        changeMissionStatusService.execute(missionWithNewStatus);
        removeRocketAssignmentService.execute(missionWithNewStatus);
    }

    void assignRocketsToMission(RocketsToMissionAssignmentDTO assignment) {
        assignRocketsToMissionService.execute(assignment);
        changeMissionStatusService.execute(assignment.missionName());
    }

    List<MissionSummaryDTO> getCurrentMissionsSummary() {
        return missionsSummaryService.getSummary();
    }
}
