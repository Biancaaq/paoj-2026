package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON

        Scanner sc = new Scanner(System.in);
        List<Tranzactie> lista = new ArrayList<>();

        if (!sc.hasNextInt())
            return;
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            lista.add(new Tranzactie(sc.nextInt(), sc.nextDouble(), sc.next(), TipTranzactie.valueOf(sc.next())));
        }

        while (sc.hasNext()) {
            String comanda = sc.next();
            switch (comanda) {
                case "UNIQUE_IDS": {
                    Set<Integer> uniqueIds = new LinkedHashSet<>();

                    for (Tranzactie t : lista)
                        uniqueIds.add(t.getId());

                    System.out.println("IDs unice (" + uniqueIds.size() + "): " + uniqueIds);
                    break;
                }

                case "MONTHLY_REPORT": {
                    Map<String, double[]> report = new TreeMap<>();

                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7);
                        report.putIfAbsent(luna, new double[2]);

                        if (t.getTip() == TipTranzactie.CREDIT)
                            report.get(luna)[0] += t.getSuma();

                        else
                            report.get(luna)[1] += t.getSuma();
                    }

                    for (Map.Entry<String, double[]> entry : report.entrySet()) {
                        System.out.printf("%s: CREDIT %.2f RON, DEBIT %.2f RON\n", entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
                    }

                    break;
                }

                case "TOP": {
                    int topN = sc.nextInt();
                    List<Tranzactie> copie = new ArrayList<>(lista);
                    copie.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma()));
                    System.out.println("Top " + topN + ":");
                    int limit = Math.min(topN, copie.size());

                    for (int i = 0; i < limit; i++) {
                        System.out.println(copie.get(i));
                    }

                    break;
                }

                case "SORT_ASC": {
                    lista.sort(Comparator.comparingDouble(Tranzactie::getSuma));
                    lista.forEach(System.out::println);
                    break;
                }

                case "SORT_DESC": {
                    lista.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma()));
                    lista.forEach(System.out::println);
                    break;
                }

                case "REVERSE": {
                    Collections.reverse(lista);
                    lista.forEach(System.out::println);
                    break;
                }

                case "MIN_MAX": {
                    if (!lista.isEmpty()) {
                        Tranzactie min = Collections.min(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                        Tranzactie max = Collections.max(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                        System.out.println("MIN: " + min);
                        System.out.println("MAX: " + max);
                    }

                    break;
                }

                case "CME_DEMO": {
                    try {
                        for (Tranzactie t : lista) {
                            lista.remove(t);
                        }
                    }

                    catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }

                    break;
                }
            }
        }
        sc.close();
    }
}
