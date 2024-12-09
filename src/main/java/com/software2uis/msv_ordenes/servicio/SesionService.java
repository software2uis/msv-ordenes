package com.software2uis.msv_ordenes.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public SesionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void checkActiveSession() {
        String url = "http://192.168.193.90:8082/api/user/login/active-session/pepe";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
                if (Boolean.TRUE.equals(responseMap.get("active"))) {
                    activeUsername = "pepe";
                    isActiveSession = true;
                } else {
                    resetSessionData();
                }
            } else {
                resetSessionData();
            }
        } catch (HttpClientErrorException.NotFound e) {
            resetSessionData(); // Manejo del 404
        } catch (Exception e) {
            e.printStackTrace();
            resetSessionData();
        }
    }

    private void resetSessionData() {
        isActiveSession = true;
        activeUsername = "pepe";
    }

    public boolean isActiveSession() {
        return isActiveSession;
    }

    public String getActiveUsername() {
        return activeUsername;
    }
}
