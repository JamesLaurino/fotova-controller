package com.fotova.firstapp.controller.file;


import com.fotova.dto.comment.CommentDto;
import com.fotova.dto.file.FileDto;
import com.fotova.dto.file.FileResponseDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${file.upload.path}")
    private String pathFile;

    @PostMapping("auth/file/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("File upload successfully")
                .data(fileService.uploadFile(file))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/files")
    public ResponseEntity<Object> getAllFiles() throws IOException {
        Response<FileDto> response = Response.<FileDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("File retrieved successfully")
                .data(fileService.getAllFiles())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/file/{filename}")
    public ResponseEntity<Object> getFileByName(@PathVariable String filename) throws IOException {
        // TODO CHECK FOR TEST
        return ResponseEntity.ok(fileService.getFileByName(filename).getBody());
    }

    @GetMapping("auth/files/content")
    public ResponseEntity<Object> getAllFilesContent() {
        Response<List<FileResponseDto>> response = Response.<List<FileResponseDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("File retrieved successfully")
                .data(fileService.getAllFilesContent())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}