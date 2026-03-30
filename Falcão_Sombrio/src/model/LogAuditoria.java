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
    private final UUID missaoId;
    private final UUID droneId;
    
    public LogAuditoria(UUID usuarioId, TipoAcao acao, String detalhes) {
        this(usuarioId, acao, detalhes, null, null);
    }
    
    // Construtor Público
    public LogAuditoria(UUID usuarioId, TipoAcao acao, String detalhes, UUID missaoId, UUID droneId) {

    	if (usuarioId == null) throw new IllegalArgumentException("Usuário inválido");
    	if (acao == null) throw new IllegalArgumentException("Ação inválida");

    	this.id = UUID.randomUUID();
    	this.usuarioId = usuarioId;
    	this.acao = acao;
    	this.detalhes = (detalhes != null) ? detalhes : "";
    	this.timestamp = Instant.now();
    	this.missaoId = missaoId;
    	this.droneId = droneId;
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
    public UUID getMissaoId() {
        return missaoId;
    }
    public UUID getDroneId() {
        return droneId;
    }
}