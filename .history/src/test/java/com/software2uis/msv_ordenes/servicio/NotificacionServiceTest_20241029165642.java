package com.software2uis.msv_ordenes.servicio;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.software2uis.msv_ordenes.modelo.Entrega;
import com.software2uis.msv_ordenes.modelo.Pedido;

public class NotificacionServiceTest {

    @InjectMocks
    private NotificacionService notificationService;

    @Mock
    private Entrega entrega;

    @Test
    public void testNotifyDelay() {
        MockitoAnnotations.openMocks(this);

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(entrega.getPedido()).thenReturn(pedido);

        notificationService.notifyDelay(entrega);

        verify(entrega, times(1)).getPedido();
    }
}
