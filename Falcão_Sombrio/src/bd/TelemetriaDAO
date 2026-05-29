package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import model.Telemetria;

public class TelemetriaDAO {

    public void salvar(Telemetria telemetria) {

        if (telemetria == null) {
            throw new IllegalArgumentException("Telemetria inválida");
        }

        String sql = "INSERT INTO telemetria (id, droneId, missaoId, latitude, longitude, altitude, velocidade, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, telemetria.getId());
            stmt.setObject(2, telemetria.getDroneId());
            stmt.setObject(3, telemetria.getMissaoId());
            stmt.setDouble(4, telemetria.getLatitude());
            stmt.setDouble(5, telemetria.getLongitude());
            stmt.setDouble(6, telemetria.getAltitude());
            stmt.setDouble(7, telemetria.getVelocidade());
            stmt.setTimestamp(8, Timestamp.from(telemetria.getTimestamp()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar telemetria", e);
        }
    }

    public Telemetria buscarPorId(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID inválido");
        }

        String sql = "SELECT * FROM telemetria WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return new Telemetria(
                            (UUID) rs.getObject("droneId"),
                            (UUID) rs.getObject("missaoId"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude"),
                            rs.getDouble("altitude"),
                            rs.getDouble("velocidade"),
                            rs.getTimestamp("timestamp").toInstant()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar telemetria", e);
        }

        return null;
    }
}
