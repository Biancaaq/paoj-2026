package com.pao.project.platforma_elearning.src.repository;

import com.pao.project.platforma_elearning.src.model.Inrolare;
import com.pao.project.platforma_elearning.src.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InrolareRepository implements Repository<Inrolare, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Inrolare entity) {
        String sql = "INSERT INTO Inrolare (id_cursant, id_curs, data_inrolarii, progres) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getIdCursant());
            pstmt.setInt(2, entity.getIdCurs());
            pstmt.setString(3, entity.getDataInrolarii().toString());
            pstmt.setDouble(4, entity.getProgres());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la salvarea inrolarii: " + e.getMessage());
        }
    }

    @Override
    public Optional<Inrolare> findById(Integer id) {
        String sql = "SELECT * FROM Inrolare WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToInrolare(rs));
                }
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la gasirea inrolarii: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Inrolare> findAll() {
        List<Inrolare> inrolari = new ArrayList<>();
        String sql = "SELECT * FROM Inrolare";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                inrolari.add(mapRowToInrolare(rs));
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la preluarea inrolarilor: " + e.getMessage());
        }

        return inrolari;
    }

    @Override
    public void update(Inrolare entity) {
        String sql = "UPDATE Inrolare SET progres = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, entity.getProgres());
            pstmt.setInt(2, entity.getIdInrolare());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la actualizarea inrolarii: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Inrolare WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la stergerea inrolarii: " + e.getMessage());
        }
    }

    public void afiseazaSituatieCursuriStudent(int idStudent) {
        String sql = "SELECT i.id, c.titlu, i.data_inrolarii, i.progres " + "FROM Inrolare i " + "JOIN Curs c ON i.id_curs = c.id " + "WHERE i.id_cursant = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idStudent);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\nCursurile tale inrolate (Date din DB)");
                boolean areCursuri = false;

                while (rs.next()) {
                    areCursuri = true;
                    System.out.println("Inrolare ID: " + rs.getInt("id") + " | Curs: " + rs.getString("titlu") + " | Data: " + rs.getString("data_inrolarii") + " | Progres: " + rs.getDouble("progres") + "%");
                }

                if (!areCursuri) {
                    System.out.println("Nu esti inrolat in niciun curs momentan");
                }
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la preluarea situatiei studentului: " + e.getMessage());
        }
    }

    private Inrolare mapRowToInrolare(ResultSet rs) throws SQLException {
        Inrolare i = new Inrolare(rs.getInt("id_cursant"), rs.getInt("id_curs"));
        i.setIdInrolare(rs.getInt("id"));
        i.setProgres(rs.getDouble("progres"));
        return i;
    }
}