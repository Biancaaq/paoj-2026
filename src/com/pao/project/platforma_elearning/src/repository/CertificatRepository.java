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
        String sql = "INSERT INTO Certificat (cod_unic, id_cursant, titlu_curs, data_emiterii) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getCodUnic());
            String sqlAdaptat = "INSERT INTO Certificat (cod_unic, nume_cursant, titlu_curs) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt2 = connection.prepareStatement(sqlAdaptat)) {
                pstmt2.setString(1, entity.getCodUnic());
                pstmt2.setString(2, entity.getNumeCursant());
                pstmt2.setString(3, entity.getNumeCurs());
                pstmt2.executeUpdate();
                return;
            }
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

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        System.out.println("Modificarea certificatelor imutabile nu este permisa.");
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

    private Certificat mapRowToCertificat(ResultSet rs) throws SQLException {
        return new Certificat(
                rs.getString("cod_unic"),
                rs.getString("nume_cursant"),
                rs.getString("titlu_curs")
        );
    }
}