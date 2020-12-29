package com.checkraiseit.adventofcode.y2020.d23;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;

import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Log4j2
public class Day23 {

    private static int MAX_VALUE = 9;

    public static void main(String[] args) throws IOException {
        log.info("part 1 example: {}", labelAfterMoves("389125467", 10));
        log.info("part 1: {}", labelAfterMoves("219347865", 100));
        log.info("part 2 example: {}", labelAfterTenMillionMoves("389125467"));
        log.info("part 2: {}", labelAfterTenMillionMoves("219347865"));
    }

    static String labelAfterMoves(String startingCups, int moves) {
        MAX_VALUE = 9;
        CupCircle cupCircle = new CupCircle(startingCups);
        IntStreamEx.range(moves).forEach(i -> cupCircle.move());
        return cupCircle.cupsAfterCupOne();
    }

    static long labelAfterTenMillionMoves(String startingCups) {
        MAX_VALUE = 1_000_000;
        CupCircle cupCircle = new CupCircle(startingCups);
        IntStreamEx.range(10_000_000).forEach(i -> {
            if (i % 1000 == 0) {
                log.info("labelAfterTenMillionMoves: {}", i);
            }
            cupCircle.move();
        });
        return cupCircle.productOfTwoCupsAfterCupOne();
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class Cup {
        private final int value;

        public Cup previous() {
            if (value == 1) {
                return new Cup(MAX_VALUE);
            }
            return new Cup(value - 1);
        }

        public long multiply(Cup cup) {
            return ((long) value) * cup.value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private static class CupCircle {
        private final List<Cup> cups;
        private int currentCupIndex = 0;

        public CupCircle(String startingCups) {
            cups = EntryStream.of(startingCups.split(""))
                    .map(entry -> new Cup(Integer.parseInt(entry.getValue()))).toList();
            IntStreamEx.rangeClosed(10, MAX_VALUE).forEach(i -> cups.add(new Cup(i)));
        }

        public void move() {
            Cup currentCup = cups.get(currentCupIndex);
            Cup cup1 = removeNextCup();
            Cup cup2 = removeNextCup();
            Cup cup3 = removeNextCup();
            List<Cup> pickedCups = newArrayList(cup1, cup2, cup3);
            Cup previousCup = currentCup.previous();
            while (pickedCups.contains(previousCup)) {
                previousCup = previousCup.previous();
            }
            int destinationCupIndex = Math.min(cups.indexOf(previousCup) + 1, cups.size());
            placeCupAt(cup1, destinationCupIndex);
            placeCupAt(cup2, Math.min(destinationCupIndex + 1, cups.size()));
            placeCupAt(cup3, Math.min(destinationCupIndex + 2, cups.size()));
            if (destinationCupIndex <= currentCupIndex) {
                currentCupIndex += 3;
            }
            currentCupIndex = (currentCupIndex + 1) % cups.size();
        }

        private void placeCupAt(Cup cup, int index) {
            cups.add(index, cup);
        }

        private Cup removeNextCup() {
            if (currentCupIndex + 1 >= cups.size()) {
                currentCupIndex--;
                return cups.remove(0);
            }
            return cups.remove(currentCupIndex + 1);
        }

        public String cupsAfterCupOne() {
            int cupAfterOne = (cups.indexOf(new Cup(1)) + 1) % cups.size();
            StringBuilder label = new StringBuilder();
            for (int i = cupAfterOne; i < (cupAfterOne + cups.size() - 1); i++) {
                label.append(cups.get(i % cups.size()));
            }
            return label.toString();
        }

        public long productOfTwoCupsAfterCupOne() {
            int cupAfterOne = (cups.indexOf(new Cup(1)) + 1) % cups.size();
            int cupTwoAfterOne = (cupAfterOne + 1) % cups.size();
            return cups.get(cupAfterOne).multiply(cups.get(cupTwoAfterOne));
        }
    }
}
