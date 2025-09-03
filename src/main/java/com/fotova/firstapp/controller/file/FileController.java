package com.fotova.firstapp.controller.file;


import com.fotova.dto.file.FileDto;
import com.fotova.dto.file.FileResponseDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Upload a file")
    @ApiResponse(responseCode = "200", description = "File uploaded successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            })
    @ApiResponse(responseCode = "500", description = "Error uploading file",
            content = @Content)
    @PostMapping("auth/file/upload")
    public ResponseEntity<Object> uploadFile(
            @Parameter(description = "file to upload", required = true, example = "file.png")
            @RequestParam("file") MultipartFile file) throws IOException {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("File upload successfully")
                .data(fileService.uploadFile(file))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all files")
    @ApiResponse(responseCode = "200", description = "Files retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = FileDto.class))
            })
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

    @Operation(summary = "Get a file by its filename")
    @ApiResponse(responseCode = "200", description = "File retrieved successfully",
            content = {
                    @Content(mediaType = "application/octet-stream", schema =
                    @Schema(type = "string", format = "binary"))
            })
    @ApiResponse(responseCode = "404", description = "File not found",
            content = @Content)
    @ApiResponse(responseCode = "500", description = "Error retrieving file",
            content = @Content)
    @GetMapping("auth/file/{filename}")
    public ResponseEntity<Object> getFileByName(
            @Parameter(description = "file name", required = true, example = "test.png")
            @PathVariable String filename) throws IOException {
        // TODO CHECK FOR TEST
        return ResponseEntity.ok(fileService.getFileByName(filename).getBody());
    }

    @Operation(summary = "Get content of all files")
    @ApiResponse(responseCode = "200", description = "File content retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = FileResponseDto.class))
            })
    @ApiResponse(responseCode = "500", description = "Error retrieving file content",
            content = @Content)
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