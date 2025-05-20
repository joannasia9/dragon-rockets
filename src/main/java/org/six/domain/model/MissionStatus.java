package org.six.domain.model;

public enum MissionStatus {
    SCHEDULED("Scheduled"),
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    ENDED("Ended");

    private final String label;

    MissionStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
