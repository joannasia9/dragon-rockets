package org.six.domain.model;


public record Mission(
        String name,
        MissionStatus status
) {

    public static Mission withDefaultStatus(String name) {
        return new Mission(name, MissionStatus.SCHEDULED);
    }
}