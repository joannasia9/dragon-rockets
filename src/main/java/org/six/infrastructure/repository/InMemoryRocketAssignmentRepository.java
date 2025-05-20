package org.six.infrastructure.repository;

import org.six.port.repository.RocketToMissionAssignmentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryRocketAssignmentRepository implements RocketToMissionAssignmentRepository {
    private final Map<String, String> missionsByRocketNames = new ConcurrentHashMap<>();

    @Override
    public boolean isAssignedToMission(String rocketName) {
        return missionsByRocketNames.containsKey(rocketName);
    }

    @Override
    public Optional<String> findMissionFor(String rocketName) {
        return Optional.ofNullable(missionsByRocketNames.getOrDefault(rocketName, null));
    }

    @Override
    public List<String> findRocketsFor(String missionName) {
        return missionsByRocketNames.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(missionName))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void insertAssignment(String rocketName, String missionName) {
        missionsByRocketNames.putIfAbsent(rocketName, missionName);
    }

    @Override
    public void removeRocketsAssignedTo(String missionName) {
        missionsByRocketNames.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(missionName))
                .map(Map.Entry::getKey)
                .forEach(this::removeRocketAssignment);
    }

    private void removeRocketAssignment(String rocketName) {
        missionsByRocketNames.remove(rocketName);
    }
}
