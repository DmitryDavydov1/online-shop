package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class FileController {

    @Value("${avatars.dir}")
    private String adsDir;

    @GetMapping(value = "file/{filepath}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filepath) throws IOException {
        Path filePath = Paths.get(adsDir, filepath);
        File file = filePath.toFile();
        byte[] img = Files.readAllBytes(file.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(file.toPath())));
        headers.setContentLength(img.length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(img);

    }
}
