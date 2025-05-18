package org.six.domain.model;

import java.util.ArrayList;
import java.util.List;

public record Mission(
        String name,
        MissionStatus status,
        List<Rocket> rockets
) {

    public static Mission withDefaultStatus(String name) {
        return new Mission(name, MissionStatus.SCHEDULED, new ArrayList<>());
    }
}