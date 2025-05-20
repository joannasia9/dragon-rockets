package org.six;

import org.six.application.dto.MissionDTO;
import org.six.application.dto.MissionSummaryDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewMissionService;
import org.six.application.service.AddNewRocketService;
import org.six.application.service.ChangeRocketStatusService;

import java.util.ArrayList;
import java.util.List;

public class RocketMissionManager {
    private final AddNewRocketService addNewRocketService;
    private final ChangeRocketStatusService changeRocketStatusService;
    private final AddNewMissionService addNewMissionService;

    public RocketMissionManager(AddNewRocketService addNewRocketService,
                                ChangeRocketStatusService changeRocketStatusService,
                                AddNewMissionService addNewMissionService) {
        this.addNewRocketService = addNewRocketService;
        this.changeRocketStatusService = changeRocketStatusService;
        this.addNewMissionService = addNewMissionService;
    }

    void addNewRocket(SimpleRocketDTO rocket) {
        addNewRocketService.execute(rocket);
    }

    void changeRocketStatus(RocketDTO rocketWithNewStatus) {
        changeRocketStatusService.execute(rocketWithNewStatus);
        // TODO: Update mission status if needed
    }

    void addNewMission(NewMissionDTO mission) {
        addNewMissionService.execute(mission);
    }

    void changeMissionStatus(MissionDTO missionWithNewStatus) {
        //TODO: Implement
    }

    void assignRocketsToMission(RocketsToMissionAssignmentDTO assignment) {
        //TODO: Implement
    }

    List<MissionSummaryDTO> getCurrentMissionsSummary() {
        //TODO: Implement
        return new ArrayList<>();
    }

}
