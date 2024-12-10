package com.software2uis.msv_ordenes.controlador;

import com.software2uis.msv_ordenes.servicio.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    @Autowired
    private SesionService sesionService;

    @GetMapping("/active/{username}")
    public Map<String, Object> checkActiveSession(@PathVariable String username) {
        sesionService.checkActiveSession(username);
        Map<String, Object> response = new HashMap<>();
        response.put("isActiveSession", sesionService.isActiveSession());
        response.put("activeUsername", sesionService.getActiveUsername());
        return response;
    }
}
