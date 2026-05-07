package com.pao.laboratory11.exercise1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.function.Predicate;

public class FraudRules {
    public static final BigDecimal AMOUNT_THRESHOLD = new BigDecimal("1000.00");
    public static final Set<String> RISKY_COUNTRIES = Set.of("NG", "RU", "UA", "CN", "BR");
    public static final Set<String> SUSPICIOUS_CHANNELS = Set.of("WEB", "MOBILE");

    public static Predicate<Transaction> amountOverThreshold = t -> t.getAmount().compareTo(AMOUNT_THRESHOLD) > 0;
    public static Predicate<Transaction> countryInRisk = t -> RISKY_COUNTRIES.contains(t.getCountry().toUpperCase());
    public static Predicate<Transaction> channelSuspicious = t -> SUSPICIOUS_CHANNELS.contains(t.getChannel().toUpperCase());

    public static Predicate<Transaction> flaggedRule = amountOverThreshold.or(countryInRisk).or(channelSuspicious);

    public static int computeScore(Transaction t) {
        int amountScore = Math.min(50, t.getAmount().divide(new BigDecimal("1000"), 0, RoundingMode.DOWN).intValue() * 10);

        int countryScore = RISKY_COUNTRIES.contains(t.getCountry().toUpperCase()) ? 20 : 0;

        int channelScore = switch (t.getChannel().toUpperCase()) {
            case "WEB" -> 22;
            case "MOBILE", "APP" -> 10;
            case "ATM" -> 5;
            case "POS" -> 3;
            default -> 0;
        };

        return amountScore + countryScore + channelScore;
    }
}