package org.six.domain.model;

public enum RocketStatus {

    ON_GROUND("On ground"),
    IN_SPACE("In space"),
    IN_REPAIR("In repair");

    private final String label;

    RocketStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
