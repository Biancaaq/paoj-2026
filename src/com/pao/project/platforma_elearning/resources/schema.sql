DROP TABLE IF EXISTS Certificat;
DROP TABLE IF EXISTS Inrolare;
DROP TABLE IF EXISTS Curs;
DROP TABLE IF EXISTS Utilizator;

CREATE TABLE Utilizator (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nume TEXT NOT NULL,
                            email TEXT NOT NULL UNIQUE,
                            parola TEXT NOT NULL,
                            tip_utilizator TEXT NOT NULL,
                            portofel_virtual REAL DEFAULT 0.0,
                            salariu REAL DEFAULT 0.0,
                            specializare TEXT,
                            nivel_acces INTEGER DEFAULT 0
);

CREATE TABLE Curs (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      titlu TEXT NOT NULL,
                      categorie TEXT NOT NULL,
                      pret REAL NOT NULL,
                      id_instructor INTEGER,
                      FOREIGN KEY (id_instructor) REFERENCES Utilizator(id) ON DELETE SET NULL
);

CREATE TABLE Inrolare (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          id_cursant INTEGER NOT NULL,
                          id_curs INTEGER NOT NULL,
                          data_inrolarii TEXT NOT NULL,
                          progres REAL DEFAULT 0.0,
                          FOREIGN KEY (id_cursant) REFERENCES Utilizator(id) ON DELETE CASCADE,
                          FOREIGN KEY (id_curs) REFERENCES Curs(id) ON DELETE CASCADE
);

CREATE TABLE Certificat (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            cod_unic TEXT NOT NULL,
                            nume_cursant TEXT NOT NULL,
                            titlu_curs TEXT NOT NULL,
                            data_emiterii TEXT NOT NULL
);