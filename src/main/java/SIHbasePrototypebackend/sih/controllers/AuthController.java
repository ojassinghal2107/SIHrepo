package SIHbasePrototypebackend.sih.controllers;


import SIHbasePrototypebackend.sih.securityconfig.JwtUtil;
import SIHbasePrototypebackend.sih.model.User;
import SIHbasePrototypebackend.sih.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = List.of("ADMIN"); // Static role for now

        String token = jwtUtil.generateToken(user.getUsername(), roles);

        return ResponseEntity.ok(Map.of(
            "accessToken", token,
            "tokenType", "Bearer",
            "username", user.getUsername(),
            "roles", roles
        ));
    }
}