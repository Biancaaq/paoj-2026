package com.pao.laboratory11.exercise1;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class FraudRules {
    public static final Set<String> HIGH_RISK_COUNTRIES = Set.of("RU", "NG", "IR", "KP", "SY");
    public static final Map<String, Integer> CHANNEL_SCORE = Map.of(
            "WEB", 15,
            "APP", 10,
            "CRYPTO", 30,
            "POS", 5,
            "ATM", 0
    );

    public static final int FLAG_THRESHOLD = 60;

    public static Predicate<Transaction> amountOverThreshold = t -> t.getAmount().compareTo(new BigDecimal("1000")) >= 0;
    public static Predicate<Transaction> countryInRisk = t -> HIGH_RISK_COUNTRIES.contains(t.getCountry());
    public static Predicate<Transaction> channelSuspicious = t -> Set.of("WEB", "APP", "CRYPTO").contains(t.getChannel());

    public static int computeScore(Transaction t) {
        int score = 0;
        double amt = t.getAmount().doubleValue();

        if (amt >= 5000) {
            score += 70;
        }

        else if (amt >= 1000) {
            score += 40;
        }

        else if (amt >= 500) {
            score += 20;
        }

        if (amt <= 100) {
            score += 5;
        }

        if (HIGH_RISK_COUNTRIES.contains(t.getCountry())) {
            score += 25;
        }

        score += CHANNEL_SCORE.getOrDefault(t.getChannel(), 0);

        return score;
    }

    public static String getVerdict(int score) {
        return score >= FLAG_THRESHOLD ? "FLAG" : "ALLOW";
    }
}