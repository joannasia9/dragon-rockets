package org.six.domain.service;

import org.six.domain.model.Rocket;

public interface RocketService {
    void addNewRocket(Rocket newRocket);

    void changeRocketStatus(Rocket rocketWithNewStatus);
}

