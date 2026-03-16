package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Telemetria {
	
	// Atributos Privados
    private String id;
    private UUID droneId;
    private double latitude;
    private double longitude;
    private double altitude;
    private double velocidade;
    private LocalDateTime timestamp;

    // Construtor 
    public Telemetria(Drone drone, double velocidade) {
        this.id = UUID.randomUUID().toString();
        this.droneId = drone.getId();
        this.latitude = drone.getLocalizacao().getLatitude();
        this.longitude = drone.getLocalizacao().getLongitude();
        this.altitude = drone.getLocalizacao().getAltitude();
        this.velocidade = velocidade;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
	public String getId() {
		return id;
	}
	public UUID getDroneId() {
		return droneId;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public double getVelocidade() {
		return velocidade;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
