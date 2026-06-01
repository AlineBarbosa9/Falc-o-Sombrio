package model;

import enums.NivelAcesso;
import enums.StatusDrone;
import enums.StatusMissao;
import enums.StatusSensor;
import enums.TipoAcao;
import enums.TipoSensor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Main {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                             .withZone(ZoneId.systemDefault());

    // --- Sensor concreto para demonstracao ---
    static class SensorCamera extends Sensor {
        private String ultimaLeitura;

        public SensorCamera() {
            super(TipoSensor.CAMERA);
        }

        @Override
        public void coletarDados() {
            this.ultimaLeitura = "Frame capturado em " + Instant.now();
        }

        public String getUltimaLeitura() {
            return ultimaLeitura;
        }
    }

    static class SensorLidar extends Sensor {
        private double distanciaDetectada;

        public SensorLidar() {
            super(TipoSensor.LIDAR);
        }

        @Override
        public void coletarDados() {
            this.distanciaDetectada = 42.5;
        }

        public double getDistanciaDetectada() {
            return distanciaDetectada;
        }
    }

    static class SensorGPS extends Sensor {
        private String sinal;

        public SensorGPS() {
            super(TipoSensor.GPS);
        }

        @Override
        public void coletarDados() {
            this.sinal = "GPS bloqueado em satélites: 8";
        }

        public String getSinal() {
            return sinal;
        }
    }

    // --- Utilitario de impressao ---
    static void secao(String titulo) {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("  " + titulo);
        System.out.println("=".repeat(60));
    }

    static void subsecao(String titulo) {
        System.out.println();
        System.out.println("  -- " + titulo + " --");
    }

    static void linha(String chave, Object valor) {
        System.out.printf("  %-30s %s%n", chave + ":", valor);
    }

    static void info(String mensagem) {
        System.out.println("  " + mensagem);
    }

    // =========================================================
    public static void main(String[] args) {

        System.out.println();
        System.out.println("  SISTEMA FALCAO SOMBRIO - DEMONSTRACAO COMPLETA");
        System.out.println("  Iniciado em: " + FORMATTER.format(Instant.now()));

        // =====================================================
        secao("1. ENUMS DO SISTEMA");
        // =====================================================

        subsecao("StatusDrone");
        for (StatusDrone s : StatusDrone.values()) {
            linha(s.name() + " [" + s.getCodigo() + "]", s.getDescricao());
        }

        subsecao("StatusMissao");
        for (StatusMissao s : StatusMissao.values()) {
            linha(s.name() + " [" + s.getCodigo() + "]", s.getDescricao());
        }

        subsecao("NivelAcesso");
        for (NivelAcesso n : NivelAcesso.values()) {
            linha(n.name() + " [prioridade " + n.getPrioridade() + "]", n.getDescricao());
        }

        subsecao("TipoSensor");
        for (TipoSensor t : TipoSensor.values()) {
            info("- " + t.name());
        }

        subsecao("StatusSensor");
        for (StatusSensor s : StatusSensor.values()) {
            info("- " + s.name());
        }

        subsecao("TipoAcao");
        for (TipoAcao a : TipoAcao.values()) {
            info("- " + a.name());
        }

        // =====================================================
        secao("2. COORDENADAS");
        // =====================================================

        Coordenadas base        = new Coordenadas(-23.5505, -46.6333, 0.0);
        Coordenadas alvo        = new Coordenadas(-23.5600, -46.6400, 150.0);
        Coordenadas pontoProximo = new Coordenadas(-23.5510, -46.6335, 5.0);

        subsecao("Ponto base");
        linha("Localizacao", base);

        subsecao("Ponto alvo");
        linha("Localizacao", alvo);

        subsecao("Calculos");
        linha("Distancia 3D base -> alvo", String.format("%.2f m", base.calcularDistancia(alvo)));
        linha("Distancia 2D base -> alvo", String.format("%.2f m", base.calcularDistancia2D(alvo)));
        linha("Alvo dentro de 5 km?",      base.isProximo(alvo, 5000) ? "Sim" : "Nao");
        linha("PontoProximo igual a base?", base.isIgual(pontoProximo) ? "Sim" : "Nao");

        // =====================================================
        secao("3. OPERADORES");
        // =====================================================

        Operador comandante = new Operador(
                "Coronel Santos",
                "santos@falcao.mil",
                "hash_senha_segura_123",
                NivelAcesso.COMANDANTE
        );
        comandante.setMfaSecret("MFA_SECRET_CORONEL");

        Operador soldado = new Operador(
                "Cabo Ferreira",
                "ferreira@falcao.mil",
                "hash_senha_campo_456",
                NivelAcesso.SOLDADO
        );

        subsecao("Comandante");
        linha("ID",            comandante.getId());
        linha("Nome",          comandante.getNome());
        linha("Email",         comandante.getEmail());
        linha("Nivel de acesso", comandante.getNivelAcesso().name() + " - " + comandante.getNivelAcesso().getDescricao());
        linha("Poder de decisao", comandante.temPoderDeDecisao() ? "Sim" : "Nao");

        subsecao("Validacao de acesso - credenciais corretas");
        boolean acessoValido = comandante.validarAcesso("hash_senha_segura_123", "TOKEN_MFA_001");
        linha("Resultado", acessoValido ? "Acesso concedido" : "Acesso negado");

        subsecao("Validacao de acesso - senha errada");
        boolean acessoInvalido = comandante.validarAcesso("senha_errada", "TOKEN_MFA_001");
        linha("Resultado", acessoInvalido ? "Acesso concedido" : "Acesso negado");

        subsecao("Soldado");
        linha("ID",            soldado.getId());
        linha("Nome",          soldado.getNome());
        linha("Nivel de acesso", soldado.getNivelAcesso().name() + " - " + soldado.getNivelAcesso().getDescricao());
        linha("Poder de decisao", soldado.temPoderDeDecisao() ? "Sim" : "Nao");

        // =====================================================
        secao("4. SENSORES");
        // =====================================================

        SensorCamera  camera = new SensorCamera();
        SensorLidar   lidar  = new SensorLidar();
        SensorGPS     gps    = new SensorGPS();

        camera.coletarDados();
        lidar.coletarDados();
        gps.coletarDados();

        subsecao("Camera");
        linha("ID",     camera.getId());
        linha("Tipo",   camera.getTipo());
        linha("Status", camera.getStatus());
        linha("Leitura", camera.getUltimaLeitura());

        subsecao("Lidar");
        linha("ID",               lidar.getId());
        linha("Tipo",             lidar.getTipo());
        linha("Status",           lidar.getStatus());
        linha("Distancia detectada", lidar.getDistanciaDetectada() + " m");

        subsecao("GPS");
        linha("ID",     gps.getId());
        linha("Tipo",   gps.getTipo());
        linha("Status", gps.getStatus());
        linha("Sinal",  gps.getSinal());

        subsecao("Alterando status do LIDAR para FALHA");
        lidar.atualizarStatus(StatusSensor.FALHA);
        lidar.verificarStatus();
        linha("Novo status LIDAR", lidar.getStatus());

        // =====================================================
        secao("5. DRONES");
        // =====================================================

        Drone drone1 = new Drone("Falcon-X1", base);
        Drone drone2 = new Drone("Falcon-X2", new Coordenadas(-23.5490, -46.6320, 0.0));

        drone1.adicionarSensor(camera);
        drone1.adicionarSensor(gps);
        drone2.adicionarSensor(new SensorLidar());

        subsecao("Drone 1");
        linha("ID",       drone1.getId());
        linha("Modelo",   drone1.getModelo());
        linha("Status",   drone1.getStatus());
        linha("Bateria",  drone1.getBateria() + "%");
        linha("Localizacao", drone1.getLocalizacao());
        linha("Sensores", drone1.getSensores().size());

        subsecao("Sincronizando sensores do Drone 1");
        drone1.sincronizarSensores();
        info("Sensores sincronizados com sucesso.");

        subsecao("Movimentacao do Drone 1");
        Coordenadas destino = new Coordenadas(-23.5550, -46.6360, 100.0);
        drone1.moverPara(destino, 5.0);
        linha("Nova localizacao", drone1.getLocalizacao());
        linha("Bateria apos movimento", drone1.getBateria() + "%");

        subsecao("Tentativa de movimento com bateria insuficiente (Drone 2 - forcado)");
        Drone droneAlerta = new Drone(
                UUID.randomUUID(), "Falcon-X3",
                new Coordenadas(-23.555, -46.635, 50.0),
                StatusDrone.IDLE, 14.0
        );
        try {
            droneAlerta.moverPara(new Coordenadas(-23.560, -46.640, 50.0), 1.0);
        } catch (IllegalStateException e) {
            linha("Excecao capturada", e.getMessage());
            linha("Status apos alerta", droneAlerta.getStatus());
        }

        // =====================================================
        secao("6. SISTEMA DE COMUNICACAO");
        // =====================================================

        SistemaComunicacao comunicacao = new SistemaComunicacao("AES_ENCRYPT_LNK");

        subsecao("Estado inicial");
        linha("Protocolo",      comunicacao.getProtocolo());
        linha("Conexao ativa",  comunicacao.isConexaoAtiva() ? "Sim" : "Nao");

        subsecao("Envio de comando seguro - assinatura valida");
        boolean cmdValido = comunicacao.enviarComandoSeguro(drone1.getId(), "INICIAR_PATRULHA", "SIG_001");
        linha("Resultado", cmdValido ? "Comando enviado" : "Comando rejeitado");

        subsecao("Envio de comando seguro - assinatura nula");
        boolean cmdInvalido = comunicacao.enviarComandoSeguro(drone1.getId(), "INICIAR_PATRULHA", null);
        linha("Resultado", cmdInvalido ? "Comando enviado" : "Comando rejeitado");

        subsecao("Recebendo telemetria");
        Telemetria telemetriaRecebida = new Telemetria(
                drone1.getId(), null,
                drone1.getLocalizacao().getLatitude(),
                drone1.getLocalizacao().getLongitude(),
                drone1.getLocalizacao().getAltitude(),
                35.0, Instant.now()
        );
        boolean telOk = comunicacao.receberTelemetria(telemetriaRecebida);
        linha("Telemetria aceita", telOk ? "Sim" : "Nao");

        subsecao("Reconexao");
        comunicacao.tentarReconexao();
        linha("Protocolo apos reconexao", comunicacao.getProtocolo());
        linha("Conexao ativa", comunicacao.isConexaoAtiva() ? "Sim" : "Nao");

        // =====================================================
        secao("7. TELEMETRIA");
        // =====================================================

        Telemetria telemetria = new Telemetria(
                drone1.getId(),
                null,
                drone1.getLocalizacao().getLatitude(),
                drone1.getLocalizacao().getLongitude(),
                drone1.getLocalizacao().getAltitude(),
                42.7,
                Instant.now()
        );

        linha("ID",           telemetria.getId());
        linha("Drone ID",     telemetria.getDroneId());
        linha("Latitude",     telemetria.getLatitude());
        linha("Longitude",    telemetria.getLongitude());
        linha("Altitude",     telemetria.getAltitude() + " m");
        linha("Velocidade",   telemetria.getVelocidade() + " km/h");
        linha("Timestamp",    FORMATTER.format(telemetria.getTimestamp()));
        linha("toString()",   telemetria);

        // =====================================================
        secao("8. LOG DE AUDITORIA");
        // =====================================================

        LogAuditoria logLogin = new LogAuditoria(
                comandante.getId(),
                TipoAcao.LOGIN,
                "Operador autenticado com MFA"
        );

        LogAuditoria logComando = new LogAuditoria(
                comandante.getId(),
                TipoAcao.ENVIO_COMANDO,
                "Comando INICIAR_PATRULHA enviado",
                null,
                drone1.getId()
        );

        LogAuditoria logFalha = new LogAuditoria(
                soldado.getId(),
                TipoAcao.FALHA_SEGURANCA,
                "Tentativa de comando sem permissao",
                null,
                drone2.getId()
        );

        List<LogAuditoria> logsDemo = List.of(logLogin, logComando, logFalha);

        subsecao("Registros gerados");
        for (LogAuditoria log : logsDemo) {
            System.out.println();
            linha("  Log ID",    log.getId());
            linha("  Usuario",   log.getUsuarioId());
            linha("  Acao",      log.getAcao());
            linha("  Detalhes",  log.getDetalhes());
            linha("  Drone ID",  log.getDroneId() != null ? log.getDroneId() : "N/A");
            linha("  Missao ID", log.getMissaoId() != null ? log.getMissaoId() : "N/A");
            linha("  Timestamp", FORMATTER.format(log.getTimestamp()));
        }

        // =====================================================
        secao("9. MISSAO");
        // =====================================================

        Missao missao = new Missao(
                "Reconhecimento noturno setor Alfa",
                new Coordenadas(-23.5580, -46.6380, 200.0),
                500.0
        );
        missao.definirOperadorResponsavel(comandante.getId());
        missao.alocarDrone(drone1.getId());
        missao.alocarDrone(drone2.getId());

        subsecao("Missao criada");
        linha("ID",              missao.getId());
        linha("Objetivo",        missao.getObjetivo());
        linha("Status inicial",  missao.getStatus() + " - " + missao.getStatus().getDescricao());
        linha("Ponto de entrada", missao.getPontoEntrada());
        linha("Raio de operacao", missao.getRaioOperacao() + " m");
        linha("Drones alocados", missao.getDronesIds().size());
        linha("Operador resp.",  missao.getOperadorResponsavel());

        subsecao("Tentativa de alocacao duplicada");
        try {
            missao.alocarDrone(drone1.getId());
        } catch (IllegalStateException e) {
            linha("Excecao capturada", e.getMessage());
        }

        subsecao("Verificacao de drone alocado");
        linha("Drone 1 alocado?", missao.isDroneAlocado(drone1.getId()) ? "Sim" : "Nao");
        linha("ID aleatorio alocado?", missao.isDroneAlocado(UUID.randomUUID()) ? "Sim" : "Nao");

        subsecao("Iniciando missao");
        missao.iniciarMissao();
        linha("Status apos inicio", missao.getStatus() + " - " + missao.getStatus().getDescricao());
        linha("Data inicio",        FORMATTER.format(missao.getDataInicio()));

        subsecao("Tentativa de iniciar missao ja em curso");
        try {
            missao.iniciarMissao();
        } catch (IllegalStateException e) {
            linha("Excecao capturada", e.getMessage());
        }

        subsecao("Abortando missao");
        missao.abortarMissao("Perda de sinal com drone-2");
        linha("Status apos aborto", missao.getStatus() + " - " + missao.getStatus().getDescricao());
        linha("Motivo do aborto",   missao.getMotivoAborto());
        linha("Data fim",           FORMATTER.format(missao.getDataFim()));
        linha("toString()",         missao);

        subsecao("Nova missao para demonstrar finalizacao com sucesso");
        Missao missao2 = new Missao(
                "Vigilancia perimetro Beta",
                new Coordenadas(-23.5620, -46.6410, 180.0),
                300.0
        );
        missao2.definirOperadorResponsavel(comandante.getId());
        missao2.alocarDrone(drone2.getId());
        missao2.iniciarMissao();
        missao2.finalizarMissao();
        linha("Status apos finalizacao", missao2.getStatus() + " - " + missao2.getStatus().getDescricao());
        linha("Data fim",                FORMATTER.format(missao2.getDataFim()));

        // =====================================================
        secao("10. NAVEGACAO INTELIGENTE");
        // =====================================================

        NavegacaoInteligente nav = new NavegacaoInteligente();

        subsecao("Calculo de rota");
        String rota = nav.calcularRota(base, alvo);
        linha("Resultado", rota);

        subsecao("Deteccao de ameaca por bateria");
        linha("Bateria 14% (abaixo do limite)", nav.detectarAmeaca(14.0) ? "Ameaca detectada" : "Sem ameaca");
        linha("Bateria 80% (acima do limite)",  nav.detectarAmeaca(80.0) ? "Ameaca detectada" : "Sem ameaca");

        subsecao("Desvio de obstaculo");
        nav.desviarObstaculo(drone1);
        info("desviarObstaculo executado sem excecao.");

        // =====================================================
        secao("11. CENTRAL DE CONTROLE");
        // =====================================================

        SistemaComunicacao comCentral = new SistemaComunicacao("AES_ENCRYPT_LNK");
        CentralDeControle central = new CentralDeControle(comCentral);

        Drone drone3 = new Drone("Falcon-X4", new Coordenadas(-23.5500, -46.6330, 0.0));
        Drone drone4 = new Drone("Falcon-X5", new Coordenadas(-23.5495, -46.6325, 0.0));

        central.adicionarDroneAFrota(drone3);
        central.adicionarDroneAFrota(drone4);

        Missao missaoCentral = new Missao(
                "Patrulha setor Gama",
                new Coordenadas(-23.5530, -46.6350, 120.0),
                400.0
        );
        central.adicionarMissao(missaoCentral);

        subsecao("Estado da central");
        linha("Drones na frota", central.getFrota().size());
        linha("Missoes registradas", central.getMissoes().size());
        linha("Logs de auditoria", central.getLogs().size());

        subsecao("Envio de comando pelo comandante");
        central.enviarComando(
                comandante,
                drone3.getId(),
                "DECOLAR",
                "SIG_CMD_002"
        );
        linha("Logs apos comando", central.getLogs().size());

        subsecao("Tentativa de comando pelo soldado (sem permissao)");
        try {
            central.enviarComando(
                    soldado,
                    drone3.getId(),
                    "DECOLAR",
                    "SIG_CMD_003"
            );
        } catch (IllegalStateException e) {
            linha("Excecao capturada", e.getMessage());
            linha("Logs apos falha de seguranca", central.getLogs().size());
        }

        subsecao("Recepcao de telemetria pela central");
        Telemetria telCentral = new Telemetria(
                drone3.getId(), missaoCentral.getId(),
                -23.5530, -46.6350, 120.0,
                28.5, Instant.now()
        );
        central.receberTelemetria(drone3, telCentral);
        linha("Logs apos telemetria", central.getLogs().size());

        subsecao("Frota registrada");
        for (Drone d : central.getFrota()) {
            linha("  " + d.getModelo(), "ID=" + d.getId() + " | Status=" + d.getStatus() + " | Bateria=" + d.getBateria() + "%");
        }

        subsecao("Missoes registradas");
        for (Missao m : central.getMissoes()) {
            linha("  " + m.getObjetivo(), "Status=" + m.getStatus());
        }

        subsecao("Logs de auditoria na central");
        for (LogAuditoria log : central.getLogs()) {
            linha("  " + log.getAcao(), log.getDetalhes());
        }

        // =====================================================
        secao("DEMONSTRACAO CONCLUIDA");
        // =====================================================
        System.out.println("  Todos os recursos do sistema foram exercitados.");
        System.out.println("  Encerrado em: " + FORMATTER.format(Instant.now()));
        System.out.println();
    }
}
