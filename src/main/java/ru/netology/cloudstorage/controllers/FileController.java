package ru.netology.cloudstorage.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.FileRequest;
import ru.netology.cloudstorage.dto.FileResponse;
import ru.netology.cloudstorage.service.AuthorizationService;
import ru.netology.cloudstorage.service.FileService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
@Validated
public class FileController {
    private final FileService fileService;
    private final AuthorizationService authorizationService;

    public FileController(FileService fileService, AuthorizationService authorizationService) {
        this.fileService = fileService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileResponse>> getFileList(@RequestHeader("auth-token") @NotBlank String authToken,
                                                          @RequestParam @Min(1) int limit) {
        authorizationService.checkToken(authToken);
        return ResponseEntity.ok(fileService.getFileList(limit));
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestHeader("auth-token") @NotBlank String authToken,
                                          @RequestParam @NotBlank String filename) {
        authorizationService.checkToken(authToken);
        return ResponseEntity.ok(fileService.getFile(filename));
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") @NotBlank String authToken,
                                        @RequestParam @NotBlank String filename,
                                        @NotNull @RequestBody MultipartFile file) throws IOException {
        authorizationService.checkToken(authToken);
        fileService.addFile(filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/file")
    public ResponseEntity<?> renameFile(@RequestHeader("auth-token") @NotBlank String authToken,
                                        @RequestParam @NotBlank String filename,
                                        @RequestBody @Valid FileRequest newFileName) {
        authorizationService.checkToken(authToken);
        fileService.renameFile(filename, newFileName.getFilename());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") @NotBlank String authToken,
                                        @RequestParam @NotBlank String filename) {
        authorizationService.checkToken(authToken);
        fileService.deleteFile(filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
