package com.fotova.firstapp.controller.file;


import com.fotova.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${file.upload.path}")
    private String pathFile;

    @PostMapping("auth/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @GetMapping("auth/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        return ResponseEntity.ok(fileService.getFileByName(filename).getBody());
    }
}