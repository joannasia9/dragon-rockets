package org.six;

import org.six.application.dto.MissionDTO;
import org.six.application.dto.MissionSummaryDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewRocketService;

import java.util.ArrayList;
import java.util.List;

public class RocketMissionManager {
    private final AddNewRocketService addNewRocketService;

    public RocketMissionManager(AddNewRocketService addNewRocketService) {
        this.addNewRocketService = addNewRocketService;
    }

    void addNewRocket(SimpleRocketDTO rocket) {
        addNewRocketService.execute(rocket);
    }

    void changeRocketStatus(RocketDTO rocketWithNewStatus) {
        //TODO: Implement
    }

    void addNewMission(NewMissionDTO mission) {
        //TODO: Implement
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
