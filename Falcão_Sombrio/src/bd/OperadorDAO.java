package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import enums.NivelAcesso;
import model.Operador;

public class OperadorDAO {

    public void salvar(Operador operador) {

        String sql = "INSERT INTO operadores (id, nome, email, senhaHash, nivelAcesso, mfaSecret) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, operador.getId());
            stmt.setString(2, operador.getNome());
            stmt.setString(3, operador.getEmail());
            stmt.setString(4, operador.getSenhaHash());
            stmt.setString(5, operador.getNivelAcesso().name());
            stmt.setString(6, operador.getMfaSecret());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar operador", e);
        }
    }

    public Operador buscarPorId(UUID id) {

        String sql = "SELECT * FROM operadores WHERE id = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return new Operador(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senhaHash"),
                            NivelAcesso.valueOf(rs.getString("nivelAcesso"))
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar operador", e);
        }

        return null;
    }
}
