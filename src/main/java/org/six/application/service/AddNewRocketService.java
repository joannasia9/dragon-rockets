package org.six.application.service;

import org.six.application.dto.SimpleRocketDTO;
import org.six.application.mappers.RocketMapper;
import org.six.domain.service.RocketService;

public class AddNewRocketService {
    private final RocketMapper rocketMapper;
    private final RocketService rocketService;

    public AddNewRocketService(RocketMapper rocketMapper, RocketService rocketService) {
        this.rocketMapper = rocketMapper;
        this.rocketService = rocketService;
    }

    public void execute(SimpleRocketDTO rocket) {
        var rocketToCreate = rocketMapper.toDomain(rocket);
        rocketService.addNewRocket(rocketToCreate);
    }
}
