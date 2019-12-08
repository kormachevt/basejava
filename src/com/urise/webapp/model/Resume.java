package com.urise.webapp.model;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        if (uuid != null) {
            this.uuid = uuid;
        }
    }

    @Override
    public String toString() {
        return uuid;
    }
}
