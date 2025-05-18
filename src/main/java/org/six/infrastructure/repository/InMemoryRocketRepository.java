package org.six.infrastructure.repository;

import org.six.domain.model.Rocket;
import org.six.port.repository.RocketRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryRocketRepository implements RocketRepository {
    @Override
    public void insert(Rocket rocket) {
        // TODO: Implement
    }

    @Override
    public void update(Rocket rocket) {
        // TODO: Implement
    }

    @Override
    public List<Rocket> findAll() {
        // TODO: Implement
        return null;
    }

    @Override
    public Optional<Rocket> findByName(String name) {
        // TODO: Implement
        return Optional.empty();
    }
}
