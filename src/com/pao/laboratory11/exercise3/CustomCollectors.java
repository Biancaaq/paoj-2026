package com.pao.laboratory11.exercise3;

import com.pao.laboratory11.exercise1.Transaction;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;

public class CustomCollectors {

    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
        class Agg {
            Map<String, Long> countries = new HashMap<>();
            Map<String, Long> channels = new HashMap<>();
            BigDecimal total = BigDecimal.ZERO;
            List<Transaction> all = new ArrayList<>();

            void accumulate(Transaction tx) {
                countries.merge(tx.getCountry(), 1L, Long::sum);
                channels.merge(tx.getChannel(), 1L, Long::sum);
                total = total.add(tx.getAmount());
                all.add(tx);
            }

            Agg combine(Agg other) {
                other.countries.forEach((k, v) -> countries.merge(k, v, Long::sum));
                other.channels.forEach((k, v) -> channels.merge(k, v, Long::sum));
                total = total.add(other.total);
                all.addAll(other.all);

                return this;
            }

            Snapshot finisher() {
                List<Transaction> top = all.stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()).limit(topN).toList();

                return new Snapshot(countries, channels, total, top);
            }
        }

        return Collector.of(Agg::new, Agg::accumulate, Agg::combine, Agg::finisher);
    }
}