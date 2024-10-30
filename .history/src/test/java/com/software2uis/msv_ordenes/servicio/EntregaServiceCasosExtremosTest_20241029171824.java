package com.software2uis.msv_ordenes.servicio;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.software2uis.msv_ordenes.modelo.Pedido;

public class EntregaServiceCasosExtremosTest {

    @InjectMocks
    private EntregaService entregaService;

    @Test
    public void testCalculateEstimatedDelivery_withLongDistance() {
        Pedido pedido = new Pedido();
        pedido.setDireccionEnvio("Distant Address");

        // when(entregaService.getDistanceFromAddress(pedido.getDireccionEnvio())).thenReturn(5000.0); // Distancia muy larga

        LocalDate estimatedDate = entregaService.calculateEstimatedDelivery(pedido);
        LocalDate expectedDate = LocalDate.now().plusDays(102); // cálculo basado en distancia extrema

        assertTrue(estimatedDate.isAfter(LocalDate.now()));
    }
}
