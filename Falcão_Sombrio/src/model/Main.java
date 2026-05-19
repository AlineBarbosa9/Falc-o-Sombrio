package model;

import java.time.Instant;
import java.util.UUID;

import enums.NivelAcesso;
import enums.StatusDrone;
import enums.StatusSensor;
import enums.TipoSensor;

public class Main {

    // ── Constantes de formatação ─────────────────────────────
    private static final String VERDE    = "\u001B[32m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String AMARELO  = "\u001B[33m";
    private static final String AZUL     = "\u001B[34m";
    private static final String ROXO     = "\u001B[35m";
    private static final String RESET    = "\u001B[0m";
    private static final String NEGRITO  = "\u001B[1m";

    private static int totalTestes = 0;
    private static int testesPassaram = 0;
    private static int testesFalharam = 0;

    // ── Subclasse concreta de Sensor para testes ─────────────
    static class SensorGPS extends Sensor {
        private boolean alertaGerado = false;

        public SensorGPS() {
            super(TipoSensor.GPS);
        }

        @Override
        public void coletarDados() {
            System.out.println("    " + AZUL + "[GPS] Dados coletados: posição global atualizada" + RESET);
        }

        @Override
        protected void gerarAlerta() {
            alertaGerado = true;
            System.out.println("    " + AMARELO + "[GPS] ALERTA gerado — sensor em falha!" + RESET);
        }

        public boolean isAlertaGerado() { return alertaGerado; }
    }

    static class SensorLidar extends Sensor {
        public SensorLidar() {
            super(TipoSensor.LIDAR);
        }

        @Override
        public void coletarDados() {
            System.out.println("    " + AZUL + "[LIDAR] Mapeamento 3D concluído" + RESET);
        }
    }

    static class SensorCamera extends Sensor {
        public SensorCamera() {
            super(TipoSensor.CAMERA);
        }

        @Override
        public void coletarDados() {
            System.out.println("    " + AZUL + "[CAMERA] Captura visual processada" + RESET);
        }
    }

    // ── Helpers de teste ─────────────────────────────────────
    static void secao(String titulo) {
        System.out.println("\n" + ROXO + NEGRITO +
                "═══════════════════════════════════════════════════════" + RESET);
        System.out.println(ROXO + NEGRITO + "  " + titulo + RESET);
        System.out.println(ROXO + NEGRITO +
                "═══════════════════════════════════════════════════════" + RESET);
    }

    static void subsecao(String titulo) {
        System.out.println("\n" + AZUL + NEGRITO + "  ── " + titulo + " ──" + RESET);
    }

    static void ok(String descricao) {
        totalTestes++;
        testesPassaram++;
        System.out.println("  " + VERDE + "✔ PASSOU" + RESET + " — " + descricao);
    }

    static void falhou(String descricao, String motivo) {
        totalTestes++;
        testesFalharam++;
        System.out.println("  " + VERMELHO + "✘ FALHOU" + RESET + " — " + descricao);
        System.out.println("         " + VERMELHO + "Motivo: " + motivo + RESET);
    }

    static void testar(String descricao, Runnable acao) {
        try {
            acao.run();
            ok(descricao);
        } catch (AssertionError e) {
            falhou(descricao, e.getMessage());
        } catch (Exception e) {
            falhou(descricao, "Exceção inesperada: " + e.getMessage());
        }
    }

    static void testarExcecao(String descricao, Class<? extends Exception> esperada, Runnable acao) {
        totalTestes++;
        try {
            acao.run();
            testesFalharam++;
            System.out.println("  " + VERMELHO + "✘ FALHOU" + RESET + " — " + descricao);
            System.out.println("         " + VERMELHO + "Esperava " + esperada.getSimpleName() + " mas nenhuma exceção foi lançada" + RESET);
        } catch (Exception e) {
            if (esperada.isInstance(e)) {
                testesPassaram++;
                System.out.println("  " + VERDE + "✔ PASSOU" + RESET + " — " + descricao +
                        AMARELO + " [" + e.getClass().getSimpleName() + ": " + e.getMessage() + "]" + RESET);
            } else {
                testesFalharam++;
                System.out.println("  " + VERMELHO + "✘ FALHOU" + RESET + " — " + descricao);
                System.out.println("         " + VERMELHO + "Esperava " + esperada.getSimpleName() +
                        " mas recebeu " + e.getClass().getSimpleName() + ": " + e.getMessage() + RESET);
            }
        }
    }

    static void assertTrue(boolean condicao, String msg) {
        if (!condicao) throw new AssertionError(msg);
    }

    static void assertEquals(Object esperado, Object atual, String msg) {
        if (!esperado.equals(atual))
            throw new AssertionError(msg + " — esperado: " + esperado + ", obtido: " + atual);
    }

    // ════════════════════════════════════════════════════════
    //  MAIN
    // ════════════════════════════════════════════════════════
    public static void main(String[] args) {

        System.out.println(ROXO + NEGRITO);
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║         FALCÃO SOMBRIO — SUITE DE TESTES              ║");
        System.out.println("║         Sistema de Controle de Drones Autônomos       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println(RESET);

        testarCoordenadas();
        testarOperador();
        testarSensor();
        testarDrone();
        testarSistemaComunicacao();
        testarNavegacaoInteligente();
        testarTelemetria();
        testarLogAuditoria();
        testarMissao();
        testarCentralDeControle();
        testarFluxoCompleto();
        testarCasosLimite();

        relatorioFinal();
    }

    // ════════════════════════════════════════════════════════
    //  1. COORDENADAS
    // ════════════════════════════════════════════════════════
    static void testarCoordenadas() {
        secao("1. COORDENADAS");

        subsecao("Criação válida");
        testar("Cria coordenada com lat/lon/alt válidos", () -> {
            Coordenadas c = new Coordenadas(-23.5505, -46.6333, 100.0);
            assertEquals(-23.5505, c.getLatitude(), "Latitude");
            assertEquals(-46.6333, c.getLongitude(), "Longitude");
            assertEquals(100.0, c.getAltitude(), "Altitude");
        });

        subsecao("Validações de entrada");
        testarExcecao("Latitude inválida (+91) lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Coordenadas(91.0, 0.0, 0.0));

        testarExcecao("Latitude inválida (-91) lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Coordenadas(-91.0, 0.0, 0.0));

        testarExcecao("Longitude inválida (+181) lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Coordenadas(0.0, 181.0, 0.0));

        subsecao("Cálculos de distância");
        testar("calcularDistancia() retorna valor positivo entre dois pontos", () -> {
            Coordenadas sp = new Coordenadas(-23.5505, -46.6333, 0.0);
            Coordenadas rj = new Coordenadas(-22.9068, -43.1729, 0.0);
            double dist = sp.calcularDistancia(rj);
            assertTrue(dist > 0, "Distância deve ser positiva");
            assertTrue(dist > 300_000, "SP→RJ deve ser > 300km");
        });

        testar("calcularDistancia2D() ignora altitude", () -> {
            Coordenadas a = new Coordenadas(-23.5505, -46.6333, 0.0);
            Coordenadas b = new Coordenadas(-23.5505, -46.6333, 9999.0);
            double dist2D = a.calcularDistancia2D(b);
            assertTrue(dist2D < 1.0, "Mesma lat/lon = distância 2D próxima de 0");
        });

        testar("isProximo() retorna true dentro do raio", () -> {
            Coordenadas base = new Coordenadas(-23.5505, -46.6333, 0.0);
            Coordenadas perto = new Coordenadas(-23.5506, -46.6334, 0.0);
            assertTrue(base.isProximo(perto, 1000), "Deve estar dentro de 1km");
        });

        testar("isProximo() retorna false fora do raio", () -> {
            Coordenadas sp = new Coordenadas(-23.5505, -46.6333, 0.0);
            Coordenadas rj = new Coordenadas(-22.9068, -43.1729, 0.0);
            assertTrue(!sp.isProximo(rj, 100), "SP e RJ não estão a 100m um do outro");
        });

        testar("isIgual() retorna true para coordenadas idênticas", () -> {
            Coordenadas a = new Coordenadas(-23.5505, -46.6333, 100.0);
            Coordenadas b = new Coordenadas(-23.5505, -46.6333, 100.0);
            assertTrue(a.isIgual(b), "Coordenadas idênticas devem ser iguais");
        });

        testar("isIgual() retorna false para coordenadas diferentes", () -> {
            Coordenadas a = new Coordenadas(-23.5505, -46.6333, 100.0);
            Coordenadas b = new Coordenadas(-22.9068, -43.1729, 100.0);
            assertTrue(!a.isIgual(b), "Coordenadas diferentes não devem ser iguais");
        });

        testar("toString() formata corretamente", () -> {
            Coordenadas c = new Coordenadas(-23.5505, -46.6333, 100.0);
            assertTrue(c.toString().contains("Lat"), "toString deve conter 'Lat'");
        });
    }

    // ════════════════════════════════════════════════════════
    //  2. OPERADOR
    // ════════════════════════════════════════════════════════
    static void testarOperador() {
        secao("2. OPERADOR");

        subsecao("Criação e atributos");
        testar("Cria operador COMANDANTE corretamente", () -> {
            Operador op = new Operador("Capitão Silva", "silva@falcao.mil", "senha123", NivelAcesso.COMANDANTE);
            assertEquals("Capitão Silva", op.getNome(), "Nome");
            assertEquals(NivelAcesso.COMANDANTE, op.getNivelAcesso(), "Nível");
            assertTrue(op.getId() != null, "ID não pode ser nulo");
        });

        testarExcecao("Nome vazio lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Operador("", "x@x.com", "hash", NivelAcesso.SOLDADO));

        testarExcecao("Email inválido lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Operador("Nome", "emailsemarroba", "hash", NivelAcesso.SOLDADO));

        testarExcecao("Senha nula lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Operador("Nome", "x@x.com", null, NivelAcesso.SOLDADO));

        subsecao("Autenticação 2FA");
        testar("validarAcesso() retorna true com credenciais corretas", () -> {
            Operador op = new Operador("Silva", "s@f.mil", "senhaCorreta", NivelAcesso.COMANDANTE);
            assertTrue(op.validarAcesso("senhaCorreta", "token123"), "Deve autenticar");
        });

        testar("validarAcesso() retorna false com senha errada", () -> {
            Operador op = new Operador("Silva", "s@f.mil", "senhaCorreta", NivelAcesso.COMANDANTE);
            assertTrue(!op.validarAcesso("senhaErrada", "token123"), "Não deve autenticar");
        });

        testar("validarAcesso() retorna false com token MFA nulo", () -> {
            Operador op = new Operador("Silva", "s@f.mil", "senhaCorreta", NivelAcesso.COMANDANTE);
            assertTrue(!op.validarAcesso("senhaCorreta", null), "Sem MFA não autentica");
        });

        testar("validarAcesso() retorna false com token MFA vazio", () -> {
            Operador op = new Operador("Silva", "s@f.mil", "senhaCorreta", NivelAcesso.COMANDANTE);
            assertTrue(!op.validarAcesso("senhaCorreta", "  "), "MFA em branco não autentica");
        });

        subsecao("Controle de acesso");
        testar("temPoderDeDecisao() retorna true para COMANDANTE", () -> {
            Operador op = new Operador("Cmd", "c@f.mil", "h", NivelAcesso.COMANDANTE);
            assertTrue(op.temPoderDeDecisao(), "COMANDANTE tem poder de decisão");
        });

        testar("temPoderDeDecisao() retorna false para SOLDADO", () -> {
            Operador op = new Operador("Sol", "s@f.mil", "h", NivelAcesso.SOLDADO);
            assertTrue(!op.temPoderDeDecisao(), "SOLDADO não tem poder de decisão");
        });
    }

    // ════════════════════════════════════════════════════════
    //  3. SENSOR
    // ════════════════════════════════════════════════════════
    static void testarSensor() {
        secao("3. SENSOR");

        subsecao("Subclasses concretas");
        testar("SensorGPS criado com tipo correto e status OPERACIONAL", () -> {
            SensorGPS gps = new SensorGPS();
            assertEquals(TipoSensor.GPS, gps.getTipo(), "Tipo");
            assertEquals(StatusSensor.OPERACIONAL, gps.getStatus(), "Status inicial");
            assertTrue(gps.getId() != null, "ID não nulo");
        });

        testar("coletarDados() executa sem exceção (GPS)", () -> {
            new SensorGPS().coletarDados();
        });

        testar("coletarDados() executa sem exceção (LIDAR)", () -> {
            new SensorLidar().coletarDados();
        });

        testar("coletarDados() executa sem exceção (CAMERA)", () -> {
            new SensorCamera().coletarDados();
        });

        subsecao("Ciclo de status");
        testar("atualizarStatus() altera para FALHA", () -> {
            SensorGPS gps = new SensorGPS();
            gps.atualizarStatus(StatusSensor.FALHA);
            assertEquals(StatusSensor.FALHA, gps.getStatus(), "Status deve ser FALHA");
        });

        testar("verificarStatus() aciona gerarAlerta() quando FALHA", () -> {
            SensorGPS gps = new SensorGPS();
            gps.atualizarStatus(StatusSensor.FALHA);
            gps.verificarStatus();
            assertTrue(gps.isAlertaGerado(), "Alerta deve ter sido gerado");
        });

        testar("verificarStatus() NÃO aciona alerta quando OPERACIONAL", () -> {
            SensorGPS gps = new SensorGPS();
            gps.verificarStatus();
            assertTrue(!gps.isAlertaGerado(), "Alerta não deve ser gerado quando operacional");
        });

        testar("atualizarStatus() para MANUTENCAO funciona", () -> {
            SensorGPS gps = new SensorGPS();
            gps.atualizarStatus(StatusSensor.MANUTENCAO);
            assertEquals(StatusSensor.MANUTENCAO, gps.getStatus(), "Status");
        });

        testarExcecao("atualizarStatus(null) lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new SensorGPS().atualizarStatus(null));

        testarExcecao("Sensor com tipo nulo lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Sensor(null) {
                    @Override public void coletarDados() {}
                });
    }

    // ════════════════════════════════════════════════════════
    //  4. DRONE
    // ════════════════════════════════════════════════════════
    static void testarDrone() {
        secao("4. DRONE");

        Coordenadas base = new Coordenadas(-23.5505, -46.6333, 0.0);

        subsecao("Criação");
        testar("Cria drone com status IDLE e bateria 100%", () -> {
            Drone d = new Drone("Falcon-X", base);
            assertEquals(StatusDrone.IDLE, d.getStatus(), "Status inicial");
            assertEquals(100.0, d.getBateria(), "Bateria inicial");
            assertEquals("Falcon-X", d.getModelo(), "Modelo");
            assertTrue(d.getId() != null, "ID não nulo");
        });

        testar("Construtor de reconstituição (BD) funciona", () -> {
            UUID id = UUID.randomUUID();
            Drone d = new Drone(id, "Falcon-Y", base, StatusDrone.EM_MISSAO, 75.0);
            assertEquals(id, d.getId(), "ID");
            assertEquals(StatusDrone.EM_MISSAO, d.getStatus(), "Status");
            assertEquals(75.0, d.getBateria(), "Bateria");
        });

        testarExcecao("Modelo vazio lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Drone("", base));

        testarExcecao("Localização nula lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Drone("Falcon", null));

        subsecao("Sensores");
        testar("adicionarSensor() adiciona corretamente", () -> {
            Drone d = new Drone("Falcon-X", base);
            d.adicionarSensor(new SensorGPS());
            d.adicionarSensor(new SensorLidar());
            assertEquals(2, d.getSensores().size(), "Deve ter 2 sensores");
        });

        testar("sincronizarSensores() chama coletarDados() e verificarStatus()", () -> {
            Drone d = new Drone("Falcon-X", base);
            d.adicionarSensor(new SensorGPS());
            d.adicionarSensor(new SensorCamera());
            d.sincronizarSensores(); // Não deve lançar exceção
        });

        testarExcecao("adicionarSensor(null) lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Drone("Falcon-X", base).adicionarSensor(null));

        subsecao("Movimentação");
        testar("moverPara() atualiza localização e consome bateria", () -> {
            Drone d = new Drone("Falcon-X", base);
            Coordenadas destino = new Coordenadas(-23.5600, -46.6400, 50.0);
            d.moverPara(destino, 5.0);
            assertTrue(d.getLocalizacao().isIgual(destino), "Localização deve ser o destino");
            assertEquals(95.0, d.getBateria(), "Bateria deve ser 95%");
        });

        testar("moverPara() com consumo alto (bateria cai ≤15%) entra em ALERTA", () -> {
            Drone d = new Drone("Falcon-X", base);
            Coordenadas destino = new Coordenadas(-23.56, -46.64, 50.0);
            d.moverPara(destino, 85.0); // bateria: 100 - 85 = 15 → ALERTA
            assertEquals(StatusDrone.ALERTA, d.getStatus(), "Status deve ser ALERTA");
        });

        testarExcecao("moverPara() quando (bateria - consumo) < 5% lança IllegalStateException",
                IllegalStateException.class,
                () -> {
                    Drone d = new Drone("Falcon-X", base);
                    // bateria=100, consumo=96 → (100-96)=4 < reserva(5%) → exceção
                    Coordenadas d1 = new Coordenadas(-23.56, -46.64, 0.0);
                    d.moverPara(d1, 96.0);
                });

        testarExcecao("moverPara() com destino nulo lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Drone("Falcon-X", base).moverPara(null, 5.0));
    }

    // ════════════════════════════════════════════════════════
    //  5. SISTEMA DE COMUNICAÇÃO
    // ════════════════════════════════════════════════════════
    static void testarSistemaComunicacao() {
        secao("5. SISTEMA DE COMUNICAÇÃO");

        subsecao("Envio seguro");
        testar("enviarComandoSeguro() retorna true com dados válidos", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT_LINK");
            boolean ok = sc.enviarComandoSeguro(UUID.randomUUID(), "DECOLAR", "assinatura-valida");
            assertTrue(ok, "Deve enviar com sucesso");
        });

        testar("enviarComandoSeguro() retorna false com assinatura nula", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT_LINK");
            boolean ok = sc.enviarComandoSeguro(UUID.randomUUID(), "DECOLAR", null);
            assertTrue(!ok, "Assinatura nula não deve enviar");
        });

        testar("enviarComandoSeguro() retorna false com assinatura vazia", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT_LINK");
            boolean ok = sc.enviarComandoSeguro(UUID.randomUUID(), "DECOLAR", "  ");
            assertTrue(!ok, "Assinatura vazia não deve enviar");
        });

        testar("enviarComandoSeguro() retorna false com droneId nulo", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT_LINK");
            boolean ok = sc.enviarComandoSeguro(null, "DECOLAR", "sig");
            assertTrue(!ok, "DroneId nulo não deve enviar");
        });

        subsecao("Telemetria e reconexão");
        testar("receberTelemetria() retorna true com conexão ativa", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT_LINK");
            Telemetria t = new Telemetria(UUID.randomUUID(), -23.55, -46.63, 100.0, 30.0, "EM_MISSAO", "GPS", Instant.now());
            assertTrue(sc.receberTelemetria(t), "Deve receber telemetria");
        });

        testar("receberTelemetria() retorna false com telemetria nula", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT_LINK");
            assertTrue(!sc.receberTelemetria(null), "Telemetria nula não deve retornar true");
        });

        testar("tentarReconexao() restaura conexão e redefine protocolo", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("PROTOCOLO_ANTIGO");
            sc.tentarReconexao();
            assertTrue(sc.isConexaoAtiva(), "Conexão deve estar ativa");
            assertEquals("SAT_ENCRYPT_LNK", sc.getProtocolo(), "Protocolo deve ser atualizado");
        });
    }

    // ════════════════════════════════════════════════════════
    //  6. NAVEGAÇÃO INTELIGENTE
    // ════════════════════════════════════════════════════════
    static void testarNavegacaoInteligente() {
        secao("6. NAVEGAÇÃO INTELIGENTE");

        NavegacaoInteligente nav = new NavegacaoInteligente();
        Coordenadas a = new Coordenadas(-23.5505, -46.6333, 0.0);
        Coordenadas b = new Coordenadas(-23.5600, -46.6400, 50.0);

        testar("calcularRota() retorna string não nula", () -> {
            String rota = nav.calcularRota(a, b);
            assertTrue(rota != null && !rota.isBlank(), "Rota não pode ser vazia");
        });

        testarExcecao("calcularRota() com coordenada nula lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> nav.calcularRota(null, b));

        testar("detectarAmeaca() retorna true com bateria < 15%", () -> {
            assertTrue(nav.detectarAmeaca(14.9), "Bateria 14.9 é ameaça");
            assertTrue(nav.detectarAmeaca(0.0), "Bateria 0 é ameaça");
        });

        testar("detectarAmeaca() retorna false com bateria >= 15%", () -> {
            assertTrue(!nav.detectarAmeaca(15.0), "Bateria 15 não é ameaça");
            assertTrue(!nav.detectarAmeaca(100.0), "Bateria 100 não é ameaça");
        });

        testar("desviarObstaculo() executa sem exceção com drone válido", () -> {
            Drone d = new Drone("Falcon-X", a);
            nav.desviarObstaculo(d);
        });

        testarExcecao("desviarObstaculo() com drone nulo lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> nav.desviarObstaculo(null));
    }

    // ════════════════════════════════════════════════════════
    //  7. TELEMETRIA
    // ════════════════════════════════════════════════════════
    static void testarTelemetria() {
        secao("7. TELEMETRIA");

        UUID droneId = UUID.randomUUID();

        testar("Cria Telemetria com todos os campos", () -> {
            Telemetria t = new Telemetria(droneId, -23.55, -46.63, 100.0, 50.0, "EM_MISSAO", "GPS", Instant.now());
            assertEquals(droneId, t.getDroneId(), "DroneId");
            assertEquals(-23.55, t.getLatitude(), "Latitude");
            assertEquals(50.0, t.getVelocidade(), "Velocidade");
            assertTrue(t.getId() != null, "ID não nulo");
            assertTrue(t.getTimestamp() != null, "Timestamp não nulo");
        });

        testar("Timestamp null usa Instant.now()", () -> {
            Telemetria t = new Telemetria(droneId, 0.0, 0.0, 0.0, 0.0, "IDLE", "GPS", null);
            assertTrue(t.getTimestamp() != null, "Timestamp não deve ser nulo");
        });

        testarExcecao("droneId nulo lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Telemetria(null, 0.0, 0.0, 0.0, 0.0, "IDLE", "GPS", null));

        testarExcecao("Velocidade negativa lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Telemetria(droneId, 0.0, 0.0, 0.0, -1.0, "IDLE", "GPS", null));

        testar("toString() formata corretamente", () -> {
            Telemetria t = new Telemetria(droneId, -23.55, -46.63, 100.0, 50.0, "EM_MISSAO", "GPS", Instant.now());
            assertTrue(t.toString().contains("Telemetria"), "toString deve conter 'Telemetria'");
        });
    }

    // ════════════════════════════════════════════════════════
    //  8. LOG DE AUDITORIA
    // ════════════════════════════════════════════════════════
    static void testarLogAuditoria() {
        secao("8. LOG DE AUDITORIA");

        UUID usuarioId = UUID.randomUUID();

        testar("Cria LogAuditoria com 3 parâmetros", () -> {
            LogAuditoria log = new LogAuditoria(usuarioId, enums.TipoAcao.LOGIN, "Usuário logado");
            assertEquals(enums.TipoAcao.LOGIN, log.getAcao(), "Ação");
            assertEquals("Usuário logado", log.getDetalhes(), "Detalhes");
            assertTrue(log.getMissaoId() == null, "MissaoId deve ser nulo");
            assertTrue(log.getDroneId() == null, "DroneId deve ser nulo");
        });

        testar("Cria LogAuditoria com 5 parâmetros", () -> {
            UUID missaoId = UUID.randomUUID();
            UUID droneId = UUID.randomUUID();
            LogAuditoria log = new LogAuditoria(usuarioId, enums.TipoAcao.ENVIO_COMANDO, "Cmd", missaoId, droneId);
            assertEquals(missaoId, log.getMissaoId(), "MissaoId");
            assertEquals(droneId, log.getDroneId(), "DroneId");
        });

        testar("Timestamp é preenchido automaticamente", () -> {
            LogAuditoria log = new LogAuditoria(usuarioId, enums.TipoAcao.LOGOUT, "Saiu");
            assertTrue(log.getTimestamp() != null, "Timestamp não nulo");
        });

        testarExcecao("usuarioId nulo lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new LogAuditoria(null, enums.TipoAcao.LOGIN, "test"));

        testarExcecao("acao nula lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new LogAuditoria(usuarioId, null, "test"));
    }

    // ════════════════════════════════════════════════════════
    //  9. MISSÃO
    // ════════════════════════════════════════════════════════
    static void testarMissao() {
        secao("9. MISSÃO");

        Coordenadas coord = new Coordenadas(-23.5505, -46.6333, 0.0);
        Operador cmdante = new Operador("Cmd", "cmd@f.mil", "senha", NivelAcesso.COMANDANTE);
        Operador soldado = new Operador("Sol", "sol@f.mil", "senha", NivelAcesso.SOLDADO);

        subsecao("Criação");
        testar("Cria Missao com status AGUARDANDO", () -> {
            Missao m = new Missao("Reconhecimento", coord, 5000.0);
            assertEquals(enums.StatusMissao.AGUARDANDO, m.getStatus(), "Status inicial");
            assertEquals("Reconhecimento", m.getObjetivo(), "Objetivo");
            assertTrue(m.getDronesIds().isEmpty(), "Sem drones inicialmente");
        });

        testarExcecao("Objetivo vazio lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Missao("", coord, 5000.0));

        testarExcecao("Coordenada nula lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Missao("Obj", null, 5000.0));

        testarExcecao("Raio <= 0 lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new Missao("Obj", coord, 0.0));

        subsecao("Alocação de drones");
        testar("alocarDrone() adiciona drone e cria log", () -> {
            Missao m = new Missao("Missão A", coord, 5000.0);
            UUID droneId = UUID.randomUUID();
            m.alocarDrone(droneId, cmdante);
            assertTrue(m.isDroneAlocado(droneId), "Drone deve estar alocado");
            assertTrue(!m.getLogs().isEmpty(), "Deve ter log");
        });

        testarExcecao("alocarDrone() por SOLDADO lança SecurityException",
                SecurityException.class,
                () -> {
                    Missao m = new Missao("Missão A", coord, 5000.0);
                    m.alocarDrone(UUID.randomUUID(), soldado);
                });

        testarExcecao("alocarDrone() duplicado lança IllegalStateException",
                IllegalStateException.class,
                () -> {
                    Missao m = new Missao("Missão A", coord, 5000.0);
                    UUID id = UUID.randomUUID();
                    m.alocarDrone(id, cmdante);
                    m.alocarDrone(id, cmdante);
                });

        subsecao("Ciclo de vida completo");
        testar("Fluxo AGUARDANDO → EM_CURSO → SUCESSO", () -> {
            Missao m = new Missao("Operação Alfa", coord, 5000.0);
            UUID droneId = UUID.randomUUID();
            m.alocarDrone(droneId, cmdante);
            m.definirOperadorResponsavel(cmdante.getId(), cmdante);
            m.iniciarMissao(cmdante);
            assertEquals(enums.StatusMissao.EM_CURSO, m.getStatus(), "Status EM_CURSO");
            assertTrue(m.getDataInicio() != null, "DataInicio preenchida");
            m.finalizarMissao(cmdante);
            assertEquals(enums.StatusMissao.SUCESSO, m.getStatus(), "Status SUCESSO");
            assertTrue(m.getDataFim() != null, "DataFim preenchida");
        });

        testar("Fluxo AGUARDANDO → EM_CURSO → ABORTADA", () -> {
            Missao m = new Missao("Operação Beta", coord, 5000.0);
            m.alocarDrone(UUID.randomUUID(), cmdante);
            m.definirOperadorResponsavel(cmdante.getId(), cmdante);
            m.iniciarMissao(cmdante);
            m.abortarMissao("Condições adversas", cmdante);
            assertEquals(enums.StatusMissao.ABORTADA, m.getStatus(), "Status ABORTADA");
            assertEquals("Condições adversas", m.getMotivoAborto(), "Motivo aborto");
        });

        testarExcecao("iniciarMissao() sem drones lança IllegalStateException",
                IllegalStateException.class,
                () -> {
                    Missao m = new Missao("Sem drones", coord, 5000.0);
                    m.definirOperadorResponsavel(cmdante.getId(), cmdante);
                    m.iniciarMissao(cmdante);
                });

        testarExcecao("iniciarMissao() sem operador responsável lança IllegalStateException",
                IllegalStateException.class,
                () -> {
                    Missao m = new Missao("Sem responsável", coord, 5000.0);
                    m.alocarDrone(UUID.randomUUID(), cmdante);
                    m.iniciarMissao(cmdante);
                });

        testarExcecao("finalizarMissao() em missão AGUARDANDO lança IllegalStateException",
                IllegalStateException.class,
                () -> new Missao("M", coord, 100.0).finalizarMissao(cmdante));

        testarExcecao("abortarMissao() com motivo vazio lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> {
                    Missao m = new Missao("M", coord, 100.0);
                    m.alocarDrone(UUID.randomUUID(), cmdante);
                    m.definirOperadorResponsavel(cmdante.getId(), cmdante);
                    m.iniciarMissao(cmdante);
                    m.abortarMissao("", cmdante);
                });

        testarExcecao("definirOperadorResponsavel() duplicado lança IllegalStateException",
                IllegalStateException.class,
                () -> {
                    Missao m = new Missao("M", coord, 100.0);
                    m.definirOperadorResponsavel(cmdante.getId(), cmdante);
                    m.definirOperadorResponsavel(cmdante.getId(), cmdante);
                });
    }

    // ════════════════════════════════════════════════════════
    //  10. CENTRAL DE CONTROLE
    // ════════════════════════════════════════════════════════
    static void testarCentralDeControle() {
        secao("10. CENTRAL DE CONTROLE");

        Coordenadas base = new Coordenadas(-23.5505, -46.6333, 0.0);
        Operador cmdante = new Operador("Cmd", "cmd@f.mil", "senha", NivelAcesso.COMANDANTE);
        Operador soldado = new Operador("Sol", "sol@f.mil", "senha", NivelAcesso.SOLDADO);

        subsecao("Frota e missões");
        testar("adicionarDroneAFrota() adiciona corretamente", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            Drone d = new Drone("Falcon-X", base);
            cdc.adicionarDroneAFrota(d);
            assertEquals(1, cdc.getFrota().size(), "Frota deve ter 1 drone");
        });

        testarExcecao("adicionarDroneAFrota(null) lança IllegalArgumentException",
                IllegalArgumentException.class,
                () -> new CentralDeControle(new SistemaComunicacao("SAT")).adicionarDroneAFrota(null));

        testar("adicionarMissao() adiciona corretamente", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            Missao m = new Missao("Op", base, 1000.0);
            cdc.adicionarMissao(m);
            assertEquals(1, cdc.getMissoes().size(), "Deve ter 1 missão");
        });

        subsecao("Envio de comandos");
        testar("enviarComando() por COMANDANTE cria log ENVIO_COMANDO", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            cdc.enviarComando(cmdante, UUID.randomUUID(), "DECOLAR", "sig123");
            assertEquals(1, cdc.getLogs().size(), "Deve ter 1 log");
            assertEquals(enums.TipoAcao.ENVIO_COMANDO, cdc.getLogs().get(0).getAcao(), "Ação do log");
        });

        testarExcecao("enviarComando() por SOLDADO lança IllegalStateException",
                IllegalStateException.class,
                () -> {
                    SistemaComunicacao sc = new SistemaComunicacao("SAT");
                    CentralDeControle cdc = new CentralDeControle(sc);
                    cdc.enviarComando(soldado, UUID.randomUUID(), "DECOLAR", "sig123");
                });

        testar("enviarComando() por SOLDADO cria log FALHA_SEGURANCA", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            try {
                cdc.enviarComando(soldado, UUID.randomUUID(), "DECOLAR", "sig123");
            } catch (IllegalStateException ignored) {}
            assertEquals(1, cdc.getLogs().size(), "Deve ter 1 log de falha");
            assertEquals(enums.TipoAcao.FALHA_SEGURANCA, cdc.getLogs().get(0).getAcao(), "Ação do log");
        });

        subsecao("Telemetria");
        testar("receberTelemetria() cria log ALTERACAO_STATUS", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            Drone d = new Drone("Falcon-X", base);
            Telemetria t = new Telemetria(d.getId(), -23.55, -46.63, 100.0, 30.0, "EM_MISSAO", "GPS", Instant.now());
            cdc.receberTelemetria(d, t);
            assertEquals(1, cdc.getLogs().size(), "Deve ter 1 log");
            assertEquals(enums.TipoAcao.ALTERACAO_STATUS, cdc.getLogs().get(0).getAcao(), "Ação");
        });

        testar("receberTelemetria() com drone nulo não lança exceção", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            cdc.receberTelemetria(null, null); // Deve ignorar silenciosamente
        });

        subsecao("Sincronização de logs");
        testar("sincronizarLogsMissao() replica logs sem duplicatas", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            Missao m = new Missao("Op", base, 1000.0);
            Operador op = new Operador("Cmd", "c@f.mil", "s", NivelAcesso.COMANDANTE);
            m.alocarDrone(UUID.randomUUID(), op);
            m.definirOperadorResponsavel(op.getId(), op);
            m.iniciarMissao(op);
            m.finalizarMissao(op);
            cdc.sincronizarLogsMissao(m);
            assertTrue(cdc.getLogs().size() > 0, "Logs sincronizados");
            int tamanho = cdc.getLogs().size();
            cdc.sincronizarLogsMissao(m); // Segunda vez — não deve duplicar
            assertEquals(tamanho, cdc.getLogs().size(), "Não deve duplicar logs");
        });
    }

    // ════════════════════════════════════════════════════════
    //  11. FLUXO COMPLETO DE OPERAÇÃO
    // ════════════════════════════════════════════════════════
    static void testarFluxoCompleto() {
        secao("11. FLUXO COMPLETO DE OPERAÇÃO");

        System.out.println("\n  " + AZUL + "Simulando cenário real de missão tática..." + RESET);

        testar("Fluxo completo: autenticação → missão → operação → telemetria → finalização", () -> {

            // 1. Setup do sistema
            SistemaComunicacao sc = new SistemaComunicacao("SAT_ENCRYPT_LNK");
            CentralDeControle central = new CentralDeControle(sc);
            NavegacaoInteligente nav = new NavegacaoInteligente();
            System.out.println("\n    [1] Sistema inicializado");

            // 2. Criar operadores
            Operador comandante = new Operador("Capitão Torres", "torres@falcao.mil", "senha_secreta", NivelAcesso.COMANDANTE);
            Operador soldado    = new Operador("Cabo Lima", "lima@falcao.mil", "senha456", NivelAcesso.SOLDADO);
            System.out.println("    [2] Operadores criados");

            // 3. Autenticar
            assertTrue(comandante.validarAcesso("senha_secreta", "mfa-token-123"), "Autenticação do comandante");
            assertTrue(!soldado.validarAcesso("errada", "token"), "Falha na autenticação do soldado");
            System.out.println("    [3] Autenticação validada");

            // 4. Criar e adicionar drones à frota
            Coordenadas base = new Coordenadas(-23.5505, -46.6333, 0.0);
            Drone drone1 = new Drone("Falcon-Alpha", base);
            Drone drone2 = new Drone("Falcon-Beta",  base);
            drone1.adicionarSensor(new SensorGPS());
            drone1.adicionarSensor(new SensorLidar());
            drone2.adicionarSensor(new SensorCamera());
            central.adicionarDroneAFrota(drone1);
            central.adicionarDroneAFrota(drone2);
            assertEquals(2, central.getFrota().size(), "Frota com 2 drones");
            System.out.println("    [4] Frota configurada com 2 drones e sensores");

            // 5. Criar missão
            Coordenadas pontoMissao = new Coordenadas(-23.5600, -46.6400, 50.0);
            Missao missao = new Missao("Reconhecimento Noturno", pontoMissao, 3000.0);
            central.adicionarMissao(missao);
            assertEquals(enums.StatusMissao.AGUARDANDO, missao.getStatus(), "Status inicial da missão");
            System.out.println("    [5] Missão criada — Status: AGUARDANDO");

            // 6. Configurar missão
            missao.alocarDrone(drone1.getId(), comandante);
            missao.alocarDrone(drone2.getId(), comandante);
            missao.definirOperadorResponsavel(comandante.getId(), comandante);
            assertTrue(missao.isDroneAlocado(drone1.getId()), "Drone1 alocado");
            assertTrue(missao.isDroneAlocado(drone2.getId()), "Drone2 alocado");
            System.out.println("    [6] 2 drones alocados, operador responsável definido");

            // 7. Enviar comando de decolar
            central.enviarComando(comandante, drone1.getId(), "DECOLAR", "sig-falcon-alpha");
            System.out.println("    [7] Comando DECOLAR enviado para Falcon-Alpha");

            // 8. Iniciar missão
            missao.iniciarMissao(comandante);
            assertEquals(enums.StatusMissao.EM_CURSO, missao.getStatus(), "Status EM_CURSO");
            System.out.println("    [8] Missão iniciada — Status: EM_CURSO");

            // 9. Operação dos drones
            Coordenadas destino = new Coordenadas(-23.5580, -46.6380, 50.0);
            String rota = nav.calcularRota(base, destino);
            assertTrue(rota != null, "Rota calculada");
            assertTrue(!nav.detectarAmeaca(drone1.getBateria()), "Sem ameaça — bateria cheia");

            drone1.moverPara(destino, 10.0);
            assertTrue(drone1.getLocalizacao().isIgual(destino), "Drone1 movido");
            assertEquals(90.0, drone1.getBateria(), "Bateria drone1");

            drone1.sincronizarSensores();
            System.out.println("    [9] Drone movido e sensores sincronizados");

            // 10. Telemetria
            Telemetria tel = new Telemetria(
                    drone1.getId(),
                    destino.getLatitude(), destino.getLongitude(), destino.getAltitude(),
                    45.0, drone1.getStatus().name(), "GPS", Instant.now()
            );
            central.receberTelemetria(drone1, tel);
            System.out.println("    [10] Telemetria recebida pela Central");

            // 11. Desviar obstáculo
            nav.desviarObstaculo(drone1);
            System.out.println("    [11] Desvio de obstáculo calculado");

            // 12. Finalizar missão
            missao.finalizarMissao(comandante);
            assertEquals(enums.StatusMissao.SUCESSO, missao.getStatus(), "Status SUCESSO");
            System.out.println("    [12] Missão finalizada — Status: SUCESSO");

            // 13. Sincronizar logs
            central.sincronizarLogsMissao(missao);
            assertTrue(central.getLogs().size() > 0, "Logs sincronizados na central");
            System.out.println("    [13] Logs sincronizados — total: " + central.getLogs().size());
        });

        testar("Fluxo de aborto de missão", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle central = new CentralDeControle(sc);
            Operador op = new Operador("Cmd", "c@f.mil", "s", NivelAcesso.COMANDANTE);
            Coordenadas coord = new Coordenadas(-23.55, -46.63, 0.0);
            Missao m = new Missao("Missão Abortada", coord, 1000.0);
            m.alocarDrone(UUID.randomUUID(), op);
            m.definirOperadorResponsavel(op.getId(), op);
            m.iniciarMissao(op);
            m.abortarMissao("Mudança climática adversa", op);
            assertEquals(enums.StatusMissao.ABORTADA, m.getStatus(), "Status ABORTADA");
            assertEquals("Mudança climática adversa", m.getMotivoAborto(), "Motivo correto");
        });
    }

    // ════════════════════════════════════════════════════════
    //  12. CASOS LIMITE
    // ════════════════════════════════════════════════════════
    static void testarCasosLimite() {
        secao("12. CASOS LIMITE E SEGURANÇA");

        Coordenadas base = new Coordenadas(-23.5505, -46.6333, 0.0);
        Operador cmdante = new Operador("Cmd", "c@f.mil", "s", NivelAcesso.COMANDANTE);

        subsecao("Fronteiras de bateria");
        testar("Bateria exatamente 16% permite movimento com consumo pequeno", () -> {
            Drone d = new Drone("F", base);
            d.moverPara(new Coordenadas(-23.56, -46.64, 0.0), 84.0); // 16% restante
            Coordenadas dest2 = new Coordenadas(-23.57, -46.65, 0.0);
            d.moverPara(dest2, 1.0); // 16 - 1 = 15 → ALERTA após consumo, mas move
            assertEquals(StatusDrone.ALERTA, d.getStatus(), "Deve entrar em ALERTA após consumo");
        });

        subsecao("Coordenadas limítrofes válidas");
        testar("Coordenadas nos extremos válidos (±90 lat, ±180 lon)", () -> {
            new Coordenadas(90.0, 180.0, 0.0);
            new Coordenadas(-90.0, -180.0, 0.0);
            new Coordenadas(0.0, 0.0, 0.0);
        });

        subsecao("Segurança — acesso negado gera log");
        testar("SOLDADO tentando enviarComando gera log FALHA_SEGURANCA na Central", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            Operador soldado = new Operador("Sol", "s@f.mil", "s", NivelAcesso.SOLDADO);
            try { cdc.enviarComando(soldado, UUID.randomUUID(), "CMD", "sig"); }
            catch (Exception ignored) {}
            assertTrue(cdc.getLogs().stream()
                    .anyMatch(l -> l.getAcao() == enums.TipoAcao.FALHA_SEGURANCA),
                    "Log FALHA_SEGURANCA deve existir");
        });

        testar("SOLDADO tentando alocarDrone gera log FALHA_SEGURANCA na Missao", () -> {
            Missao m = new Missao("M", base, 100.0);
            Operador soldado = new Operador("Sol", "s@f.mil", "s", NivelAcesso.SOLDADO);
            try { m.alocarDrone(UUID.randomUUID(), soldado); }
            catch (Exception ignored) {}
            assertTrue(m.getLogs().stream()
                    .anyMatch(l -> l.getAcao() == enums.TipoAcao.FALHA_SEGURANCA),
                    "Log FALHA_SEGURANCA deve existir na missão");
        });

        subsecao("Thread-safety");
        testar("Múltiplas threads alocando drones distintos na mesma Missao", () -> {
            Missao m = new Missao("Multi-thread", base, 1000.0);
            Thread[] threads = new Thread[5];
            for (int i = 0; i < 5; i++) {
                final UUID id = UUID.randomUUID();
                threads[i] = new Thread(() -> m.alocarDrone(id, cmdante));
                threads[i].start();
            }
            for (Thread t : threads) {
                try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            assertEquals(5, m.getDronesIds().size(), "5 drones distintos alocados sem race condition");
        });

        subsecao("Integridade de coleções");
        testar("getFrota() retorna cópia imutável — modificação externa não afeta central", () -> {
            SistemaComunicacao sc = new SistemaComunicacao("SAT");
            CentralDeControle cdc = new CentralDeControle(sc);
            cdc.adicionarDroneAFrota(new Drone("F", base));
            try {
                cdc.getFrota().add(new Drone("G", base));
            } catch (UnsupportedOperationException ignored) {}
            assertEquals(1, cdc.getFrota().size(), "Frota interna não deve ser modificada externamente");
        });
    }

    // ════════════════════════════════════════════════════════
    //  RELATÓRIO FINAL
    // ════════════════════════════════════════════════════════
    static void relatorioFinal() {
        System.out.println("\n\n" + ROXO + NEGRITO);
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                  RELATÓRIO FINAL                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════╣");
        System.out.printf ("║  Total de testes  : %-33d ║%n", totalTestes);
        System.out.printf ("║  " + VERDE + "✔ Passaram       : %-33d" + ROXO + " ║%n", testesPassaram);
        System.out.printf ("║  " + (testesFalharam > 0 ? VERMELHO : VERDE) +
                           "✘ Falharam        : %-33d" + ROXO + " ║%n", testesFalharam);
        System.out.println("╠═══════════════════════════════════════════════════════╣");

        double pct = totalTestes > 0 ? (testesPassaram * 100.0 / totalTestes) : 0;
        String resultado = testesFalharam == 0
                ? VERDE + "✔ TODOS OS TESTES PASSARAM"
                : VERMELHO + "✘ ALGUNS TESTES FALHARAM";
        System.out.printf ("║  %s  (%.1f%%)%-8s" + ROXO + " ║%n",
                resultado, pct, RESET + ROXO);
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }
}