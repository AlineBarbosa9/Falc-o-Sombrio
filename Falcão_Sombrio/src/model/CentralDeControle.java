package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bd.LogAuditoriaDAO;
import enums.TipoAcao;

public class CentralDeControle {

    // Atributos
    private final List<Drone> frota;
    private final List<Missao> missoes;
    private final List<LogAuditoria> logs;

    // Construtor Público
    public CentralDeControle() {
        this.frota = new ArrayList<>();
        this.missoes = new ArrayList<>();
        this.logs = new ArrayList<>();
    }
    
    private void registrarLog(UUID usuarioId, TipoAcao acao, String detalhes) {
        LogAuditoria log = new LogAuditoria(usuarioId, acao, detalhes);
        logs.add(log);

        LogAuditoriaDAO logDAO = new LogAuditoriaDAO();
        logDAO.salvar(log);
    }
    
    public void login(Operador operador, String senha, String tokenMFA) {
        boolean senhaValida = operador.getSenhaHash().equals(senha);
        boolean mfaValido = tokenMFA != null && !tokenMFA.isBlank();

        if (senhaValida && mfaValido) {
            registrarLog(operador.getId(), TipoAcao.LOGIN, "Login realizado com sucesso");
        } else {
            registrarLog(operador.getId(), TipoAcao.FALHA_SEGURANCA, "Falha no login");
            throw new IllegalStateException("Credenciais inválidas");
        }
    }
    
    public void iniciarMissao(Operador operador, Missao missao) {
        if (operador == null || missao == null) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        if (!operador.temPoderDeDecisao()) {
            registrarLog(operador.getId(), TipoAcao.FALHA_SEGURANCA,
                    "Tentativa de iniciar missão sem permissão");
            throw new IllegalStateException("Sem permissão");
        }

        missao.definirOperadorResponsavel(operador.getId());
        missao.iniciarMissao();
        registrarLog(operador.getId(), TipoAcao.ALTERACAO_STATUS,
                "Missão iniciada: " + missao.getId());
    }

    public void abortarMissao(Operador operador, Missao missao, String motivo) {
        if (operador == null || missao == null) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        missao.abortarMissao(motivo);
        registrarLog(operador.getId(), TipoAcao.ALTERACAO_STATUS,
                "Missão abortada: " + motivo);
    }

    public void finalizarMissao(Operador operador, Missao missao) {
        if (operador == null || missao == null) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        missao.finalizarMissao();
        registrarLog(operador.getId(), TipoAcao.ALTERACAO_STATUS,
                "Missão finalizada: " + missao.getId());
    }
    
    public void receberTelemetria(Drone drone, Telemetria t) {

        if (drone == null || t == null) return;

        registrarLog(drone.getId(), TipoAcao.ALTERACAO_STATUS,
                "Telemetria recebida do drone " + drone.getId());
    }
    
    public void enviarComando(Operador operador, UUID droneId, String comando) {

        if (operador == null || droneId == null || comando == null) {
            throw new IllegalArgumentException("Dados inválidos");
            
        }
        
        if (!operador.temPoderDeDecisao()) {
            registrarLog(operador.getId(), TipoAcao.FALHA_SEGURANCA,
                "Tentativa de envio de comando sem permissão");
            throw new IllegalStateException("Sem permissão");
        }

        registrarLog(operador.getId(), TipoAcao.ENVIO_COMANDO,
                "Comando enviado ao drone " + droneId + ": " + comando);
    }
    
    public void adicionarDroneAFrota(Drone drone) {
        if (drone == null) {
            throw new IllegalArgumentException("Drone inválido");
        }
        this.frota.add(drone);
    }

    public void adicionarMissao(Missao missao) {
        if (missao == null) {
            throw new IllegalArgumentException("Missão inválida");
        }
        this.missoes.add(missao);
    }


    // Getters
    public List<Drone> getFrota() {
        return List.copyOf(frota);
    }
    public List<Missao> getMissoes() {
        return List.copyOf(missoes);
    }
    public List<LogAuditoria> getLogs() {
        return List.copyOf(logs);
    }
}