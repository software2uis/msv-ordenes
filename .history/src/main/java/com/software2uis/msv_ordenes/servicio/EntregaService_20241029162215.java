package com.software2uis.msv_ordenes.servicio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.software2uis.msv_ordenes.modelo.Pedido;


@Service
public class EntregaService {

    @Autowired
    private EntregaRepositorio entregaRepositorio;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public LocalDate calculateEstimatedDelivery(Pedido pedido) {
        // Suponiendo que tenemos una API para calcular la distancia usando la dirección
        String direccion = pedido.getDireccionEnvio();
        
        // Llamada a una API externa para obtener la distancia
        Integer distancia = webClientBuilder.build()
            .get()
            .uri("https://api-de-distancia.com/distance?address=" + direccion)
            .retrieve()
            .bodyToMono(Integer.class)
            .block(); // Puede ser asíncrono también, pero esto lo simplifica para el ejemplo.

        // Cálculo de la fecha estimada de entrega basada en la distancia
        int diasEstimados = (distancia / 100) + 2; // Ejemplo: cada 100 km agrega un día + 2 días de procesamiento
        LocalDate estimatedDate = LocalDate.now().plus(diasEstimados, ChronoUnit.DAYS);

        // Guardar la estimación de entrega en la base de datos
        Entrega entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setFechaEstimada(estimatedDate);
        entregaRepositorio.save(entrega);

        return estimatedDate;
    }
}
