package com.pao.project.platforma_elearning.src.repository;

import com.pao.project.platforma_elearning.src.model.Certificat;
import com.pao.project.platforma_elearning.src.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificatRepository implements Repository<Certificat, Integer> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Certificat entity) {
        String sql = "INSERT INTO Certificat (cod_unic, nume_cursant, titlu_curs, data_emiterii) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getCodUnic());
            pstmt.setString(2, entity.getNumeCursant());
            pstmt.setString(3, entity.getNumeCurs());
            pstmt.setString(4, java.time.LocalDate.now().toString());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la salvarea certificatului: " + e.getMessage());
        }
    }

    @Override
    public Optional<Certificat> findById(Integer id) {
        String sql = "SELECT * FROM Certificat WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCertificat(rs));
                }
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la cautarea certificatului: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Certificat> findAll() {
        List<Certificat> certificate = new ArrayList<>();
        String sql = "SELECT * FROM Certificat";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                certificate.add(mapRowToCertificat(rs));
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare la citirea certificatelor: " + e.getMessage());
        }

        return certificate;
    }

    @Override
    public void update(Certificat entity) {
        String sql = "UPDATE Certificat SET nume_cursant = ?, titlu_curs = ? WHERE cod_unic = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNumeCursant());
            pstmt.setString(2, entity.getNumeCurs());
            pstmt.setString(3, entity.getCodUnic());
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la actualizarea certificatului: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Certificat WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println("Eroare la stergerea certificatului: " + e.getMessage());
        }
    }

    public void afiseazaCertificateEmiseCuDetaliiUtilizator() {
        String sql = "SELECT cert.id, cert.cod_unic, cert.titlu_curs, u.email " + "FROM Certificat cert " + "JOIN Utilizator u ON cert.nume_cursant = u.nume";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\nRaport audit certificate emise per email utilizator");

            while (rs.next()) {
                System.out.println("Certificat: [" + rs.getString("cod_unic") + "] pentru cursul: " + rs.getString("titlu_curs") + " | Email Detinator: " + rs.getString("email"));
            }
        }

        catch (SQLException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private Certificat mapRowToCertificat(ResultSet rs) throws SQLException {
        return new Certificat(
                rs.getString("cod_unic"),
                rs.getString("nume_cursant"),
                rs.getString("titlu_curs")
        );
    }
}