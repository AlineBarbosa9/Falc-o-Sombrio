package model;
import java.time.Instant;
import java.util.UUID;

public class Telemetria {
	
	// Atributos Privados
    private final UUID id;
    private final UUID droneId;
    private final double latitude;
    private final double longitude;
    private final double altitude;
    private final double velocidade;
    private final Instant timestamp;

    // Construtor Público
    public Telemetria(UUID droneId, double latitude, double longitude, double altitude,
            double velocidade, String statusDrone, String origemDado, Instant timestamp){
    	
        if (droneId == null) throw new IllegalArgumentException("DroneId não pode ser nulo");
        if (velocidade < 0) throw new IllegalArgumentException("Velocidade inválida");

        this.id = UUID.randomUUID();
        this.droneId = droneId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.velocidade = velocidade;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
    }

    // Getters
	public UUID getId() {
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
	public Instant getTimestamp() {
		return timestamp;
	}
	
	@Override
    public String toString() {
        return String.format("Telemetria[DroneID=%s, Lat=%.6f, Lon=%.6f, Alt=%.2f, Vel=%.2f, TS=%s]",
                droneId, latitude, longitude, altitude, velocidade, timestamp);
    }
}
