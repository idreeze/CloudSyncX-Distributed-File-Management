package com.cloudsyncx.repository;

import com.cloudsyncx.entity.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileVersionRepository
        extends JpaRepository<FileVersion, Long> {

    List<FileVersion> findByFileId(Long fileId);

    Optional<FileVersion> findTopByFileIdOrderByVersionNumberDesc(
            Long fileId);
}