package com.cloudsyncx.controller;

import com.cloudsyncx.entity.FileMetadata;
import com.cloudsyncx.service.FileService;
import com.cloudsyncx.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final VersionService versionService;

    @GetMapping
    public List<FileMetadata> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/{id}")
    public FileMetadata getFile(@PathVariable Long id) {
        return fileService.getFile(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileService.delete(id);
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        String uploadDir =
                System.getProperty("user.dir")
                        + File.separator
                        + "uploads";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destination =
                new File(directory,
                        file.getOriginalFilename());

        file.transferTo(destination);

        FileMetadata existingFile =
                fileService.findByFileName(
                        file.getOriginalFilename());

        if (existingFile == null) {

            FileMetadata metadata =
                    FileMetadata.builder()
                            .fileName(file.getOriginalFilename())
                            .s3Url(destination.getAbsolutePath())
                            .currentVersion(1)
                            .ownerId(1L)
                            .build();

            FileMetadata savedFile =
                    fileService.save(metadata);

            versionService.createVersion(
                    savedFile.getId(),
                    1,
                    "Initial Upload");

        } else {

            existingFile.setCurrentVersion(
                    existingFile.getCurrentVersion() + 1);

            existingFile.setS3Url(
                    destination.getAbsolutePath());

            fileService.save(existingFile);

            versionService.createVersion(
                    existingFile.getId(),
                    existingFile.getCurrentVersion(),
                    "Updated Version");
        }

        return "Uploaded Successfully : "
                + file.getOriginalFilename();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id)
            throws MalformedURLException {

        FileMetadata metadata =
                fileService.getFile(id);

        Path path =
                Paths.get(metadata.getS3Url());

        Resource resource =
                new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + resource.getFilename()
                                + "\"")
                .body(resource);
    }
}