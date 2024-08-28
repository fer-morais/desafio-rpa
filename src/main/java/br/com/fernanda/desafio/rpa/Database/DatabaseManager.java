package br.com.fernanda.desafio.rpa.Database;
import br.com.fernanda.desafio.rpa.Automation.Feriado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "fer67na");
    }

    public void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS feriados (" +
                "estado VARCHAR(50), " +
                "cidade VARCHAR(50), " +
                "data DATE, " +
                "tipo VARCHAR(50), " +
                "feriado VARCHAR(100))";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(createTableSQL)) {
            stmt.executeUpdate();
        }
    }

    public void insertFeriados(List<Feriado> feriados) throws SQLException {
        String sql = "INSERT INTO feriados (estado, cidade, data, tipo, feriado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Feriado f : feriados) {
                pstmt.setString(1, f.getEstado());
                pstmt.setString(2, f.getCidade());
                pstmt.setDate(3, java.sql.Date.valueOf(f.getData()));
                pstmt.setString(4, f.getTipo());
                pstmt.setString(5, f.getFeriado());
                pstmt.addBatch();

                System.out.println("Inserindo no banco de dados: " + f);
            }
            pstmt.executeBatch();
        }
    }
}
