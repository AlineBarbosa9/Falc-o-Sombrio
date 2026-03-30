package bd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import model.Coordenadas;
import model.Missao;

public class MissaoDAO {

    public void salvar(Missao missao) {

        String sql = "INSERT INTO missoes (id, objetivo, status, pontoEntrada_lat, pontoEntrada_lon, pontoEntrada_alt, raioOperacao, dataInicio, dataFim, motivoAborto, operadorResponsavelId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, missao.getId());
            stmt.setString(2, missao.getObjetivo());
            stmt.setString(3, missao.getStatus().name());
            stmt.setDouble(4, missao.getPontoEntrada().getLatitude());
            stmt.setDouble(5, missao.getPontoEntrada().getLongitude());
            stmt.setDouble(6, missao.getPontoEntrada().getAltitude());
            stmt.setDouble(7, missao.getRaioOperacao());
            stmt.setTimestamp(8, missao.getDataInicio() != null ? Timestamp.from(missao.getDataInicio()) : null);
            stmt.setTimestamp(9, missao.getDataFim() != null ? Timestamp.from(missao.getDataFim()) : null);
            stmt.setString(10, missao.getMotivoAborto());
            stmt.setObject(11, missao.getOperadorResponsavel());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar missão", e);
        }
    }

    public Missao buscarPorId(UUID id) {

        String sql = "SELECT * FROM missoes WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Coordenadas ponto = new Coordenadas(rs.getDouble("pontoEntrada_lat"),
                    		rs.getDouble("pontoEntrada_lon"),
                            rs.getDouble("pontoEntrada_alt"));

                    Missao missao = new Missao(
                            rs.getString("objetivo"),ponto,rs.getDouble("raioOperacao"));
                    return missao;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar missão", e);
        }

        return null;
    }
}