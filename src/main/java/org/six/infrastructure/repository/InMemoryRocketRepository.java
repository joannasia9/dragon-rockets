package org.six.infrastructure.repository;

import org.six.domain.model.Rocket;
import org.six.port.repository.RocketRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRocketRepository implements RocketRepository {
    private final Map<String, Rocket> rockets = new ConcurrentHashMap<>();

    @Override
    public boolean existsByName(String name) {
        return rockets.containsKey(name);
    }

    @Override
    public void insert(Rocket rocket) {
        rockets.putIfAbsent(rocket.name(), rocket);
    }

    @Override
    public void update(Rocket rocket) {
        if (rockets.containsKey(rocket.name()))
            rockets.put(rocket.name(), rocket);
    }

    @Override
    public List<Rocket> findAll() {
        return rockets.values().stream()
                .toList();
    }

    @Override
    public Optional<Rocket> findByName(String name) {
        return Optional.ofNullable(this.rockets.getOrDefault(name, null));
    }
}
