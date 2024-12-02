package com.software2uis.msv_ordenes.servicio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.software2uis.msv_ordenes.modelo.Entrega;
import com.software2uis.msv_ordenes.modelo.Pedido;
import com.software2uis.msv_ordenes.repositorio.EntregaRepositorio;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepositorio entregaRepositorio;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public double getDistanceFromAddress(String direccion) {
        // Llamada a una API externa para obtener la distancia
        return webClientBuilder.build()
            .get()
            .uri("https://api-de-distancia.com/distance?address=" + direccion)
            .retrieve()
            .bodyToMono(Double.class) // Asegúrate de que el tipo coincida con lo que devuelve la API
            .block();
    }

    public LocalDate calculateEstimatedDelivery(Pedido pedido) {
        // Se usa el método getDistanceFromAddress para obtener la distancia
        double distancia = getDistanceFromAddress(pedido.getDireccionEnvio());

        // Cálculo de la fecha estimada de entrega basada en la distancia
        int diasEstimados = (int) (distancia / 100) + 2; // Datos ejemplo: cada 100 km agrega un día + 2 días de procesamiento
        LocalDate estimatedDate = LocalDate.now().plus(diasEstimados, ChronoUnit.DAYS);

        // Guardar la estimación de entrega en la base de datos
        Entrega entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setFechaEstimada(estimatedDate);
        entregaRepositorio.save(entrega);

        return estimatedDate;
    }
}


