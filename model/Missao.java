package model;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import enums.StatusMissao;

public class Missao {

    // Atributos Privados
    private final UUID id;
    private final String objetivo;
    private StatusMissao status;
    private final Set<UUID> dronesIds;
    private final Coordenadas pontoEntrada;
    private final double raioOperacao;
    private Instant dataInicio;
    private Instant dataFim;
    private String motivoAborto;
    private UUID operadorResponsavelId;

    // Construtor Público
    public Missao(String objetivo, Coordenadas pontoEntrada, double raioOperacao) {

        if (objetivo == null || objetivo.isBlank()) {
            throw new IllegalArgumentException("Objetivo inválido");
        }

        if (pontoEntrada == null) {
            throw new IllegalArgumentException("Coordenadas obrigatórias");
        }

        if (raioOperacao <= 0) {
            throw new IllegalArgumentException("Raio de operação inválido");
        }

        this.id = UUID.randomUUID();
        this.objetivo = objetivo;
        this.pontoEntrada = pontoEntrada;
        this.raioOperacao = raioOperacao;

        this.status = StatusMissao.AGUARDANDO;
        this.dronesIds = ConcurrentHashMap.newKeySet();
    }

    // Retorna se Um Drone Já Foi Alocado na Missão
    public boolean isDroneAlocado(UUID droneId) {
        return droneId != null && dronesIds.contains(droneId);
    }
    
    // Aloca um Drone na Missão
    public synchronized void alocarDrone(UUID droneId) {

        if (droneId == null) {
            throw new IllegalArgumentException("Drone ID inválido");
        }

        if (status != StatusMissao.AGUARDANDO) {
            throw new IllegalStateException("Não é possível alocar drones após início da missão");
        }

        boolean adicionado = dronesIds.add(droneId);

        if (!adicionado) {
            throw new IllegalStateException("Drone já alocado na missão");
        }
    }
    
    // Inicia a Missão Após Verificação de Status Atual, Drones e Operador
    public synchronized void iniciarMissao() {

        if (status != StatusMissao.AGUARDANDO) {
            throw new IllegalStateException("Missão já iniciada ou finalizada");
        }

        if (dronesIds.isEmpty()) {
            throw new IllegalStateException("Missão sem drones alocados");
        }

        if (operadorResponsavelId == null) {
            throw new IllegalStateException("Missão sem operador responsável");
        }

        this.status = StatusMissao.EM_CURSO;
        this.dataInicio = Instant.now();
    }
    
    // Aborta a Missão Apenas se Estiver Em Curso
    public synchronized void abortarMissao(String motivo) {

        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do aborto obrigatório");
        }

        if (status != StatusMissao.EM_CURSO) {
            throw new IllegalStateException("Só é possível abortar missão em andamento");
        }

        this.status = StatusMissao.ABORTADA;
        this.dataFim = Instant.now();
        this.motivoAborto = motivo;
    }
    
    // Finaliza a Missão Apenas se Estiver Em Curso
    public synchronized void finalizarMissao() {

        if (status != StatusMissao.EM_CURSO) {
            throw new IllegalStateException("Só é possível finalizar missão em andamento");
        }

        this.status = StatusMissao.SUCESSO;
        this.dataFim = Instant.now();
    }

    // Definição de Operador da Missão
    public void definirOperadorResponsavel(UUID operadorId) {
        if (operadorId == null) {
            throw new IllegalArgumentException("Operador inválido");
        }

        if (this.operadorResponsavelId != null) {
            throw new IllegalStateException("Operador já definido");
        }

        this.operadorResponsavelId = operadorId;
    }

    // Getters
    public UUID getId() {
        return id;
    }
    public String getObjetivo() {
        return objetivo;
    }
    public StatusMissao getStatus() {
        return status;
    }
    public Set<UUID> getDronesIds() {
        return Collections.unmodifiableSet(dronesIds);
    }
    public Coordenadas getPontoEntrada() {
        return pontoEntrada;
    }
    public double getRaioOperacao() {
        return raioOperacao;
    }
    public Instant getDataInicio() {
        return dataInicio;
    }
    public Instant getDataFim() {
        return dataFim;
    }
    public String getMotivoAborto() {
        return motivoAborto;
    }
    public UUID getOperadorResponsavel() {
        return operadorResponsavelId;
    }

    @Override
    public String toString() {
        return "Missao{" +
                "id=" + id +
                ", objetivo='" + objetivo + '\'' +
                ", status=" + status +
                ", drones=" + dronesIds.size() +
                '}';
    }
}
