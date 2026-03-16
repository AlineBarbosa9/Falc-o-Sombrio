package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import enums.StatusMissao;

public class Missao {
	
	// Atributos Privados 
    private UUID id;
    private String objetivo;
    private StatusMissao status;
    private List<UUID> dronesIds; 
    private Coordenadas pontoEntrada;
    private double raioOperacao; 
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    
    // Construtor
    public Missao(String objetivo, Coordenadas pontoEntrada, double raio) {
        this.id = UUID.randomUUID();
        this.objetivo = objetivo;
        this.pontoEntrada = pontoEntrada;
        this.raioOperacao = raio;
        this.status = StatusMissao.AGUARDANDO;
        this.dronesIds = new ArrayList<>();
    }

    // Método para Iniciar a Missão
    public void iniciarMissao() {
        if (dronesIds.isEmpty()) throw new IllegalStateException("Missão sem Drones Alocados.");
        this.status = StatusMissao.EM_CURSO;
        this.dataInicio = LocalDateTime.now();
    }
    
    // Método para Abortar a Missão
    public void abortarMissao(String motivo) {
        this.status = StatusMissao.ABORTADA;
        this.dataFim = LocalDateTime.now();
        
    }
    
    // Método para Finalizar a Missão
    public void finalizarMissao() {
        this.status = StatusMissao.SUCESSO;
        this.dataFim = LocalDateTime.now();
    }
    
    // Getters e Setters
	public UUID getId() {
		return id;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public StatusMissao getStatus() {
		return status;
	}
	public void setStatus(StatusMissao status) {
		this.status = status;
	}
	public List<UUID> getDronesIds() {
		return dronesIds;
	}
	public void setDronesIds(List<UUID> dronesIds) {
		this.dronesIds = dronesIds;
	}
	public Coordenadas getPontoEntrada() {
		return pontoEntrada;
	}
	public void setPontoEntrada(Coordenadas pontoEntrada) {
		this.pontoEntrada = pontoEntrada;
	}
	public double getRaioOperacao() {
		return raioOperacao;
	}
	public void setRaioOperacao(double raioOperacao) {
		this.raioOperacao = raioOperacao;
	}
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDateTime getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

}
