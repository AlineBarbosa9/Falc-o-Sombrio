package model;

public class Sensor {
	
	// Atributos Privados 
    private String tipo;
    private String status;
    
    // Construtor
    public Sensor(String tipo) {
        this.tipo = tipo;
        this.status = "OPERACIONAL";
    }

    public void coletarDados() {
        if (this.status.equals("OPERACIONAL")) {
            System.out.println("[SENSOR] Coletando dados do tipo: " + tipo);
        } else {
            System.out.println("[ALERTA] Sensor " + tipo + " está fora de serviço.");
        }
    }
    
    // Getters e Setters
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}