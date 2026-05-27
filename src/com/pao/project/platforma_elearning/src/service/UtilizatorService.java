package com.pao.project.platforma_elearning.src.service;

import com.pao.project.platforma_elearning.src.exception.EntitateExistentaException;
import com.pao.project.platforma_elearning.src.exception.UtilizatorNegasitException;
import com.pao.project.platforma_elearning.src.model.Cursant;
import com.pao.project.platforma_elearning.src.model.Utilizator;
import com.pao.project.platforma_elearning.src.repository.UtilizatorRepository;

import java.util.List;
import java.util.Optional;

public class UtilizatorService {
    private static UtilizatorService instance;
    private final UtilizatorRepository utilizatorRepository = new UtilizatorRepository();

    private UtilizatorService() {}

    public static UtilizatorService getInstance() {
        if (instance == null) {
            instance = new UtilizatorService();
        }

        return instance;
    }

    public void inregistrare(Utilizator u) throws EntitateExistentaException {
        boolean exista = utilizatorRepository.findAll().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(u.getEmail()));

        if (exista) {
            throw new EntitateExistentaException("Email-ul " + u.getEmail() + " este deja folosit");
        }

        utilizatorRepository.save(u);
        System.out.println("Utilizator salvat in baza de date cu succes: " + u.getNume());
    }

    public Utilizator login(String email, String parola) throws UtilizatorNegasitException {
        Optional<Utilizator> u = utilizatorRepository.findAll().stream().filter(user -> user.getEmail().equalsIgnoreCase(email) && user.getParola().equals(parola)).findFirst();

        if (u.isEmpty()) {
            throw new UtilizatorNegasitException("Email sau parola incorecta");
        }

        return u.get();
    }

    public void afiseazaTotiUtilizatorii() {
        List<Utilizator> toti = utilizatorRepository.findAll();

        if (toti.isEmpty()) {
            System.out.println("Nu exista utilizatori in baza de date");
        }

        else {
            toti.forEach(System.out::println);
        }
    }

    public void alimenteazaPortofel(String email, double suma) throws UtilizatorNegasitException {
        List<Utilizator> toti = utilizatorRepository.findAll();
        Utilizator u = toti.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);

        if (u instanceof Cursant) {
            Cursant c = (Cursant) u;
            c.setPortofelVirtual(c.getPortofelVirtual() + suma);

            utilizatorRepository.update(c);
            System.out.println("Portofel actualizat in DB. Sold nou: " + c.getPortofelVirtual());
        }

        else {
            throw new UtilizatorNegasitException("Utilizatorul nu este cursant sau nu a fost gasit");
        }
    }
}