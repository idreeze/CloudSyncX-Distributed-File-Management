package com.cloudsyncx.service;

import com.cloudsyncx.entity.FileVersion;
import com.cloudsyncx.event.VersionCreatedEvent;
import com.cloudsyncx.repository.FileVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final FileVersionRepository versionRepository;

    private final ApplicationEventPublisher publisher;

    public FileVersion createVersion(
            Long fileId,
            Integer versionNumber,
            String description) {

        FileVersion version =
                FileVersion.builder()
                        .fileId(fileId)
                        .versionNumber(versionNumber)
                        .uploadedAt(LocalDateTime.now())
                        .changeDescription(description)
                        .build();

        FileVersion savedVersion =
                versionRepository.save(version);

        publisher.publishEvent(
                new VersionCreatedEvent(
                        fileId,
                        versionNumber
                )
        );

        return savedVersion;
    }

    public List<FileVersion> getVersions(Long fileId) {
        return versionRepository.findByFileId(fileId);
    }

    public FileVersion rollbackVersion(
            Long fileId,
            Integer versionNumber) {

        List<FileVersion> versions =
                versionRepository.findByFileId(fileId);

        return versions.stream()
                .filter(v ->
                        v.getVersionNumber()
                                .equals(versionNumber))
                .findFirst()
                .orElseThrow();
    }
}