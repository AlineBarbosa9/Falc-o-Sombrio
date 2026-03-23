package model;

public class NavegacaoInteligente {

    public String calcularRota(Coordenadas atual, Coordenadas destino) {

        if (atual == null || destino == null) {
            throw new IllegalArgumentException("Coordenadas inválidas");
        }

        return "Rota calculada";
    }

    public boolean detectarAmeaca(double bateria) {
        return bateria < 15.0;
    }

    public void desviarObstaculo(Drone drone) {

        if (drone == null || drone.getLocalizacao() == null) {
            throw new IllegalArgumentException("Drone inválido");
        }

    }
}
