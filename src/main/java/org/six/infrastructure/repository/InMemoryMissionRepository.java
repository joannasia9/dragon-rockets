package org.six.infrastructure.repository;

import org.six.domain.model.Mission;
import org.six.port.repository.MissionRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryMissionRepository implements MissionRepository {

    @Override
    public void insert(Mission mission) {
        // TODO: Implement
    }

    @Override
    public void update(Mission mission) {
        // TODO: Implement
    }

    @Override
    public List<Mission> findAll() {
        // TODO: Implement
        return null;
    }

    @Override
    public Optional<Mission> findByName(String name) {
        // TODO: Implement
        return Optional.empty();
    }
}
