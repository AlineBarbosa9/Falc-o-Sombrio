import bd.DroneDAO;
import bd.LogAuditoriaDAO;
import bd.MissaoDAO;
import bd.OperadorDAO;
import bd.TelemetriaDAO;
import enums.NivelAcesso;
import enums.TipoAcao;
import model.*;

import java.time.Instant;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== INICIANDO TESTE DO SISTEMA FALCÃO SOMBRIO ===\n");

        // --- 1. Criar e salvar Operador ---
        System.out.println(">> Criando operador...");
        Operador operador = new Operador(
                "Sofia Castelli",
                "sofia@securus.com",
                "hash123",
                NivelAcesso.COMANDANTE
        );

        OperadorDAO operadorDAO = new OperadorDAO();
        operadorDAO.salvar(operador);
        System.out.println("Operador salvo: " + operador.getNome());

        // Buscar operador
        Operador operadorBuscado = operadorDAO.buscarPorId(operador.getId());
        System.out.println("Operador buscado: " + (operadorBuscado != null ? operadorBuscado.getNome() : "NULO"));

        // --- 2. Criar e salvar Missão ---
        System.out.println("\n>> Criando missão...");
        Coordenadas pontoEntrada = new Coordenadas(-23.550520, -46.633308, 100.0);
        Missao missao = new Missao("Reconhecimento de área hostil", pontoEntrada, 500.0);

        MissaoDAO missaoDAO = new MissaoDAO();
        missaoDAO.salvar(missao);
        System.out.println("Missão salva: " + missao.getObjetivo());

        // Buscar missão
        Missao missaoBuscada = missaoDAO.buscarPorId(missao.getId());
        System.out.println("Missão buscada: " + (missaoBuscada != null ? missaoBuscada.getObjetivo() : "NULO"));

        // --- 3. Criar e salvar Drone ---
        System.out.println("\n>> Criando drone...");
        Coordenadas localizacaoInicial = new Coordenadas(-23.550520, -46.633308, 0.0);
        Drone drone = new Drone("Aquila-X", localizacaoInicial);
        drone.setMissaoId(missao.getId());

        DroneDAO droneDAO = new DroneDAO();
        droneDAO.salvar(drone);
        System.out.println("Drone salvo: " + drone.getModelo());

        // Buscar drone
        Drone droneBuscado = droneDAO.buscarPorId(drone.getId());
        System.out.println("Drone buscado: " + (droneBuscado != null ? droneBuscado.getModelo() : "NULO"));

        // --- 4. Alocar drone e iniciar missão ---
        System.out.println("\n>> Alocando drone e iniciando missão...");
        missao.alocarDrone(drone.getId());
        missao.definirOperadorResponsavel(operador.getId());
        missao.iniciarMissao();
        missaoDAO.salvar(missao); // atualiza status
        System.out.println("Missão iniciada. Status: " + missao.getStatus());

        // --- 5. Criar e salvar Telemetria ---
        System.out.println("\n>> Registrando telemetria...");
        Telemetria telemetria = new Telemetria(
                drone.getId(),
                missao.getId(),
                -23.550520,
                -46.633308,
                150.0,
                45.0,
                Instant.now()
        );

        TelemetriaDAO telemetriaDAO = new TelemetriaDAO();
        telemetriaDAO.salvar(telemetria);
        System.out.println("Telemetria salva: " + telemetria);

        // Buscar telemetria
        Telemetria telemetriaBuscada = telemetriaDAO.buscarPorId(telemetria.getId());
        System.out.println("Telemetria buscada: " + (telemetriaBuscada != null ? telemetriaBuscada.toString() : "NULO"));

        // --- 6. Registrar Log de Auditoria ---
        System.out.println("\n>> Registrando log de auditoria...");
        LogAuditoria log = new LogAuditoria(
                operador.getId(),
                TipoAcao.MISSAO_INICIADA,
                "Missão iniciada pelo operador",
                missao.getId(),
                drone.getId()
        );

        LogAuditoriaDAO logDAO = new LogAuditoriaDAO();
        logDAO.salvar(log);
        System.out.println("Log salvo: " + log.getAcao());

        // --- 7. Finalizar missão ---
        System.out.println("\n>> Finalizando missão...");
        missao.finalizarMissao();
        missaoDAO.salvar(missao);
        System.out.println("Missão finalizada. Status: " + missao.getStatus());

        // --- 8. Testar autenticação ---
        System.out.println("\n>> Testando autenticação...");
        boolean acessoValido = operador.validarAcesso("hash123", "tokenMFA123");
        boolean acessoInvalido = operador.validarAcesso("senhaErrada", null);
        System.out.println("Acesso válido: " + acessoValido);
        System.out.println("Acesso inválido bloqueado: " + !acessoInvalido);

        // --- 9. Testar SistemaComunicacao ---
        System.out.println("\n>> Testando comunicação...");
        SistemaComunicacao sc = new SistemaComunicacao("SAT_ENCRYPT_LNK");
        boolean comandoEnviado = sc.enviarComandoSeguro(drone.getId(), "START", "assinatura_valida");
        System.out.println("Comando enviado: " + comandoEnviado);

        // --- 10. Testar NavegacaoInteligente ---
        System.out.println("\n>> Testando navegação...");
        NavegacaoInteligente nav = new NavegacaoInteligente();
        Coordenadas destino = new Coordenadas(-23.560000, -46.640000, 200.0);
        String rota = nav.calcularRota(localizacaoInicial, destino);
        boolean ameaca = nav.detectarAmeaca(drone.getBateria());
        System.out.println("Rota calculada: " + rota);
        System.out.println("Ameaça detectada (bateria " + drone.getBateria() + "%): " + ameaca);

        System.out.println("\n=== TESTE CONCLUÍDO COM SUCESSO ===");
    }
}
