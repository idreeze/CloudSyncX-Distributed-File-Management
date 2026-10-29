package com.cloudsyncx.repository;

import com.cloudsyncx.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository
        extends JpaRepository<FileMetadata, Long> {

    List<FileMetadata> findByFileName(
            String fileName);
}