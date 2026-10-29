package com.cloudsyncx.service;

import com.cloudsyncx.entity.FileMetadata;
import com.cloudsyncx.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileMetadata save(FileMetadata file) {

        FileMetadata saved =
                fileRepository.save(file);

        System.out.println(
                "FILE SAVED : "
                        + saved.getFileName());

        return saved;
    }

    public List<FileMetadata> getAllFiles() {
        return fileRepository.findAll();
    }

    public FileMetadata getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow();
    }

    public void delete(Long id) {
        fileRepository.deleteById(id);
    }

    public FileMetadata findByFileName(
            String fileName) {

        List<FileMetadata> files =
                fileRepository.findByFileName(fileName);

        if (files.isEmpty()) {
            return null;
        }

        return files.get(0);
    }
}