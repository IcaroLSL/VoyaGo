package com.voyago.service;

import com.voyago.model.User;
import com.voyago.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Argon2id parameters:
    // saltLength: 16 bytes, hashLength: 32 bytes, parallelism: 1, memory: 65536 KB (64 MB), iterations: 3
    // Adjust memory/iterations for your environment (lower for dev, higher for production as needed).
    private final Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 3);

    public Optional<User> authenticate(String username, String password) {
        // Busca usuário pelo username
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            if (user.getStatus_active() == null || !user.getStatus_active()) {
                return Optional.empty();
            }
            
            if (user.getPassword() != null && passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }

    public Optional<User> register(String name, String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return Optional.empty();
        }

        String hashedPassword = passwordEncoder.encode(password);

        String sql = "INSERT INTO users (name, username, password) VALUES (?, ?, ?) RETURNING id";
        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class, name, username, hashedPassword);

        return userRepository.findById(generatedId);
    }
}
