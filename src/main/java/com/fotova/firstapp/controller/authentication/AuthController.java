package com.fotova.firstapp.controller.authentication;

import com.fotova.dto.authentification.AuthentificationDto;
import com.fotova.dto.authentification.ResponseDto;
import com.fotova.dto.request.LoginRequest;
import com.fotova.dto.request.RegisterRequest;
import com.fotova.firstapp.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("auth/login/demo")
    public ResponseDto auth(@RequestBody AuthentificationDto authentication) {

        ResponseDto responseDto = new ResponseDto();

        if(authentication.getUsername().equals("admin") && authentication.getPassword().equals("admin")){
            responseDto.setAuthenticate(true);
            return responseDto;
        }
        else {
            responseDto.setAuthenticate(false);
            return responseDto;
        }
    }

    @PostMapping(path = "auth/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("auth/register/check")
    public ResponseEntity<Object> checkRegisterEmail(@RequestParam String uuid) {
        return ResponseEntity.ok(authService.registerAfterEmailConfirm(uuid));
    }

    @PostMapping("auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUser() {
        return ResponseEntity.ok(authService.getUser());
    }
}
