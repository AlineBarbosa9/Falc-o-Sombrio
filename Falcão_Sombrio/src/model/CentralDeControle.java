package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import enums.TipoAcao;

public class CentralDeControle {

    private final List<Drone> frota;
    private final List<Missao> missoes;
    private final List<LogAuditoria> logs;

    private final SistemaComunicacao sistemaComunicacao;

    public CentralDeControle(SistemaComunicacao sistemaComunicacao) {
        this.frota = new ArrayList<>();
        this.missoes = new ArrayList<>();
        this.logs = new ArrayList<>();
        this.sistemaComunicacao = sistemaComunicacao;
    }

    private void registrarLog(LogAuditoria log) {
        if (log == null) return;

        logs.add(log);
    }

    public void enviarComando(
            Operador operador,
            UUID droneId,
            String comando,
            String assinatura
    ) {

        if (operador == null || droneId == null || comando == null || assinatura == null) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        if (!operador.temPoderDeDecisao()) {

            registrarLog(
                    new LogAuditoria(
                            operador.getId(),
                            TipoAcao.FALHA_SEGURANCA,
                            "Tentativa de comando sem permissão",
                            null,
                            droneId
                    )
            );

            throw new IllegalStateException("Sem permissão");
        }

        boolean enviado =
                sistemaComunicacao.enviarComandoSeguro(
                        droneId,
                        comando,
                        assinatura
                );

        if (enviado) {

            registrarLog(
                    new LogAuditoria(
                            operador.getId(),
                            TipoAcao.ENVIO_COMANDO,
                            "Comando enviado ao drone " + droneId,
                            null,
                            droneId
                    )
            );
        }
    }

    public void receberTelemetria(Drone drone, Telemetria t) {

        if (drone == null || t == null) return;

        sistemaComunicacao.receberTelemetria(t);

        registrarLog(
                new LogAuditoria(
                        drone.getId(),
                        TipoAcao.ALTERACAO_STATUS,
                        "Telemetria recebida",
                        null,
                        drone.getId()
                )
        );
    }

    public void adicionarDroneAFrota(Drone drone) {
        if (drone == null) throw new IllegalArgumentException("Drone inválido");
        frota.add(drone);
    }

    public void adicionarMissao(Missao missao) {
        if (missao == null) throw new IllegalArgumentException("Missão inválida");
        missoes.add(missao);
    }

    public void sincronizarLogsMissao(Missao missao) {
        if (missao == null) return;

        //for (LogAuditoria log : missao.getLogs()) {
            //if (!logs.contains(log)) {
               // logs.add(log);
           // }
        //}
    }

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
