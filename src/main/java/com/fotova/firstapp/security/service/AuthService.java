package com.fotova.firstapp.security.service;

import com.fotova.dto.authentification.redis.RegisterRequestDto;
import com.fotova.dto.client.ClientDto;
import com.fotova.dto.request.ResetPasswordRequest;
import com.fotova.entity.ClientEntity;
import com.fotova.entity.ERole;
import com.fotova.entity.RoleEntity;
import com.fotova.exception.NotFoundException;
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
import com.fotova.service.client.ClientMapper;
import com.fotova.service.client.ClientService;
import com.fotova.service.email.EmailService;
import com.fotova.service.client.redis.RegisterRequestService;
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
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private ClientRepositoryImpl clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoleRepositoryImpl roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RegisterRequestService registerRequestService;

    @Transactional
    public Response<Object> register(RegisterRequest request) {

        if (clientRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        RegisterRequestDto registerRequestDto = registerRequestService.save(request);

        try
        {
            emailService.sendRegisterEmail(registerRequestDto.getRegisterId());
            return Response.builder()
                    .responseCode(200)
                    .responseMessage("SUCCESS")
                    .data("Registration is pending for email validation")
                    .build();
        }
        catch (Exception e)
        {
            return Response.builder()
                    .responseCode(500)
                    .responseMessage("ERROR")
                    .data("An error occured during the registration")
                    .build();
        }
    }

    @Transactional
    public Response<Object> registerAfterEmailConfirm(String uuid) {

        List<RegisterRequestDto> registerRequestDtoList = registerRequestService.findAll();
        for (RegisterRequestDto request : registerRequestDtoList) {
            if (request.getRegisterId().equals(uuid)) {

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
                clientRepository.save(user);

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
        }
        throw new RuntimeException("A error occured with the verification code");
    }

    @Transactional
    public Response<Object> resetPassword(ResetPasswordRequest resetPasswordRequest) {

        String hashedPassword = encoder.encode(resetPasswordRequest.getNewPassword());
        Optional<ClientEntity> clientEntity = clientRepository.findFirstByEmail(resetPasswordRequest.getEmail());

        if (clientEntity.isPresent()) {
            clientEntity.get().setPassword(hashedPassword);
            clientRepository.save(clientEntity.get());
            return Response.builder()
                    .responseCode(200)
                    .responseMessage("SUCCESS")
                    .data("Password reset successfully")
                    .build();
        }

        return Response.builder()
                .responseCode(200)
                .responseMessage("SUCCESS")
                .data("Password cannot be reset a error occured")
                .build();
    }

    // Login Function
    @Transactional
    public Response<Object> login(LoginRequest request) {

        // Check if User by Email exist. if not throw error
        clientRepository.findFirstByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("User not found. Please register first"));


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

        ClientEntity user = clientRepository.findById(userId);
        ClientDto userDto = clientService.getClientById(userId);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found !");
        }

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .address(userDto.getAddress())
                .comments(userDto.getCommentEntities())
                .roles(user.getRoles().stream().map(RoleEntity::getName).toList())
                .build();

        return Response.builder()
                .responseCode(200)
                .responseMessage("SUCCESS")
                .data(userResponse)
                .build();
    }

    @Transactional
    public ClientDto getPrincipal() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer userId = userDetails.getId();

        ClientEntity user = clientRepository.findById(userId);

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found !");
        }

        return clientMapper.mapClientToClientDto(user);
    }
}
