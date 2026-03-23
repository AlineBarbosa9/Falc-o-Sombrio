package model;

import java.util.UUID;
import enums.StatusSensor;
import enums.TipoSensor;

public abstract class Sensor {
	
	// Atributos Privados
    private final UUID id;
    private final TipoSensor tipo;
    private StatusSensor status;

    // Construtor Protegido
    protected Sensor(TipoSensor tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de sensor obrigatório");
        }

        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.status = StatusSensor.OPERACIONAL;
    }

    
    // Verificar Status do Tipo de Sensor
    public void verificarStatus() {
        if (this.status == StatusSensor.FALHA) {
            gerarAlerta();
        }
    }
    
    // Método Protegido Para Alerta em Cada Sensor
    protected void gerarAlerta() {
        
    }
    
    // Método Abstrato para Coleta de Dados
    public abstract void coletarDados(); 
    
    // Controle de Status
    public void atualizarStatus(StatusSensor novoStatus) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("Status inválido");
        }
        this.status = novoStatus;
    }

    // Getters
    public UUID getId() {
        return id;
    }
    public TipoSensor getTipo() {
        return tipo;
    }
    public StatusSensor getStatus() {
        return status;
    }
}