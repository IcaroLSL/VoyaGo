package com.voyago.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DatabaseOperationInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        
        switch (method) {
            case "GET":
                DatabaseContextHolder.setDatabaseOperation(DatabaseOperation.SELECT);
                System.out.println("Usando api_select");
                break;
            case "POST":
                DatabaseContextHolder.setDatabaseOperation(DatabaseOperation.INSERT);
                System.out.println("Usando api_insert");
                break;
            case "PUT":
            case "PATCH":
                DatabaseContextHolder.setDatabaseOperation(DatabaseOperation.UPDATE);
                System.out.println("Usando api_update");
                break;
            case "DELETE":
                DatabaseContextHolder.setDatabaseOperation(DatabaseOperation.DELETE);
                System.out.println("Usando api_delete");
                break;
            default:
                System.out.println("Método desconhecido, defina uma operação padrão");
                return false;
        }
        
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        DatabaseContextHolder.clear();
    }
}
