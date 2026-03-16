package model;

import enums.StatusDrone;

public class NavegacaoInteligente {

    public void calcularRota(Coordenadas atual, Coordenadas destino) {
        System.out.println("[IA] Rota calculada: (" + atual.getLatitude() + ") -> (" + destino.getLatitude() + ")");
    }

    public void detectarAmeaca(Drone drone) {
        if (drone.getBateria() < 15.0) {
            System.out.println("[IA-ALERTA] Bateria baixa!");
            drone.setStatus(StatusDrone.ALERTA);
        } else {
            System.out.println("[IA] Sensores normais.");
        }
    }

    public void desviarObstaculo(Drone drone) {
        System.out.println("[IA] Desviando de obstáculo...");
        double altitudeAtual = drone.getLocalizacao().getAltitude();
        drone.getLocalizacao().setAltitude(altitudeAtual + 10);
    }
}
