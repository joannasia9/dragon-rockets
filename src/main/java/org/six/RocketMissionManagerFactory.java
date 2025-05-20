package org.six;

import org.six.application.mappers.DefaultMissionMapper;
import org.six.application.mappers.DefaultMissionSummaryMapper;
import org.six.application.mappers.DefaultRocketMapper;
import org.six.application.service.AddNewMissionService;
import org.six.application.service.AddNewRocketService;
import org.six.application.service.AssignRocketsToMissionService;
import org.six.application.service.ChangeMissionStatusService;
import org.six.application.service.ChangeRocketStatusService;
import org.six.application.service.MissionsSummaryService;
import org.six.application.service.RemoveRocketAssignmentService;
import org.six.domain.service.DefaultMissionService;
import org.six.domain.service.DefaultMissionSummaryService;
import org.six.domain.service.DefaultRocketAssignmentService;
import org.six.domain.service.DefaultRocketService;
import org.six.domain.validation.MissionStatusUpdateValidator;
import org.six.domain.validation.RocketStatusUpdateValidator;
import org.six.infrastructure.repository.InMemoryMissionRepository;
import org.six.infrastructure.repository.InMemoryRocketAssignmentRepository;
import org.six.infrastructure.repository.InMemoryRocketRepository;

public class RocketMissionManagerFactory {
    public static RocketMissionManager create() {
        var rocketMapper = new DefaultRocketMapper();
        var missionMapper = new DefaultMissionMapper();
        var summaryMapper = new DefaultMissionSummaryMapper(rocketMapper);

        var rocketRepository = new InMemoryRocketRepository();
        var missionRepository = new InMemoryMissionRepository();
        var assignmentRepository = new InMemoryRocketAssignmentRepository();

        var rocketStatusUpdateValidator = new RocketStatusUpdateValidator(assignmentRepository);
        var missionStatusUpdateValidator = new MissionStatusUpdateValidator(rocketRepository, assignmentRepository);

        var rocketService = new DefaultRocketService(
                rocketStatusUpdateValidator,
                rocketRepository
        );
        var missionService = new DefaultMissionService(
                missionRepository,
                rocketRepository,
                assignmentRepository,
                missionStatusUpdateValidator
        );
        var rocketAssignmentService = new DefaultRocketAssignmentService(missionRepository, rocketRepository, assignmentRepository);
        var missionSummaryService = new DefaultMissionSummaryService(rocketRepository, missionRepository, assignmentRepository);

        AddNewRocketService addNewRocketService = new AddNewRocketService(rocketMapper, rocketService);
        ChangeRocketStatusService changeRocketStatusService = new ChangeRocketStatusService(rocketMapper, rocketService);
        AddNewMissionService addNewMissionService = new AddNewMissionService(missionMapper, missionService);
        ChangeMissionStatusService changeMissionStatusService = new ChangeMissionStatusService(missionMapper, missionService, rocketMapper);
        AssignRocketsToMissionService assignRocketsToMissionService = new AssignRocketsToMissionService(rocketMapper, rocketAssignmentService);
        RemoveRocketAssignmentService removeRocketAssignmentService = new RemoveRocketAssignmentService(missionMapper, rocketAssignmentService);
        MissionsSummaryService missionsSummaryService = new MissionsSummaryService(summaryMapper, missionSummaryService);

        return new RocketMissionManager(
                addNewRocketService,
                changeRocketStatusService,
                addNewMissionService,
                changeMissionStatusService,
                assignRocketsToMissionService,
                removeRocketAssignmentService,
                missionsSummaryService
        );
    }
}
