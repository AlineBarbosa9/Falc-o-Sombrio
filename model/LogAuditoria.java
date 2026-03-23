package model;

import java.time.Instant;
import java.util.UUID;
import enums.TipoAcao;

public class LogAuditoria {
	
	// Atributos Privados
    private final UUID id;
    private final UUID usuarioId;
    private final TipoAcao acao;
    private final String detalhes;
    private final Instant timestamp;
    
    
    // Construtor Público
    public LogAuditoria(UUID usuarioId, TipoAcao acao, String detalhes) {
    	
        if (usuarioId == null) throw new IllegalArgumentException("Usuário inválido");
        if (acao == null) throw new IllegalArgumentException("Ação inválida");

        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.acao = acao;
        this.detalhes = detalhes != null ? detalhes : "";
        this.timestamp = Instant.now();
    }
    
    // Construtor Público
    public LogAuditoria(UUID usuarioId, TipoAcao acao, String detalhes, UUID missaoId, UUID droneID) {
    	
        if (usuarioId == null) throw new IllegalArgumentException("Usuário inválido");
        if (acao == null) throw new IllegalArgumentException("Ação inválida");

        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.acao = acao;
        this.detalhes = detalhes != null ? detalhes : "";
        this.timestamp = Instant.now();
    }
    
    // Getters
    public UUID getId() { 
    	return id; 
    }
    public UUID getUsuarioId() { 
    	return usuarioId; 
    }
    public TipoAcao getAcao() { 
    	return acao; 
    }
    public String getDetalhes() { 
    	return detalhes; 
    }
    public Instant getTimestamp() { 
    	return timestamp; 
    }
}