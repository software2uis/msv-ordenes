package com.software2uis.msv_ordenes.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SesionService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private boolean isActiveSession = false;
    private String activeUsername = null;

    @Value("${back.usuarios.url}")
    private String urlUsuarios;

    public SesionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void checkActiveSession(String username) {
        String url =  urlUsuarios + "/api/user/login/active-session/" + username;
        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(url, boolean.class);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                if (Boolean.TRUE.equals(response.getBody())) {
                    activeUsername = username;
                    isActiveSession = true;
                } else {
                    resetSessionData();
                }
            } else {
                resetSessionData();
            }
        } catch (HttpClientErrorException.NotFound e) {
            resetSessionData();
        } catch (Exception e) {
            e.printStackTrace();
            resetSessionData();
        }
    }

    private void resetSessionData() {
        isActiveSession = false;
        activeUsername = null;
    }

    public boolean isActiveSession() {
        return isActiveSession;
    }

    public String getActiveUsername() {
        return activeUsername;
    }
}
