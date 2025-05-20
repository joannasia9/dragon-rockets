package org.six.domain.service;

import org.six.domain.exception.RocketAlreadyExistsException;
import org.six.domain.exception.RocketNotExistsException;
import org.six.domain.exception.RocketStatusChangeNotAllowedException;
import org.six.domain.model.Rocket;
import org.six.domain.validation.RocketStatusUpdateValidator;
import org.six.port.repository.RocketRepository;

public class DefaultRocketService implements RocketService {
    private final RocketStatusUpdateValidator rocketStatusUpdateValidator;
    private final RocketRepository rocketRepository;

    public DefaultRocketService(RocketStatusUpdateValidator rocketStatusUpdateValidator,
                                RocketRepository rocketRepository) {
        this.rocketStatusUpdateValidator = rocketStatusUpdateValidator;
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
        updateRocketStatusIfPossible(rocketWithNewStatus);
    }

    void updateRocketStatusIfPossible(Rocket rocketWithNewStatus) {
        if (!rocketStatusUpdateValidator.isValidUpdate(rocketWithNewStatus))
            throw new RocketStatusChangeNotAllowedException(rocketWithNewStatus.name(), rocketWithNewStatus.status());
        rocketRepository.update(rocketWithNewStatus);
    }
}
