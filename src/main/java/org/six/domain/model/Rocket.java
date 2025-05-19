package org.six.domain.model;

public record Rocket(
        String name,
        RocketStatus status
) {
    public static Rocket withDefaultStatus(String name) {
        return new Rocket(name, RocketStatus.ON_GROUND);
    }
}
