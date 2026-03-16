package model;

public class Coordenadas {
	
	// Atributos Privados e Fixos
    private double latitude;
    private double longitude;
    private double altitude;
    
    // Construtor Público
    public Coordenadas(double latitude, double longitude, double altitude) {
        validarLimites(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
    
    // Validação das Coordenadas
    private void validarLimites(double lat, double lon) {
        if (lat < -90.0 || lat > 90.0) {
            throw new IllegalArgumentException("Latitude fora dos limites (-90 a 90): " + lat);
        }
        if (lon < -180.0 || lon > 180.0) {
            throw new IllegalArgumentException("Longitude fora dos limites (-180 a 180): " + lon);
        }
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
    
    // Setters
    public void setLatitude(double latitude) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude inválida: " + latitude);
        }
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude inválida: " + longitude);
        }
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    
    // Para Retorno dos Valores das Coordenadas
    @Override
    public String toString() {
        return String.format("Lat: %.6f, Lon: %.6f, Alt: %.2fm", latitude, longitude, altitude);
    }
    
    // Verifica se o Objeto Está Próximo do Raio da Missão
    public boolean isProximo(Coordenadas destino, double raioToleranciaMetros) {
        return calcularDistancia(destino) <= raioToleranciaMetros;
    }

    public double calcularDistancia(Coordenadas destino) {
       // Método aqui
        return 0.0; 
    }
}