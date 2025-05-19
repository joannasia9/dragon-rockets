package org.six.domain.service;

import org.six.domain.exception.RocketAlreadyExistsException;
import org.six.domain.exception.RocketNotExistsException;
import org.six.domain.model.Rocket;
import org.six.port.repository.RocketRepository;

public interface RocketService {
    void addNewRocket(Rocket newRocket);

    void changeRocketStatus(Rocket rocketWithNewStatus);
}

class DefaultRocketService implements RocketService {
    private final RocketRepository rocketRepository;

    public DefaultRocketService(RocketRepository rocketRepository) {
        this.rocketRepository = rocketRepository;
    }

    @Override
    public void addNewRocket(Rocket newRocket) {
        if (rocketRepository.existsByName(newRocket.name())) {
            throw new RocketAlreadyExistsException(newRocket.name());
        }
        rocketRepository.insert(newRocket);
    }

    @Override
    public void changeRocketStatus(Rocket rocketWithNewStatus) {
        if (!rocketRepository.existsByName(rocketWithNewStatus.name())) {
            throw new RocketNotExistsException(rocketWithNewStatus.name());
        }
        rocketRepository.update(rocketWithNewStatus);
    }
}
