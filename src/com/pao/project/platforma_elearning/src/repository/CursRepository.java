package com.pao.project.platforma_elearning.src.repository;

import com.pao.project.platforma_elearning.src.model.Curs;
import com.pao.project.platforma_elearning.src.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursRepository implements Repository<Curs, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Curs entity) {
        String sql = "INSERT INTO Curs (titlu, categorie, pret, id_instructor) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getTitlu());
            pstmt.setString(2, entity.getCategorie());
            pstmt.setDouble(3, entity.getPret());
            pstmt.setInt(4, entity.getIdInstructor());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la salvarea cursului: " + e.getMessage());
        }
    }

    @Override
    public Optional<Curs> findById(Integer id) {
        String sql = "SELECT * FROM Curs WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCurs(rs));
                }
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la cautarea cursului: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Curs> findAll() {
        List<Curs> cursuri = new ArrayList<>();
        String sql = "SELECT * FROM Curs";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cursuri.add(mapRowToCurs(rs));
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la preluarea cursurilor: " + e.getMessage());
        }

        return cursuri;
    }

    @Override
    public void update(Curs entity) {
        String sql = "UPDATE Curs SET titlu = ?, categorie = ?, pret = ?, id_instructor = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getTitlu());
            pstmt.setString(2, entity.getCategorie());
            pstmt.setDouble(3, entity.getPret());
            pstmt.setInt(4, entity.getIdInstructor());
            pstmt.setInt(5, entity.getId());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la actualizarea cursului: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Curs WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la stergerea cursului: " + e.getMessage());
        }
    }

    private Curs mapRowToCurs(ResultSet rs) throws SQLException {
        Curs c = new Curs(
                rs.getString("titlu"),
                rs.getString("categorie"),
                rs.getDouble("pret"),
                rs.getInt("id_instructor")
        );

        c.setId(rs.getInt("id"));
        return c;
    }
}