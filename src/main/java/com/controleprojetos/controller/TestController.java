package com.controleprojetos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @PostMapping
    public ResponseEntity<String> testJson(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok("Recebido com sucesso");
    }
}
