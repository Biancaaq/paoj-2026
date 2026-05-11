package com.pao.laboratory11.exercise1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final Comparator<Transaction> BY_RISK = Comparator.comparingInt(FraudRules::computeScore).reversed().thenComparingInt(Transaction::getId);

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line = br.readLine();

            if (line == null || line.trim().isEmpty()) {
                return;
            }

            int n = Integer.parseInt(line.trim());
            Map<Integer, Transaction> byId = new HashMap<>();
            List<Transaction> all = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String[] tok = br.readLine().trim().split("\\s+");
                Transaction tx = new Transaction(
                        Integer.parseInt(tok[0]),
                        new BigDecimal(tok[1]),
                        LocalDate.parse(tok[2]),
                        tok[3].toUpperCase(),
                        tok[4].toUpperCase()
                );

                byId.put(tx.getId(), tx);
                all.add(tx);
            }

            line = br.readLine();

            if (line == null || line.trim().isEmpty()) {
                return;
            }

            int q = Integer.parseInt(line.trim());

            for (int i = 0; i < q; i++) {
                String cmdLine = br.readLine();

                if (cmdLine == null) {
                    break;
                }

                String[] cmd = cmdLine.trim().split("\\s+");
                String op = cmd[0].toUpperCase();

                switch (op) {
                    case "CHECK" -> {
                        int id = Integer.parseInt(cmd[1]);
                        Transaction tx = byId.get(id);

                        if (tx == null) {
                            System.out.println("CHECK " + id + " => NOT_FOUND");
                        }

                        else {
                            int score = FraudRules.computeScore(tx);
                            System.out.println("CHECK " + id + " => " + FraudRules.getVerdict(score) + " score=" + score);
                        }
                    }

                    case "LIST_FLAGGED" -> {
                        List<Transaction> flagged = all.stream().filter(t -> FraudRules.computeScore(t) >= FraudRules.FLAG_THRESHOLD).sorted(BY_RISK).toList();

                        if (flagged.isEmpty()) {
                            System.out.println("NONE");
                        }

                        else {
                            flagged.forEach(t -> System.out.println(formatLine(t)));
                        }
                    }

                    case "TOP_RISK" -> {
                        int k = Integer.parseInt(cmd[1]);
                        all.stream().sorted(BY_RISK).limit(k).forEach(t -> System.out.println(formatLine(t)));
                    }

                    default -> System.out.println("ERR UNKNOWN_COMMAND");
                }
            }
        } catch (Exception e) {
        }
    }

    private static String formatLine(Transaction t) {
        int score = FraudRules.computeScore(t);
        return "[" + t.getId() + "] " + FraudRules.getVerdict(score) + " score=" + score;
    }
}