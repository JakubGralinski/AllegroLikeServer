package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class AuthController {

    @GetMapping("/public")
    public ResponseEntity<String> publicAccess() {
        return ResponseEntity.ok("Public Content.");
    }

    @GetMapping("/user")
    public ResponseEntity<String> userAccess() {
        return ResponseEntity.ok("User Content.");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Admin Content.");
    }
} 