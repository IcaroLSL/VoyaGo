package com.voyago.controller;

import com.voyago.model.User;
import com.voyago.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Component
public class LoginController {
    @Autowired
    private AuthService authService;

    @PostMapping("/user/login/")
    public ResponseEntity<?> loginUser(@RequestBody Request req) {
        try {
            String regex = ".*(['\"\\s]|/\\*|\\*/|--).*";
            boolean usernameValid = req.username.matches(regex);
            boolean passwordValid = req.password.matches(regex);
            if (usernameValid || passwordValid) {
                ErrorResponse error = new ErrorResponse();
                error.message = "Entrada inválida campos negados na validação";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            if (req.ipAddress == null) {
                ErrorResponse error = new ErrorResponse();
                error.message = "Endereço IP é obrigatório";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            Optional<User> userOpt = authService.authenticate(req.username, req.password);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                Response res = new Response();
                res.id = user.getId() != 0 ? user.getId() : null;
                res.name = user.getName();
                try {
                    res.token = "TODO";
                    authService.logSuccess(user.getId(), req.ipAddress);
                } catch (Exception logException) {
                    if (res.token == null) {
                        authService.logFailure(user.getId(), req.ipAddress, "Token generation failed:" + logException);
                    }
                }
                return ResponseEntity.ok(res);
            } else {
                ErrorResponse error = new ErrorResponse();
                error.message = "Usuário ou senha inválidos";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            ErrorResponse error = new ErrorResponse();
            error.message = "Erro da API: " + e.getMessage();
            authService.logFailure(null, req.ipAddress, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    public static class Request {
        public String username;
        public String password;
        public InetAddress ipAddress;
    }    
    public static class Response {
        public String token;
        public String name;
        public Long id;
    }
    public static class ErrorResponse {
        public String message;
    }
}