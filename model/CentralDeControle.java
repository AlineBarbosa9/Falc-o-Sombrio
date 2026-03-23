package model;

import java.util.ArrayList;
import java.util.List;

public class CentralDeControle {

    // Atributos
    private final List<Drone> frota;
    private final List<Missao> missoes;
    private final List<LogAuditoria> logs;

    // Construtor Público
    public CentralDeControle() {
        this.frota = new ArrayList<>();
        this.missoes = new ArrayList<>();
        this.logs = new ArrayList<>();
    }
    
    // Gerencia a Autorização da Missão
    public void autorizarMissao(Operador operador, Missao missao) {

        if (operador == null || missao == null) {
            throw new IllegalArgumentException("Operador ou missão inválidos");
        }
 
        operador.autorizarInicioMissao(missao);    
        missao.iniciarMissao();   
    }

    public void adicionarDroneAFrota(Drone drone) {
        if (drone == null) {
            throw new IllegalArgumentException("Drone inválido");
        }
        this.frota.add(drone);
    }

    public void adicionarMissao(Missao missao) {
        if (missao == null) {
            throw new IllegalArgumentException("Missão inválida");
        }
        this.missoes.add(missao);
    }

    
    // Monitoramento de Drone Simplificado
    public void monitorarDrone(Drone drone) {
        if (drone == null) {
            throw new IllegalArgumentException("Drone inválido");
        }

       // Implementação Futura
    }

    // Getters
    public List<Drone> getFrota() {
        return List.copyOf(frota);
    }
    public List<Missao> getMissoes() {
        return List.copyOf(missoes);
    }
    public List<LogAuditoria> getLogs() {
        return List.copyOf(logs);
    }
}