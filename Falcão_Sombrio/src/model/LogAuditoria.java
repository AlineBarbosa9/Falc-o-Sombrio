package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class LogAuditoria {
    
    private final String id;
    private String usuario; 
    private String acao;    
    private LocalDateTime timestamp;
    private String detalhes; 
    
    // Construtor
    public LogAuditoria(String usuario, String acao, String detalhes) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.acao = acao;
        this.detalhes = detalhes;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getId() { 
    	return id; 
    }
    public String getUsuario() { 
    	return usuario; 
    }
    public String getAcao() { 
    	return acao; 
    }
    public LocalDateTime getTimestamp() { 
    	return timestamp; 
    }
    public String getDetalhes() { 
    	return detalhes; 
    }
}