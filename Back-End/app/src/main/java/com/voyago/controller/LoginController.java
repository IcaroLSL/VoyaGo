// package com.voyago.controller;

// import com.voyago.model.User;
// import com.voyago.service.AuthService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Component;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import java.net.InetAddress;
// import java.util.Optional;

// @RestController
// @RequestMapping("/v1")
// @Component
// public class LoginController {
//     @Autowired
//     private AuthService authService;

//     @PostMapping("/user/login/")
//     public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
//         try {
//             Optional<User> userOpt = authService.authenticate(request.username, request.password);
//             if (userOpt.isPresent()) {
//                 User user = userOpt.get();
//                 String token = authService.generateToken(user);

//                 LoginResponse response = new LoginResponse();
//                 response.token = token;
//                 response.name = user.getName();
//                 response.id = user.getId() != null ? user.getId().toString() : null;

//                 return ResponseEntity.ok(response);
//             } else {
//                 ErrorResponse error = new ErrorResponse();
//                 error.message = "Credenciais inválidas";
//                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
//             }
//         } catch (Exception e) {
//             ErrorResponse error = new ErrorResponse();
//             error.message = "Erro interno do servidor";
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//         }
//     }

//     // Classes de Request/Response
//     public static class LoginRequest {
//         public String username;
//         public String password;
//     }    
//     public static class LoginResponse {
//         public String token;
//         public String name;
//         public String id;
//     }
//     public static class ErrorResponse {
//         public String message;
//     }
// }