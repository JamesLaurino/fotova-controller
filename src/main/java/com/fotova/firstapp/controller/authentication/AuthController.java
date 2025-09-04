package com.fotova.firstapp.controller.authentication;

import com.fotova.dto.authentification.ResponseDto;
import com.fotova.dto.request.LoginRequest;
import com.fotova.dto.request.RegisterRequest;
import com.fotova.dto.request.ResetPasswordRequest;
import com.fotova.firstapp.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;


    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "200", description = "User registered successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))
            })
    @ApiResponse(responseCode = "400", description = "Username or email already registered",
            content = @Content)
    @PostMapping(path = "auth/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Reset user password")
    @ApiResponse(responseCode = "200", description = "Password reset successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))
            })
    @PostMapping("auth/password-reset")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    @Operation(summary = "Check register email")
    @ApiResponse(responseCode = "200", description = "Email checked successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))
            })
    @ApiResponse(responseCode = "500", description = "Invalid verification code",
            content = @Content)
    @GetMapping("auth/register/check")
    public ResponseEntity<Object> checkRegisterEmail(
            @Parameter(description = "order UUID", required = true)
            @RequestParam String uuid) {
        return ResponseEntity.ok(authService.registerAfterEmailConfirm(uuid));
    }

    @Operation(summary = "User login")
    @ApiResponse(responseCode = "200", description = "User logged in successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))
            })
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content)
    @PostMapping("auth/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Get current user")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))
            })
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content)
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUser() {
        return ResponseEntity.ok(authService.getUser());
    }
}
