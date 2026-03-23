package model;

import java.util.Objects;

public final class Coordenadas {
	
	// Constantes Para Cálculo
    private static final double MIN_LAT = -90.0;
    private static final double MAX_LAT = 90.0;
    private static final double MIN_LON = -180.0;
    private static final double MAX_LON = 180.0;
    private static final double EPSILON = 1e-6;
    private static final double EARTH_RADIUS = 6371000;
    
    // Atributos Privados
    private final double latitude;
    private final double longitude;
    private final double altitude;
    
    // Construtor Público
    public Coordenadas(double latitude, double longitude, double altitude) {
        validar(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
    
    // Validação de Latitude e Longitude
    private void validar(double latitude, double longitude) {
        if (latitude < MIN_LAT || latitude > MAX_LAT) {
            throw new IllegalArgumentException("Latitude inválida: " + latitude);
        }
        if (longitude < MIN_LON || longitude > MAX_LON) {
            throw new IllegalArgumentException("Longitude inválida: " + longitude);
        }
    }
    
    // Calcula se Drones Estão no Raio Próximo do Local da Missão
    public boolean isProximo(Coordenadas destino, double raioMetros) {
        Objects.requireNonNull(destino, "Destino não pode ser nulo");
        return calcularDistancia(destino) <= raioMetros;
    }
    
    // Calcula a Distância (Considerada a Altitude)
    public double calcularDistancia(Coordenadas destino) {
        Objects.requireNonNull(destino, "Destino não pode ser nulo");

        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(destino.latitude);

        double deltaLat = Math.toRadians(destino.latitude - this.latitude);
        double deltaLon = Math.toRadians(destino.longitude - this.longitude);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distanciaSuperficie = EARTH_RADIUS * c;

        double deltaAlt = destino.altitude - this.altitude;

        return Math.sqrt(distanciaSuperficie * distanciaSuperficie + deltaAlt * deltaAlt);
    }

    // Cálculo de Distância 2D (Ignora a Altitude)
    public double calcularDistancia2D(Coordenadas destino) {
        Objects.requireNonNull(destino, "Destino não pode ser nulo");

        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(destino.latitude);

        double deltaLat = Math.toRadians(destino.latitude - this.latitude);
        double deltaLon = Math.toRadians(destino.longitude - this.longitude);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
    
    // Compara Se Uma Coordenada é Igual a Outra (Com Tolerância)
    public boolean isIgual(Coordenadas outra) {
        if (outra == null) return false;

        return Math.abs(this.latitude - outra.latitude) < EPSILON &&
               Math.abs(this.longitude - outra.longitude) < EPSILON &&
               Math.abs(this.altitude - outra.altitude) < EPSILON;
    }
    
    // Getters
    public double getLatitude() { 
    	return latitude; 
    }
    public double getLongitude() { 
    	return longitude; 
    }
    public double getAltitude() { 
    	return altitude; 
    }
 
    @Override
    public String toString() {
        return String.format("Lat: %.6f, Lon: %.6f, Alt: %.2fm",
                latitude, longitude, altitude);
    }
}