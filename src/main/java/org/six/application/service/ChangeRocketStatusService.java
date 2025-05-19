package org.six.application.service;

import org.six.application.dto.RocketDTO;
import org.six.application.mappers.RocketMapper;
import org.six.domain.service.RocketService;

public class ChangeRocketStatusService {
    private final RocketMapper rocketMapper;
    private final RocketService rocketService;

    public ChangeRocketStatusService(RocketMapper rocketMapper, RocketService rocketService) {
        this.rocketMapper = rocketMapper;
        this.rocketService = rocketService;
    }

    public void execute(RocketDTO rocketWithNewStatus) {
        rocketService.changeRocketStatus(rocketMapper.toDomain(rocketWithNewStatus));
    }
}
