package com.pao.laboratory11.exercise2;

import com.pao.laboratory11.exercise1.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static class ExtendedTransaction {
        private final Transaction transaction;
        private final String accountId;

        public ExtendedTransaction(Transaction transaction, String accountId) {
            this.transaction = transaction;
            this.accountId = accountId;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getYearMonth() {
            return transaction.getDate().toString().substring(0, 7);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        if (!sc.hasNextInt())
            return;

        int n = sc.nextInt();
        List<ExtendedTransaction> transactions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            BigDecimal amount = sc.nextBigDecimal();
            LocalDate date = LocalDate.parse(sc.next());
            String country = sc.next();
            String channel = sc.next();
            String accountId = sc.next();

            Transaction baseTx = new Transaction(id, amount, date, country, channel);
            transactions.add(new ExtendedTransaction(baseTx, accountId));
        }

        if (!sc.hasNextInt())
            return;

        int q = sc.nextInt();

        while (q-- > 0) {
            if (!sc.hasNext())
                break;

            String command = sc.next();

            switch (command) {
                case "REPORT_MONTH" -> {
                    String month = sc.next();
                    reportMonth(transactions, month);
                }

                case "REPORT_ACCOUNT" -> {
                    String account = sc.next();
                    reportAccount(transactions, account);
                }

                case "TOP_CHANNELS" -> {
                    int k = sc.nextInt();
                    reportTopChannels(transactions, k);
                }

                default -> System.out.println("ERR UNKNOWN_COMMAND");
            }
        }
    }

    private static void reportMonth(List<ExtendedTransaction> transactions, String month) {
        List<ExtendedTransaction> filtered = transactions.stream().filter(t -> t.getYearMonth().equals(month)).toList();

        BigDecimal total = filtered.stream().map(t -> t.getTransaction().getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf(Locale.US, "MONTH %s total=%.2f count=%d%n", month, total, filtered.size());
    }

    private static void reportAccount(List<ExtendedTransaction> transactions, String accountId) {
        List<ExtendedTransaction> filtered = transactions.stream().filter(t -> t.getAccountId().equals(accountId)).toList();

        BigDecimal total = filtered.stream().map(t -> t.getTransaction().getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf(Locale.US, "ACCOUNT %s total=%.2f count=%d%n", accountId, total, filtered.size());
    }

    private static void reportTopChannels(List<ExtendedTransaction> transactions, int k) {
        Map<String, Long> channelCounts = transactions.stream().collect(Collectors.groupingBy(t -> t.getTransaction().getChannel(), Collectors.counting()));

        channelCounts.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())).limit(k).forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}