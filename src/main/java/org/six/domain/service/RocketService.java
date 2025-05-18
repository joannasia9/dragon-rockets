package org.six.domain.service;

import org.six.domain.model.RocketStatus;

public interface RocketService {
    void addNewRocket(String rocketName);

    void changeRocketStatus(String rocketName, RocketStatus newStatus);
}

