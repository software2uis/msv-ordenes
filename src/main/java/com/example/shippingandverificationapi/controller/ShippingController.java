
package com.example.shippingandverificationapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {

    private final double WAREHOUSE_LAT = 7.1152;
    private final double WAREHOUSE_LON = -73.1198;
    private final double MAX_DISTANCE = 50.0; // km
    private final double RATE_PER_KM = 5000.0; // COP

    @GetMapping("/api/shipping/cost")
    public ResponseEntity<String> calculateShippingCost(@RequestParam double lat, @RequestParam double lon) {
        double distance = calculateDistance(lat, lon);
        if (distance > MAX_DISTANCE) {
            return ResponseEntity.ok("Dirección fuera del área de servicio.");
        }
        double cost = distance * RATE_PER_KM;
        return ResponseEntity.ok(String.format("Costo de envío: %.2f COP", cost));
    }

    private double calculateDistance(double lat, double lon) {
        final int EARTH_RADIUS = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(lat - WAREHOUSE_LAT);
        double dLon = Math.toRadians(lon - WAREHOUSE_LON);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(WAREHOUSE_LAT)) * Math.cos(Math.toRadians(lat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // Distancia en km
    }
}
