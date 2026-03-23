package model;

import java.util.UUID;

import enums.NivelAcesso;
import enums.TipoAcao;

public class Operador {

    private final UUID id;
    private String nome;
    private String email;
    private String senhaHash;
    private NivelAcesso nivelAcesso;
    private String mfaSecret;

    // Construtor Público
    public Operador(String nome, String email, String senhaHash, NivelAcesso nivelAcesso) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (senhaHash == null || senhaHash.isBlank()) {
            throw new IllegalArgumentException("Senha inválida");
        }

        if (nivelAcesso == null) {
            throw new IllegalArgumentException("Nível de acesso inválido");
        }

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.nivelAcesso = nivelAcesso;
    }

    // Validação de Acesso Simplificado
    public void validarAcesso(String senha, String tokenMFA) {

      
        boolean senhaValida = this.senhaHash.equals(senha);
        boolean mfaValido = tokenMFA != null && !tokenMFA.isBlank();

        // Condição de Validação
        // Gerar Log
    }

    public boolean temPoderDeDecisao() {
        return this.nivelAcesso == NivelAcesso.COMANDANTE;
    }

    public void autorizarInicioMissao(Missao missao) {
        if (missao == null) {
            throw new IllegalArgumentException("Missão inválida");
        }
        
        if (!temPoderDeDecisao()) {
            throw new IllegalStateException("Operador sem permissão");
        }
        missao.definirOperadorResponsavel(this.id);
    }

    
    
    public LogAuditoria gerarLog(TipoAcao tipo, String detalhes) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de ação inválido");
        }

        return new LogAuditoria(this.id, tipo, detalhes);
    }


    // Getters
    public UUID getId() { 
    	return id; 
    }
    public String getNome() { 
    	return nome; 
    }
    public String getEmail() { 
    	return email; 
    }
    public NivelAcesso getNivelAcesso() { 
    	return nivelAcesso; 
    }
    public String getSenhaHash() {
        return senhaHash;
    }
    public String getMfaSecret() {
        return mfaSecret;
    }

    // Setters
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }
    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }
    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        if (nivelAcesso == null) {
            throw new IllegalArgumentException("Nível inválido");
        }
        this.nivelAcesso = nivelAcesso;
    }
}