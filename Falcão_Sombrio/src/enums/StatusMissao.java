package enums;

public enum StatusMissao {
	
    AGUARDANDO(0, "Missão criada, drones não vinculados"),
    PREPARADA(1, "Drones prontos e área definida"),
    EM_CURSO(2, "Operação em andamento no campo"),
    SUCESSO(3, "Objetivo atingido e drones seguros"),
    ABORTADA(4, "Cancelada por comando humano"),
    FALHA(5, "Objetivo não atingido ou perda de equipamento");
	
	// Atributos Privados
    private final int codigo;
    private final String descricao;
    
    // Construtor
    StatusMissao(int codigo, String descricao) {
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
