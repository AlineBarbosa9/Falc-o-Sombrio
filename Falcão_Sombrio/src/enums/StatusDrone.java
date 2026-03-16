package enums;

public enum StatusDrone {
	
    IDLE(0, "Aguardando comandos"),
    DECOLANDO(1, "Iniciando propulsão"),
    EM_MISSÃO(2, "Executando rota definida"),
    RETORNANDO(3, "Voltando para a base"),
    ALERTA(4, "Falha crítica ou bateria baixa"),
    MANUTENCAO(5, "Indisponível para operações");
	
	// Atributos Privados
    private final int codigo;
    private final String descricao;
    
    // Construtor
    StatusDrone(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
    // Getters
    public int getCodigo() { 
    	return codigo; 
    }
    public String getDescricao() { 
    	return descricao; 
    }
}
