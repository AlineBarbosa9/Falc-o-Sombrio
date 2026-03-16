package model;

import java.util.ArrayList;
import java.util.List;

public class CentralDeControle {
    
	// Atributos Privados
    private List<Drone> frota;
    private List<Missao> missoes;
    private List<LogAuditoria> logs;
    private SistemaComunicacao comunicacao;
    private NavegacaoInteligente ia;
    
    // Construtor
    private CentralDeControle() {
        this.frota = new ArrayList<>();
        this.missoes = new ArrayList<>();
        this.logs = new ArrayList<>();
        this.comunicacao = new SistemaComunicacao("gRPC - Seguro");
        this.ia = new NavegacaoInteligente();
    }

   
    public void autorizarMissao(Operador operador, Missao missao, Drone drone) {
        registrarLog(operador.getNome(), "INICIO_MISSAO", "Missão " + missao.getObjetivo() + " iniciada com o drone " + drone.getModelo());
    }


    public void monitorarDrone(Drone drone) {
        System.out.println("\n[MONITORAMENTO] Verificando Telemetria do Drone: " + drone.getModelo());
        
    }

    private void registrarLog(String usuario, String acao, String detalhes) {
        LogAuditoria log = new LogAuditoria(usuario, acao, detalhes);
        this.logs.add(log);
        System.out.println("[LOG] " + log.getTimestamp() + " - " + acao + ": " + detalhes);
    }

    // Getters 
    public void adicionarDroneAFrota(Drone d) { 
    	this.frota.add(d); 
    }
    public List<LogAuditoria> getLogs() { 
    	return logs; 
    }
	public List<Drone> getFrota() {
		return frota;
	}	
}