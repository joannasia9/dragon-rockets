package org.six;

import org.six.application.dto.MissionDTO;
import org.six.application.dto.MissionSummaryDTO;
import org.six.application.dto.NewMissionDTO;
import org.six.application.dto.RocketDTO;
import org.six.application.dto.RocketsToMissionAssignmentDTO;
import org.six.application.dto.SimpleRocketDTO;
import org.six.application.service.AddNewRocketService;
import org.six.application.service.ChangeRocketStatusService;

import java.util.ArrayList;
import java.util.List;

public class RocketMissionManager {
    private final AddNewRocketService addNewRocketService;
    private final ChangeRocketStatusService changeRocketStatusService;

    public RocketMissionManager(AddNewRocketService addNewRocketService,
                                ChangeRocketStatusService changeRocketStatusService) {
        this.addNewRocketService = addNewRocketService;
        this.changeRocketStatusService = changeRocketStatusService;
    }

    void addNewRocket(SimpleRocketDTO rocket) {
        addNewRocketService.execute(rocket);
    }

    void changeRocketStatus(RocketDTO rocketWithNewStatus) {
        changeRocketStatusService.execute(rocketWithNewStatus);
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
