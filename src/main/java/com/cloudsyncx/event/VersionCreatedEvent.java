package com.cloudsyncx.event;

import lombok.Getter;

@Getter
public class VersionCreatedEvent {

    private final Long fileId;

    private final Integer versionNumber;

    public VersionCreatedEvent(
            Long fileId,
            Integer versionNumber) {

        this.fileId = fileId;
        this.versionNumber = versionNumber;
    }
}