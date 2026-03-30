package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import enums.StatusDrone;
import model.Coordenadas;
import model.Drone;

public class DroneDAO {

    public void salvar(Drone drone) {

        if (drone == null) {
            throw new IllegalArgumentException("Drone inválido");
        }

        if (drone.getLocalizacao() == null) {
            throw new IllegalArgumentException("Drone sem localização");
        }

        String sql = "INSERT INTO drones (id, modelo, status, bateria, latitude, longitude, altitude) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, drone.getId());
            stmt.setString(2, drone.getModelo());
            stmt.setString(3, drone.getStatus().name());
            stmt.setDouble(4, drone.getBateria());
            stmt.setDouble(5, drone.getLocalizacao().getLatitude());
            stmt.setDouble(6, drone.getLocalizacao().getLongitude());
            stmt.setDouble(7, drone.getLocalizacao().getAltitude());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar drone", e);
        }
    }

    public Drone buscarPorId(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID inválido");
        }

        String sql = "SELECT * FROM drones WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Coordenadas coord = new Coordenadas(
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude"),
                            rs.getDouble("altitude")
                    );

                    return new Drone(
                            (UUID) rs.getObject("id"),
                            rs.getString("modelo"),
                            coord,
                            StatusDrone.valueOf(rs.getString("status")),
                            rs.getDouble("bateria")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar drone", e);
        }

        return null;
    }
}
