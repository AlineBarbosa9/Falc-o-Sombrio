package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import enums.StatusDrone;

public class Drone {
	
	// Atributos Privados
    private final UUID id;
    private final String modelo;
    private StatusDrone status;
    private double bateria;
    private Coordenadas localizacao;
    private UUID missaoId;
    private final List<Sensor> sensores;
    
    // Construtor Público
    public Drone(String modelo, Coordenadas localizacaoInicial) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo inválido");
        }
        if (localizacaoInicial == null) {
            throw new IllegalArgumentException("Localização inicial obrigatória");
        }

        this.id = UUID.randomUUID();
        this.modelo = modelo;
        this.localizacao = localizacaoInicial;
        this.status = StatusDrone.IDLE;
        this.bateria = 100.0;
        this.sensores = new ArrayList<>();
    }

    public void moverPara(Coordenadas destino, double consumo) {
        validarOperacao(consumo);

        this.localizacao = destino;
        consumirBateria(consumo);
    }

    private void validarOperacao(double consumo) {
        double reservaSeguranca = 5.0;

        if (this.bateria <= 15.0 || (this.bateria - consumo) < reservaSeguranca) {
            entrarModoAlerta();
            throw new IllegalStateException("Energia insuficiente para operação segura");
        }
    }
    
    // Atualiza a Bateria Após Consumo
    private void consumirBateria(double consumo) {
        this.bateria -= consumo;

        if (this.bateria <= 15.0) {
            entrarModoAlerta();
        }
    }
    
    // Altera Para o Modo Alerta
    protected void entrarModoAlerta() {
        this.status = StatusDrone.ALERTA;
    }

    // Adição de Sensores
    public void adicionarSensor(Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException("Sensor não pode ser nulo");
        }
        this.sensores.add(sensor);
    }
    
    // Sincronização de Sensores
    public void sincronizarSensores() {
        for (Sensor s : sensores) {
            s.coletarDados();
            s.verificarStatus();
        }
    }
    
    // Getters
    public UUID getId() {
        return id;
    }
    public Coordenadas getLocalizacao() {
        return localizacao;
    }
    public double getBateria() {
        return bateria;
    }
    public StatusDrone getStatus() {
        return status;
    }
    public String getModelo() {
        return modelo;
    }
    public UUID getMissaoId() {
        return missaoId;
    }
    public List<Sensor> getSensores() {
        return List.copyOf(sensores);
    }
}
