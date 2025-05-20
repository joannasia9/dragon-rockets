package org.six.port.repository;

import org.six.domain.model.Mission;

import java.util.List;
import java.util.Optional;

public interface MissionRepository {
    void insert(Mission mission);

    void update(Mission mission);

    List<Mission> findAll();

    Optional<Mission> findByName(String name);

    boolean existsByName(String name);
}
