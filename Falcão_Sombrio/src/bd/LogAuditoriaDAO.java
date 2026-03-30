package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import model.LogAuditoria;

public class LogAuditoriaDAO {

    public void salvar(LogAuditoria log) {

        String sql = "INSERT INTO logs_auditoria " +
                     "(id, usuarioId, acao, detalhes, droneId, missaoId, timestamp) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, log.getId());
            stmt.setObject(2, log.getUsuarioId());
            stmt.setString(3, log.getAcao().name());
            stmt.setString(4, log.getDetalhes());
            stmt.setObject(5, log.getDroneId());
            stmt.setObject(6, log.getMissaoId());
            stmt.setTimestamp(7, Timestamp.from(log.getTimestamp()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar log", e);
        }
    }
}