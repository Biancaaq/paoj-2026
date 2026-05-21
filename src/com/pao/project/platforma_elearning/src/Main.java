//package com.pao.project.platforma_elearning.src;
//
//import com.pao.project.platforma_elearning.src.service.MeniuService;
//
//public class Main {
//    public static void main(String[] args) {
//        MeniuService meniu = MeniuService.getInstance();
//        meniu.porneste();
//    }
//}

package com.pao.project.platforma_elearning.src;

import com.pao.project.platforma_elearning.src.util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Pornire aplicatie si conectare la baza de date");

        Connection conn = DatabaseConnection.getInstance().getConnection();

        if (conn != null) {
            System.out.println("Verificare incheiata cu succes");
        }

        else {
            System.out.println("A aparut o problema la conectare");
        }
    }
}