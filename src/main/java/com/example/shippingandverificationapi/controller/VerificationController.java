
package com.example.shippingandverificationapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerificationController {

    private boolean userLoggedIn = false;

    @PostMapping("/api/store/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if ("usuario_demo".equals(user.getUsername()) && "contrasena123".equals(user.getPassword())) {
            userLoggedIn = true;
            return ResponseEntity.ok("Inicio de sesión exitoso.");
        }
        return ResponseEntity.ok("Credenciales inválidas.");
    }

    @GetMapping("/api/store/payment")
    public ResponseEntity<String> proceedToPayment(@RequestParam double cartTotal) {
        if (userLoggedIn) {
            return ResponseEntity.ok(String.format("Pago autorizado. Total: %.2f COP", cartTotal));
        }
        return ResponseEntity.ok("Debe iniciar sesión para proceder.");
    }
}

class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
