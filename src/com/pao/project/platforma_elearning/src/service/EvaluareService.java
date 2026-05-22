package com.pao.project.platforma_elearning.src.service;

import com.pao.project.platforma_elearning.src.exception.FonduriInsuficienteException;
import com.pao.project.platforma_elearning.src.model.Certificat;
import com.pao.project.platforma_elearning.src.model.Inrolare;
import com.pao.project.platforma_elearning.src.model.ScorQuiz;
import com.pao.project.platforma_elearning.src.model.Cursant;
import com.pao.project.platforma_elearning.src.model.Curs;
import com.pao.project.platforma_elearning.src.repository.InrolareRepository;
import com.pao.project.platforma_elearning.src.repository.UtilizatorRepository;
import com.pao.project.platforma_elearning.src.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class EvaluareService {
    private static EvaluareService instance;

    private List<Inrolare> inrolari = new ArrayList<>();
    private List<ScorQuiz> scoruri = new ArrayList<>();

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    private final InrolareRepository inrolareRepository = new InrolareRepository();
    private final UtilizatorRepository utilizatorRepository = new UtilizatorRepository();

    private EvaluareService() {
        try {
            this.inrolari = new ArrayList<>(inrolareRepository.findAll());
        }

        catch (Exception e) {
            this.inrolari = new ArrayList<>();
        }
    }

    public static EvaluareService getInstance() {
        if (instance == null) {
            instance = new EvaluareService();
        }

        return instance;
    }

    public boolean achizitioneazaCursTranzactional(Cursant cursant, Curs curs) throws FonduriInsuficienteException {
        if (cursant.getPortofelVirtual() < curs.getPret()) {
            throw new FonduriInsuficienteException("Fonduri insuficiente! Pret curs: " + curs.getPret() + " RON, Sold curent: " + cursant.getPortofelVirtual() + " RON");
        }

        try {
            connection.setAutoCommit(false);

            double noulSold = cursant.getPortofelVirtual() - curs.getPret();
            cursant.setPortofelVirtual(noulSold);

            utilizatorRepository.update(cursant);

            Inrolare inrNoua = new Inrolare(cursant.getId(), curs.getId());
            inrolareRepository.save(inrNoua);

            connection.commit();

            this.inrolari.add(inrNoua);

            AuditService.getInstance().logActiune("achizitie_curs_id_" + curs.getId());
            System.out.println("Tranzactie finalizata cu succes in DB!");
            return true;

        }

        catch (SQLException e) {
            try {
                System.out.println("Eroare in tranzactie. Se executa rollback: " + e.getMessage());
                connection.rollback();

                cursant.setPortofelVirtual(cursant.getPortofelVirtual() + curs.getPret());
            }

            catch (SQLException rollbackEx) {
                System.out.println("Eroare critica la rollback: " + rollbackEx.getMessage());
            }

            return false;
        }

        finally {
            try {
                connection.setAutoCommit(true);
            }

            catch (SQLException e) {
                System.out.println("Eroare la resetarea auto-commit: " + e.getMessage());
            }
        }
    }

    public boolean esteDejaInrolat(int idCursant, int idCurs) {
        for (Inrolare i : inrolari) {
            if (i.getIdCursant() == idCursant && i.getIdCurs() == idCurs) {
                return true;
            }
        }

        return false;
    }

    public Inrolare cautaInrolare(int idInrolare) {
        for (Inrolare i : inrolari) {
            if (i.getIdInrolare() == idInrolare) {
                return i;
            }
        }

        return null;
    }

    public void afiseazaInrolariUtilizator(int idCursant) {
        inrolareRepository.afiseazaSituatieCursuriStudent(idCursant);
    }

    public void inregistreazaParcurgereLectie(Inrolare inrolare, int numarLectiiTotale) {
        if (inrolare == null || numarLectiiTotale == 0) {
            return;
        }

        double crestere = 100.0 / numarLectiiTotale;
        double progresNou = inrolare.getProgres() + crestere;

        if (progresNou >= 99.9) {
            progresNou = 100.0;
        }

        inrolare.setProgres(progresNou);

        inrolareRepository.update(inrolare);
    }

    public void salveazaScorQuiz(int idCursant, int idQuiz, double punctaj) {
        scoruri.add(new ScorQuiz(idCursant, idQuiz, punctaj));
        System.out.println("Scorul de " + punctaj + " a fost inregistrat in sistem");
    }

    public Map<Integer, List<ScorQuiz>> grupeazaScoruriPeCursanti() {
        return scoruri.stream().collect(Collectors.groupingBy(ScorQuiz::getIdCursant));
    }

    public boolean areQuizPromovat(int idCursant, int idQuiz) {
        for (ScorQuiz s : scoruri) {
            if (s.getIdCursant() == idCursant && s.getIdQuiz() == idQuiz && s.getPunctaj() >= 5.0) {
                return true;
            }
        }

        return false;
    }

    public Certificat genereazaCertificat(String numeCursant, String numeCurs, double progres) {
        if (progres >= 100.0) {
            String codUnic = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            System.out.println("Felicitari! Cod certificat: " + codUnic);
            return new Certificat(codUnic, numeCursant, numeCurs);
        }

        System.out.println("Certificatul este primit doar dupa finalizarea cursului. Progres curent: " + progres + "%");
        return null;
    }

    public void stergeDateAsociateCursului(int idCurs, List<Integer> iduriQuiz) {
        inrolari.removeIf(i -> i.getIdCurs() == idCurs);
        scoruri.removeIf(s -> iduriQuiz.contains(s.getIdQuiz()));
        System.out.println("Datele asociate cursului au fost sterse");
    }

    public void stergeDateInrolare(int idInrolare, int idCursant, List<Integer> iduriQuiz) {
        boolean eliminat = inrolari.removeIf(i -> i.getIdInrolare() == idInrolare);

        if (eliminat) {
            inrolareRepository.delete(idInrolare);
            scoruri.removeIf(s -> s.getIdCursant() == idCursant && iduriQuiz.contains(s.getIdQuiz()));
            System.out.println("Inrolarea si scorurile asociate au fost eliminate definitiv din sistem");
        }

        else {
            System.out.println("Inrolarea cu ID-ul respectiv nu a fost gasita");
        }
    }
}