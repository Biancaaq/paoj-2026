package com.pao.project.platforma_elearning.src.repository;

import com.pao.project.platforma_elearning.src.model.*;
import com.pao.project.platforma_elearning.src.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilizatorRepository implements Repository<Utilizator, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Utilizator entity) {
        String sql = "INSERT INTO Utilizator (nume, email, parola, tip_utilizator, portofel_virtual, salariu, specializare, nivel_acces) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNume());
            pstmt.setString(2, entity.getEmail());
            pstmt.setString(3, entity.getParola());
            pstmt.setString(4, entity.getTipUtilizator());

            if (entity instanceof Cursant) {
                pstmt.setDouble(5, ((Cursant) entity).getPortofelVirtual());
                pstmt.setDouble(6, 0.0);
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setInt(8, 0);
            }

            else if (entity instanceof Instructor) {
                pstmt.setDouble(5, 0.0);
                pstmt.setDouble(6, ((Instructor) entity).getSalariu());
                pstmt.setString(7, ((Instructor) entity).getSpecializare());
                pstmt.setInt(8, 0);
            }

            else if (entity instanceof Admin) {
                pstmt.setDouble(5, 0.0);
                pstmt.setDouble(6, ((Admin) entity).getSalariu());
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setInt(8, 10);
            }

            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la salvarea utilizatorului: " + e.getMessage());
        }
    }

    @Override
    public Optional<Utilizator> findById(Integer id) {
        String sql = "SELECT * FROM Utilizator WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToUtilizator(rs));
                }
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la cautarea utilizatorului dupa id: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Utilizator> findAll() {
        List<Utilizator> rezultate = new ArrayList<>();
        String sql = "SELECT * FROM Utilizator";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                rezultate.add(mapRowToUtilizator(rs));
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la preluarea tuturor utilizatorilor: " + e.getMessage());
        }

        return rezultate;
    }

    @Override
    public void update(Utilizator entity) {
        String sql = "UPDATE Utilizator SET nume = ?, email = ?, parola = ?, portofel_virtual = ?, specializare = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNume());
            pstmt.setString(2, entity.getEmail());
            pstmt.setString(3, entity.getParola());

            if (entity instanceof Cursant) {
                pstmt.setDouble(4, ((Cursant) entity).getPortofelVirtual());
                pstmt.setNull(5, Types.VARCHAR);
            }

            else if (entity instanceof Instructor) {
                pstmt.setDouble(4, 0.0);
                pstmt.setString(5, ((Instructor) entity).getSpecializare());
            }

            else {
                pstmt.setNull(4, Types.DOUBLE);
                pstmt.setNull(5, Types.VARCHAR);
            }

            pstmt.setInt(6, entity.getId());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la actualizarea utilizatorului: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Utilizator WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la stergerea utilizatorului: " + e.getMessage());
        }
    }

    private Utilizator mapRowToUtilizator(ResultSet rs) throws SQLException {
        int idBaza = rs.getInt("id");
        String nume = rs.getString("nume");
        String email = rs.getString("email");
        String parola = rs.getString("parola");
        String tip = rs.getString("tip_utilizator");
        double salariu = rs.getDouble("salariu");

        Utilizator u;
        if ("CURSANT".equals(tip)) {
            u = new Cursant(nume, email, parola, rs.getDouble("portofel_virtual"));
        }

        else if ("INSTRUCTOR".equals(tip)) {
            u = new Instructor(nume, email, parola, salariu, rs.getString("specializare"));
        }

        else {
            u = new Admin(nume, email, parola, salariu, rs.getInt("nivel_acces"));
        }

        u.setId(idBaza);
        return u;
    }
}