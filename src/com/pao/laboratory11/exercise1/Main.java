package com.pao.laboratory11.exercise1;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Main {
    private static final Comparator<Transaction> TRANSACTION_COMPARATOR = Comparator.comparingInt(Transaction::getScore).reversed().thenComparing(Transaction::getAmount, Comparator.reverseOrder()).thenComparing(Transaction::getDate).thenComparingInt(Transaction::getId);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        if (!sc.hasNextInt())
            return;

        int n = sc.nextInt();
        Map<Integer, Transaction> transactionMap = new LinkedHashMap<>();
        List<Transaction> transactionList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            BigDecimal amount = sc.nextBigDecimal();
            LocalDate date = LocalDate.parse(sc.next());
            String country = sc.next().toUpperCase();
            String channel = sc.next().toUpperCase();

            Transaction tx = new Transaction(id, amount, date, country, channel);
            tx.setScore(FraudRules.computeScore(tx));
            transactionMap.put(id, tx);
            transactionList.add(tx);
        }

        if (!sc.hasNextInt())
            return;

        int q = sc.nextInt();

        for (int i = 0; i < q; i++) {
            if (!sc.hasNext())
                break;

            String command = sc.next();

            if (command.equals("CHECK")) {
                int id = sc.nextInt();
                Transaction t = transactionMap.get(id);

                if (t == null) {
                    System.out.println("CHECK " + id + " => NOT_FOUND");
                }

                else {
                    String verdict = FraudRules.flaggedRule.test(t) ? "FLAG" : "ALLOW";
                    System.out.println("CHECK " + id + " => " + verdict + " score=" + t.getScore());
                }
            }

            else if (command.equals("LIST_FLAGGED")) {
                List<Transaction> flagged = transactionList.stream().filter(FraudRules.flaggedRule).sorted(TRANSACTION_COMPARATOR).collect(Collectors.toList());

                if (flagged.isEmpty()) {
                    System.out.println("NONE");
                }

                else {
                    flagged.forEach(t -> System.out.println("[" + t.getId() + "] FLAG score=" + t.getScore()));
                }
            }

            else if (command.equals("TOP_RISK")) {
                int k = sc.nextInt();
                transactionList.stream().sorted(TRANSACTION_COMPARATOR).limit(k).forEach(t -> {String v = FraudRules.flaggedRule.test(t) ? "FLAG" : "ALLOW";System.out.println("[" + t.getId() + "] " + v + " score=" + t.getScore());});
            }

            else {
                System.out.println("ERR UNKNOWN_COMMAND");
            }
        }
    }
}