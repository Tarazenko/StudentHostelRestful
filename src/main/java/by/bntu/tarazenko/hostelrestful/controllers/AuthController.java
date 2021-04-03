package by.bntu.tarazenko.hostelrestful.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import by.bntu.tarazenko.hostelrestful.models.ERole;
import by.bntu.tarazenko.hostelrestful.models.Role;
import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.payload.request.LoginRequest;
import by.bntu.tarazenko.hostelrestful.payload.request.SignupRequest;
import by.bntu.tarazenko.hostelrestful.payload.response.JwtResponse;
import by.bntu.tarazenko.hostelrestful.payload.response.MessageResponse;
import by.bntu.tarazenko.hostelrestful.repository.RoleRepository;
import by.bntu.tarazenko.hostelrestful.repository.UserRepository;
import by.bntu.tarazenko.hostelrestful.security.jwt.JwtUtils;
import by.bntu.tarazenko.hostelrestful.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        log.debug("Login request - {} ", loginRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        log.debug("Jwt - {}", jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

        log.debug("JWT response - {}", jwtResponse);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getPatronymic());

        String strRole = signUpRequest.getRole();
        ERole role = ERole.ROLE_USER;

        if (strRole != null) {
            switch (strRole) {
                case "admin":
                    role = ERole.ROLE_ADMIN;
                    break;
                case "mod":
                    role = ERole.ROLE_MODERATOR;
                    break;
            }
        }
        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
