class ShippingCalculator:
    def __init__(self, warehouse_location, max_distance=50, rate_per_km=5000):
        """
        Inicializa el sistema de cálculo de envío.
        
        :param warehouse_location: La ubicación del almacén (coordenadas como tupla (lat, long)).
        :param max_distance: Distancia máxima de servicio en kilómetros (default=50 km).
        :param rate_per_km: Tarifa por kilómetro en pesos colombianos (default=5000 COP/km).
        """
        self.warehouse_location = warehouse_location
        self.max_distance = max_distance
        self.rate_per_km = rate_per_km
    
    def calculate_distance(self, delivery_address):
        """
        Calcula la distancia entre el almacén y la dirección de entrega.
        
        :param delivery_address: La dirección de entrega (coordenadas como tupla (lat, long)).
        :return: Distancia en kilómetros.
        """
        from math import radians, sin, cos, sqrt, atan2
        
        lat1, lon1 = self.warehouse_location
        lat2, lon2 = delivery_address

        R = 6371.0  # Radio de la Tierra en km

        dlat = radians(lat2 - lat1)
        dlon = radians(lon2 - lon1)

        a = sin(dlat / 2)**2 + cos(radians(lat1)) * cos(radians(lat2)) * sin(dlon / 2)**2
        c = 2 * atan2(sqrt(a), sqrt(1 - a))
        distance = R * c

        return distance
    
    def calculate_shipping_cost(self, delivery_address):
        """
        Calcula el costo de envío basado en la distancia.
        
        :param delivery_address: La dirección de entrega (coordenadas como tupla (lat, long)).
        :return: El costo de envío en pesos colombianos o un mensaje si está fuera del área de servicio.
        """
        distance = self.calculate_distance(delivery_address)
        
        if distance > self.max_distance:
            return "La dirección está fuera del área de servicio."
        
        shipping_cost = distance * self.rate_per_km
        return f"El costo de envío es: {shipping_cost:,.2f} COP."
    

# Ejemplo de uso
warehouse_location = (7.1152, -73.1198)  # Coordenadas del almacén (ejemplo en Bucaramanga, Colombia)
delivery_address = (7.0891, -73.1242)  # Coordenadas de una dirección de entrega

calculator = ShippingCalculator(warehouse_location)
result = calculator.calculate_shipping_cost(delivery_address)

print(result)
