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

    public void afiseazaCursuriCuInstructori() {
        String sql = "SELECT c.id, c.titlu, c.categorie, c.pret, u.nume AS nume_instructor " + "FROM Curs c " + "JOIN Utilizator u ON c.id_instructor = u.id";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\nCatalog cursuri (Informatii complete din DB)");

            while (rs.next()) {
                System.out.println("Curs: [" + rs.getInt("id") + "] " + rs.getString("titlu") + " | Categorie: " + rs.getString("categorie") + " | Pret: " + rs.getDouble("pret") + " RON" + " | Profesor: " + rs.getString("nume_instructor"));
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la executarea interogarii cu JOIN: " + e.getMessage());
        }
    }

    public void afiseazaTopCursuriPopulare() {
        String sql = "SELECT c.id, c.titlu, COUNT(i.id) AS numar_studenti " + "FROM Curs c " + "LEFT JOIN Inrolare i ON c.id = i.id_curs " + "GROUP BY c.id " + "ORDER BY numar_studenti DESC " + "LIMIT 3";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\nTop 3 cursuri cu cei mai multi studenti");
            int pozitie = 1;

            while (rs.next()) {
                System.out.println(pozitie + ". " + rs.getString("titlu") + " -> " + rs.getInt("numar_studenti") + " studenti inrolati");
                pozitie++;
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la generarea topului de popularitate: " + e.getMessage());
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