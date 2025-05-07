package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.impl.FileService;

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
    private final FileService fileService;


    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getFiles(
            @Parameter(description = "Имя файла для получения", required = true)
            @PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(adsDir, fileName);
        File file = filePath.toFile();
        byte[] img = Files.readAllBytes(file.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(file.toPath())));
        headers.setContentLength(img.length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(img);

    }


    @Operation(summary = "Загрузить файл", description = "Метод для загрузки файла на сервер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно загружен."),
            @ApiResponse(responseCode = "400", description = "Ошибка при загрузке файла.")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> saveFile(
            @Parameter(description = "Файл для загрузки", required = true)
            @RequestPart("file") MultipartFile file,

            @Parameter(description = "Имя файла", required = true)
            @RequestPart("name") String name) throws IOException {
        if (Boolean.parseBoolean(fileService.uploadFile("online-shop", name, file))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
