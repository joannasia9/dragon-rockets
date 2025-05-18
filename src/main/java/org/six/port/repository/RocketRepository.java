package org.six.port.repository;

import org.six.domain.model.Rocket;

import java.util.List;
import java.util.Optional;

public interface RocketRepository {
    void insert(Rocket rocket);

    void update(Rocket rocket);

    List<Rocket> findAll();

    Optional<Rocket> findByName(String name);
}
