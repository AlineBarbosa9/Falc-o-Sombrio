package model;

import java.util.UUID;

public class SistemaComunicacao {

	// Atributos Privados
    private String protocolo;
    private boolean conexaoAtiva;
    private int tentativasFalhas;
    
    private static final int LIMITE_TENTATIVAS = 3;

    public SistemaComunicacao(String protocolo) {
        this.setProtocolo(protocolo);
        this.conexaoAtiva = true;
        this.tentativasFalhas = 0;
    }

    public boolean enviarComandoSeguro(UUID droneId, String comando, String assinatura) {

        if (!validarAssinatura(assinatura)) {
            return false;
        }

        if (!conexaoAtiva) {
            registrarFalha();
            return false;
        }

        tentativasFalhas = 0;
        return true;
    }

    public boolean receberTelemetria(Telemetria t) {

        if (!conexaoAtiva || t == null) {
            return false;
        }

        return true;
    }

    private void registrarFalha() {
        tentativasFalhas++;

        if (tentativasFalhas >= LIMITE_TENTATIVAS) {
            tentarReconexao();
        }
        
        
    }

    public void tentarReconexao() {
        this.setProtocolo("SAT_ENCRYPT_LNK");
        this.conexaoAtiva = true;
        
        this.tentativasFalhas = 0;
    }

    
    private boolean validarAssinatura(String assinatura) {
        return assinatura != null && !assinatura.isBlank();
    }

   
    public boolean isConexaoAtiva() {
        return conexaoAtiva;
    }

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
}