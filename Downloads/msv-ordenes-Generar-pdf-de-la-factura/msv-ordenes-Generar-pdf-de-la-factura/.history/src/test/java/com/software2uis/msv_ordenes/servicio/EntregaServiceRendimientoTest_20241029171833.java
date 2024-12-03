package com.software2uis.msv_ordenes.servicio;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.RepeatedTest;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.software2uis.msv_ordenes.modelo.Pedido;

@SpringBootTest
public class EntregaServiceRendimientoTest {

    @InjectMocks
    private EntregaService entregaService;

    @RepeatedTest(1000) // Ejecuta la prueba 1000 veces para simular alta carga
    public void testPerformance() {
        Pedido pedido = new Pedido();
        pedido.setDireccionEnvio("123 Main St");

        LocalDate estimatedDate = entregaService.calculateEstimatedDelivery(pedido);
        assertNotNull(estimatedDate);
    }
}
