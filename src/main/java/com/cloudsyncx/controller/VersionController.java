package com.cloudsyncx.controller;

import com.cloudsyncx.entity.FileVersion;
import com.cloudsyncx.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/versions")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping("/{fileId}")
    public List<FileVersion> getVersions(
            @PathVariable Long fileId) {

        return versionService.getVersions(fileId);
    }
    @PostMapping("/rollback/{fileId}/{versionNumber}")
    public FileVersion rollback(
            @PathVariable Long fileId,
            @PathVariable Integer versionNumber) {

        return versionService.rollbackVersion(
                fileId,
                versionNumber);
    }
    @PostMapping("/create")
    public FileVersion createVersion(

            @RequestParam Long fileId,

            @RequestParam Integer versionNumber,

            @RequestParam String description) {

        return versionService.createVersion(
                fileId,
                versionNumber,
                    description);
    }
}