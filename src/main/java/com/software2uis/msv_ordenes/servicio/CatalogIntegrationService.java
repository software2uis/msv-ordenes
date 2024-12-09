package com.software2uis.msv_ordenes.servicio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CatalogIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${catalogo.service.url:http://192.168.193.90:8081}")
    private String catalogoServiceUrl;

    public CatalogIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void subtractQuantity(String productId, int quantity) {
        String url = catalogoServiceUrl + "/public/api/products/subtractQuantity";

        Map<String, Object> request = new HashMap<>();
        request.put("id", productId);
        request.put("quantity", quantity);

        restTemplate.postForEntity(url, request, String.class);
    }

    public void addQuantity(String productId, int quantity) {
        String url = catalogoServiceUrl + "/public/api/products/addQuantity";

        Map<String, Object> request = new HashMap<>();
        request.put("id", productId);
        request.put("quantity", quantity);

        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request), String.class);
    }
}
