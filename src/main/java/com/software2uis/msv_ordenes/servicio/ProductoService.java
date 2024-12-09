package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.dto.ProductDTO;
import com.software2uis.msv_ordenes.modelo.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;

@Service
public class ProductoService {

    private final RestTemplate restTemplate;

    public ProductoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Producto obtenerProductoDeCatalogo(String productId) {
        String url = "http://192.168.193.90:8081/public/api/products/" + productId;

        // Realiza la solicitud HTTP GET
        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(url, ProductDTO.class);
        ProductDTO product = response.getBody();

        if (product == null) {
            throw new RuntimeException("Producto no encontrado");
        }


        // Crea un objeto Producto para tu microservicio
        Producto producto = new Producto();
        producto.setId(product.getId());
        producto.setNombre(product.getName());
        producto.setPrecio(BigDecimal.valueOf(product.getPrice()));
        producto.setCantidad(product.getQuantity());

        return producto;
    }
}
