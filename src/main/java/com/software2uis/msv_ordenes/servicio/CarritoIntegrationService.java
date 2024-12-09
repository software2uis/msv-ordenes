package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.dto.CartProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CarritoIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${carrito.service.url:http://192.168.193.90:8090}")
    private String carritoServiceUrl;

    public CarritoIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CartProductDTO> obtenerCarritoDelUsuario(String username) {
        String url = carritoServiceUrl + "/api/cart/contents?username=" + username;
        CartProductDTO[] productosArray = restTemplate.getForObject(url, CartProductDTO[].class);
        return Arrays.asList(productosArray != null ? productosArray : new CartProductDTO[0]);
    }
}
