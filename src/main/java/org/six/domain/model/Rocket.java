package org.six.domain.model;

public record Rocket(
        String name,
        RocketStatus status,

        Mission mission
) {
    public static Rocket withDefaultStatus(String name) {
        return new Rocket(name, RocketStatus.ON_GROUND, null);
    }
}
