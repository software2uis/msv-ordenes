package com.software2uis.msv_ordenes.servicio;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.software2uis.msv_ordenes.modelo.Pedido;

@SpringBootTest
public class EntregaServiceIntegrationTest {

    @Autowired
    private EntregaService deliveryService;

    @Test
    public void testCalculateEstimatedDelivery_withAPICall() {
        Pedido pedido = new Pedido();
        pedido.setDireccionEnvio("123 Main St");

        LocalDate estimatedDate = deliveryService.calculateEstimatedDelivery(pedido);
        assertNotNull(estimatedDate);
    }
}
