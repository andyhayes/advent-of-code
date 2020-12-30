package com.checkraiseit.adventofcode.y2020.d23;

import lombok.extern.log4j.Log4j2;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;

@Log4j2
public class Day23 {

    public static void main(String[] args) throws IOException {
        log.info("part 1 example: {}", labelAfterMoves("389125467", 10));
        log.info("part 1: {}", labelAfterMoves("219347865", 100));
        log.info("part 2 example: {}", labelAfterTenMillionMoves("389125467"));
        log.info("part 2: {}", labelAfterTenMillionMoves("219347865"));
    }

    static String labelAfterMoves(String startingCups, int moves) {
        CupCircle cupCircle = new CupCircle(startingCups, 9);
        IntStreamEx.range(moves).forEach(i -> cupCircle.move());
        return cupCircle.cupsAfterCupOne();
    }

    static long labelAfterTenMillionMoves(String startingCups) {
        CupCircle cupCircle = new CupCircle(startingCups, 1_000_000);
        IntStreamEx.range(10_000_000).forEach(i -> cupCircle.move());
        return cupCircle.productOfTwoCupsAfterCupOne();
    }

    private static class CupCircle {
        private final int maxValue;
        private int current;
        private final int[] cupValueToNext;

        public CupCircle(String startingCups, int maxValue) {
            this.maxValue = maxValue;
            cupValueToNext = new int[maxValue + 1];
            List<Integer> cups = StreamEx.of(startingCups.split("")).map(Integer::parseInt).toList();
            for (int i = 1; i < cups.size();  i++) {
                cupValueToNext[cups.get(i - 1)] = cups.get(i);
            }
            cupValueToNext[cups.get(cups.size() - 1)] = cups.get(0);
            current = cups.get(0);
            if (maxValue > cups.size()) {
                cupValueToNext[cups.get(cups.size() - 1)] = cups.size() + 1;
                IntStreamEx.range(10, maxValue).forEach(i -> cupValueToNext[i] = i + 1);
                cupValueToNext[maxValue] = cups.get(0);
            }
        }

        public void move() {
            int destination = current;
            int next1 = cupValueToNext[current];
            int next2 = cupValueToNext[next1];
            int next3 = cupValueToNext[next2];
            do {
                destination--;
                if (destination == 0) {
                    destination = maxValue;
                }
            } while (next1 == destination || next2 == destination || next3 == destination);

            cupValueToNext[current] = cupValueToNext[next3];
            cupValueToNext[next3] = cupValueToNext[destination];
            cupValueToNext[destination] = next1;

            current = cupValueToNext[current];
        }

        public String cupsAfterCupOne() {
            StringBuilder label = new StringBuilder();
            int cupAfterOne = cupValueToNext[1];
            while (cupAfterOne != 1) {
                label.append(cupAfterOne);
                cupAfterOne = cupValueToNext[cupAfterOne];
            }
            return label.toString();
        }

        public long productOfTwoCupsAfterCupOne() {
            int cupAfterOne = cupValueToNext[1];
            int cupAfterAfterOne = cupValueToNext[cupAfterOne];
            return ((long) cupAfterOne) * cupAfterAfterOne;
        }
    }
}
