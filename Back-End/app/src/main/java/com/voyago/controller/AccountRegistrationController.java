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
public class AccountRegistrationController {
    @Autowired
    private AuthService authService;

    // ═══════════════════════════════════════════════════════════════════════════════
    // EXPLICAÇÃO:
    // Define um endpoint POST em "/v1/user/register/".
    // @RequestBody converte o JSON recebido para o objeto Request.
    // ResponseEntity<?> permite retornar diferentes tipos de resposta (sucesso ou erro).
    // ═══════════════════════════════════════════════════════════════════════════════
    // 
    // EQUIVALENTE EM GO (usando Gin):
    // func (c *Controller) RegisterUser(ctx *gin.Context) {
    //     var request Request
    //     if err := ctx.ShouldBindJSON(&request); err != nil {
    //         ctx.JSON(400, gin.H{"error": err.Error()})
    //         return
    //     }
    //     // ... lógica
    // }
    // ═══════════════════════════════════════════════════════════════════════════════
    // 
    // EQUIVALENTE EM PYTHON (usando FastAPI):
    // @app.post("/v1/user/register/")
    // def register_user(request: Request) -> Response:
    //     # FastAPI converte JSON automaticamente para o objeto request
    //     # ... lógica
    // ═══════════════════════════════════════════════════════════════════════════════
    @PostMapping("/user/register/")
    public ResponseEntity<?> registerUser(@RequestBody Request req) {
        try {
            String regex = ".*(['\"\\s]|/\\*|\\*/|--).*";
            boolean nameValid = req.name.matches(regex);
            boolean usernameValid = req.username.matches(regex);
            boolean passwordValid = req.password.matches(regex);
            if (nameValid || usernameValid || passwordValid) {
                ErrorResponse error = new ErrorResponse();
                error.message = "Entrada inválida campos negados na validação";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            if (req.ipAddress == null) {
                ErrorResponse error = new ErrorResponse();
                error.message = "Endereço IP é obrigatório";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            // ═══════════════════════════════════════════════════════════════════════════════
            // EXPLICAÇÃO:
            // Chama o método register() do AuthService passando name, username e password.
            // O método retorna Optional<User> que pode conter o usuário criado ou estar vazio.
            // Optional é como uma "caixa" que pode ter um valor dentro ou não (evita null).
            // ═══════════════════════════════════════════════════════════════════════════════
            // 
            // EQUIVALENTE EM GO:
            // user, err := authService.Register(request.Name, request.Username, request.Password)
            // if err != nil {
            //     return nil, err
            // }
            // if user != nil {
            //     // usuário existe
            // }
            // ═══════════════════════════════════════════════════════════════════════════════
            // 
            // EQUIVALENTE EM PYTHON:
            // user: Optional[User] = auth_service.register(request.name, request.username, request.password)
            // if user is not None:
            //     # usuário existe
            // ═══════════════════════════════════════════════════════════════════════════════
            
            Optional<User> userOpt = authService.register(req.name, req.username, req.password);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                Response res = new Response();
                res.id = user.getId() != 0 ? user.getId() : null;
                res.name = user.getName();
                res.username = user.getUsername();
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
                error.message = "Falha no registro do usuário";
                authService.logFailure(null, req.ipAddress, "User registration failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            // Log do erro real para debug
            e.printStackTrace();
            
            ErrorResponse error = new ErrorResponse();
            error.message = "Erro da API: " + e.getMessage();
            authService.logFailure(null, req.ipAddress, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    public static class Request {
        public String name;
        public String username;
        public String password;
        public InetAddress ipAddress;
    }    
    public static class Response {
        public Long id;
        public String name;
        public String username;
        public String token;
    }
    public static class ErrorResponse {
        public String message;
    }
}