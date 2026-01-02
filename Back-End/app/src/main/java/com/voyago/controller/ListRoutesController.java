package com.voyago.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
public class ListRoutesController {
    private List<String> routes;
    
    public ListRoutesController() {
        routes = new ArrayList<>();
    }

    public void setRoutes(String route) {
        this.routes.add("/" + route);
    }

    @GetMapping("/")
    public List<String> getRoutes() {
        return this.routes;
    }   
}
