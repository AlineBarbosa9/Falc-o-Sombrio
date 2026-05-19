package model;

import java.util.UUID;

import enums.NivelAcesso;

public class Operador {

    private final UUID id;
    private String nome;
    private String email;
    private String senhaHash;
    private NivelAcesso nivelAcesso;
    private String mfaSecret;

    public Operador(String nome, String email, String senhaHash, NivelAcesso nivelAcesso) {

        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome inválido");
        if (email == null || email.isBlank() || !email.contains("@")) throw new IllegalArgumentException("Email inválido");
        if (senhaHash == null || senhaHash.isBlank()) throw new IllegalArgumentException("Senha inválida");
        if (nivelAcesso == null) throw new IllegalArgumentException("Nível inválido");

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.setEmail(email);
        this.senhaHash = senhaHash;
        this.nivelAcesso = nivelAcesso;
    }

    public boolean validarAcesso(String senha, String tokenMFA) {

        boolean senhaValida = senha != null && senhaHash.equals(senha);
        boolean mfaValido = tokenMFA != null && !tokenMFA.isBlank();

        return senhaValida && mfaValido;
    }

    public boolean temPoderDeDecisao() {
        return nivelAcesso == NivelAcesso.COMANDANTE;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMfaSecret() {
		return mfaSecret;
	}

	public void setMfaSecret(String mfaSecret) {
		this.mfaSecret = mfaSecret;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}
}
