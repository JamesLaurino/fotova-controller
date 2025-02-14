package com.fotova.firstapp.security.service;

import com.fotova.entity.ClientEntity;
import com.fotova.entity.ERole;
import com.fotova.entity.RoleEntity;
import com.fotova.firstapp.security.config.jwt.JwtUtils;
import com.fotova.dto.request.LoginRequest;
import com.fotova.dto.request.RegisterRequest;
import com.fotova.dto.response.LoginResponse;
import com.fotova.dto.response.RegisterUserResponse;
import com.fotova.dto.response.UserResponse;
import com.fotova.firstapp.security.service.user.UserDetailsImpl;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.repository.client.ClientRepositoryImpl;
import com.fotova.repository.role.RoleRepositoryImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private ClientRepositoryImpl userRepository;

    @Autowired
    private RoleRepositoryImpl roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @Transactional
    public Response<Object> register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        // generate bcrypt password
        String hashedPassword = encoder.encode(request.getPassword());

        // Define User instance, then set new value
        ClientEntity user = new ClientEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setIsActive(true);

        // Set default role to ROLE_ADMIN
        RoleEntity adminRole = new RoleEntity(ERole.ROLE_ADMIN); // Create the Role instance
        roleRepository.save(adminRole); // Save it to the database

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(adminRole); // Add the persisted Role

        user.setRoles(roles);

        // save user
        userRepository.save(user);

        // return response DTO
        RegisterUserResponse registerUserResponse = RegisterUserResponse.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .build();

        return Response.builder()
                .responseCode(200)
                .responseMessage("SUCCESS")
                .data(registerUserResponse)
                .build();
    }

    // Login Function
    @Transactional
    public Response<Object> login(LoginRequest request) {

        // Check if User by Email exist. if not throw error
        userRepository.findFirstByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found. Please register first"));


        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LoginResponse loginResponse = LoginResponse.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .accessToken(jwt)
                .tokenType("Bearer")
                .build();

        return Response.builder()
                .responseCode(200)
                .responseMessage("SUCCESS")
                .data(loginResponse)
                .build();
    }

    // GET USER THAT CURRENTLY LOGIN
    @Transactional
    public Response<Object> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer userId = userDetails.getId();

        ClientEntity user = userRepository.findById(userId);

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found !");
        }


        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .roles(user.getRoles().stream().map(RoleEntity::getName).toList())
                .build();

        return Response.builder()
                .responseCode(200)
                .responseMessage("SUCCESS")
                .data(userResponse)
                .build();
    }
}
