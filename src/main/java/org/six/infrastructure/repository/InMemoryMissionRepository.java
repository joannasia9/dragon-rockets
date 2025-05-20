package org.six.infrastructure.repository;

import org.six.domain.model.Mission;
import org.six.port.repository.MissionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMissionRepository implements MissionRepository {
    private final Map<String, Mission> missions = new ConcurrentHashMap<>();

    @Override
    public void insert(Mission mission) {
        missions.putIfAbsent(mission.name(), mission);
    }

    @Override
    public void update(Mission mission) {
        if (missions.containsKey(mission.name()))
            missions.put(mission.name(), mission);
    }

    @Override
    public List<Mission> findAll() {
        return missions.values().stream()
                .toList();
    }

    @Override
    public Optional<Mission> findByName(String name) {
        return Optional.ofNullable(this.missions.getOrDefault(name, null));
    }

    @Override
    public boolean existsByName(String name) {
        return missions.containsKey(name);
    }
}
