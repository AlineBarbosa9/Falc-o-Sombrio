package model;

import enums.StatusDrone;

public class SistemaComunicacao {
    
	// Atributos Privados
    private String protocolo;
    private boolean conexaoAtiva;
    
    // Construtor
    public SistemaComunicacao(String protocolo) {
        this.protocolo = protocolo;
        this.conexaoAtiva = true;
    }
    
    public void enviarComando(Drone drone, String comando) {
        if (!conexaoAtiva) {
            System.err.println("[COM-ERRO] Falha: Sem sinal com o drone " + drone.getModelo());
            return;
        }

        System.out.println("[COM] Enviando: " + comando + " | Protocolo: " + protocolo);
  
        if (comando.equalsIgnoreCase("ABORTAR")) {
            drone.setStatus(StatusDrone.RETORNANDO);
            System.out.println("[SISTEMA] Comando de emergência: Drone retornando à base.");
        }
    }
    
    public void tentarReconexao() {
        System.out.println("[COM] Tentando reconexão via protocolo secundário...");
   
        this.protocolo = "PROTOCOLO_RESERVA_SATELITAL";
        this.conexaoAtiva = true;
    }

    public void receberTelemetria(Telemetria t) {
        if (conexaoAtiva) {
            System.out.println("--- Pacote de Telemetria Recebido ---");
            System.out.println("Drone ID: " + t.getDroneId());
            System.out.println("Posição: " + t.getLatitude() + ", " + t.getLongitude());
            System.out.println("Velocidade: " + t.getVelocidade() + " km/h");
            System.out.println("Timestamp: " + t.getTimestamp());
            System.out.println("-------------------------------------");
        }
    }
 
    public boolean isConexaoAtiva() { 
    	return conexaoAtiva; 
    }
    
    public void setConexaoAtiva(boolean conexaoAtiva) { 
    	this.conexaoAtiva = conexaoAtiva; 
    }
}