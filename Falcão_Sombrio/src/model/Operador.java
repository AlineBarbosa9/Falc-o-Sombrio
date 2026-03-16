package model;

import java.util.UUID;

public class Operador {
	
	// Atributos Privados
    private UUID id;
    private String nome;
    private String email;
    private String nivelAcesso;
    private String mfaSecret;

    public Operador(UUID id, String nome, String nivelAcesso) {
        this.id = id;
        this.nome = nome;
        this.nivelAcesso = nivelAcesso;
    }
    
    // temporario
    public boolean autenticar(String senha) {
        return true;
    }

    public void iniciarMissao(Missao missao) {
        System.out.println("[SISTEMA] Operador " + nome + " autorizou o início da Missão: " + missao.getId());
    }

    // Getters
    public UUID getId() { 
    	return id; 
    }
    public String getNome() { 
    	return nome; 
    }
    public String getNivelAcesso() { 
    	return nivelAcesso; 
    }
    public String getEmail() { 
    	return email; 
    }
}