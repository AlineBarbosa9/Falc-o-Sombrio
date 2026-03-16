package model;
import java.util.UUID;
import enums.StatusDrone;

public class Drone {
	
	// Atributos Privados
	private UUID id; 
    private String modelo;
    private StatusDrone status; 
    private double bateria;
    private Coordenadas localizacao; 
    private int missaoId;
    
    // Construtor
    public Drone() {
        this.id = UUID.randomUUID();
    }
    
    // Construtor 
    public Drone(String modelo, Coordenadas localizacaoInicial) {
        this.id = UUID.randomUUID();
        this.modelo = modelo;
        this.localizacao = localizacaoInicial;
        this.status = StatusDrone.IDLE;
        this.bateria = 100.0;
    }
    
    // Método para Movimentação do Drone
    public void moverPara(double novaLat, double novaLon, double novaAlt,double consumo) {
        if (this.bateria <= 0) {
            this.status = StatusDrone.ALERTA;
            System.out.println("ERRO: Drone sem energia para mover.");
            return;
        }

        this.localizacao.setLatitude(novaLat);
        this.localizacao.setLongitude(novaLon);
        this.localizacao.setAltitude(novaAlt);
        this.bateria -= consumo;
        
        if (this.bateria < 15.0) {
            this.status = StatusDrone.ALERTA;
        }
    }
    
    // Getters e Setters
	public UUID getId() {
		return id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public StatusDrone getStatus() {
		return status;
	}
	public void setStatus(StatusDrone status) {
		this.status = status;
	}
	public double getBateria() {
		return bateria;
	}
	public void setBateria(double bateria) {
		this.bateria = bateria;
	}
	public Coordenadas getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(Coordenadas localizacao) {
		this.localizacao = localizacao;
	} 
	public int getMissaoId() {
		return missaoId;
	}
	public void setMissaoId(int missaoId) {
		this.missaoId = missaoId;
	}
	
}
