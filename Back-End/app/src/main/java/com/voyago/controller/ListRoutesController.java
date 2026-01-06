package com.voyago.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.ArrayList;

@RestController
@Component
public class ListRoutesController {
    private List<String> routes;
    private List<String> description;
    
    public ListRoutesController() {
        this.routes = new ArrayList<>();
        this.description = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        // setRoutes("health");
        // setDescription("Verifica se a API está funcionando");
        
        setRoutes("/v1/user/register/");
        setDescription("Registra um novo usuário");
    }

    public void setRoutes(String route) {
        this.routes.add("/" + route);
    }
    
    public void setDescription(String description) {
        this.description.add(description);
    }

    @GetMapping("/")
    public List<Response> getRoutes() {
        List<Response> res = new ArrayList<>();
        
        for (int i = 0; i < routes.size(); i++) {
            res.add(new Response(
                                    routes.get(i), 
                                    description.get(i)
                                ));
        }
        
        return res;
    }

    public static class Response {
        private String route;
        private String description;
        
        public Response(String route, String description) {
            this.route = route;
            this.description = description;
        }

        public String getRoute() {
            return route;
        }
        public String getDescription() {
            return this.description;
        }
    }
}
