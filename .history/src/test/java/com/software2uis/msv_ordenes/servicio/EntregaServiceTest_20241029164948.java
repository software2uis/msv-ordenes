package com.software2uis.msv_ordenes.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import com.software2uis.msv_ordenes.modelo.Pedido;
import com.software2uis.msv_ordenes.repositorio.EntregaRepositorio;

public class EntregaServiceTest {

    @Mock
    private EntregaRepositorio entregaRepository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private EntregaService deliveryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateEstimatedDelivery_withDifferentDistances() {
        Pedido pedido = new Pedido();
        pedido.setDireccionEnvio("123 Main St");

        when(deliveryService.getDistanceFromAddress(pedido.getDireccionEnvio())).thenReturn(100.0); // Mock de distancia

        LocalDate estimatedDate = deliveryService.calculateEstimatedDelivery(pedido);
        LocalDate expectedDate = LocalDate.now().plusDays(4); // 2 días de procesamiento + 2 días de viaje (distancia)

        assertEquals(expectedDate, estimatedDate);
    }
}
