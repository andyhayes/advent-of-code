package com.checkraiseit.adventofcode.y2020.d15;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newLinkedHashMap;

@Log4j2
public class Day15 {

    public static void main(String[] args) throws IOException {
        String line = Resources.readLines(Resources.getResource("y2020/day15.txt"), Charsets.UTF_8).get(0);
        speakNumberAfterTurn("0,3,6", 2020);
        speakNumberAfterTurn("3,1,2", 2020);
        speakNumberAfterTurn(line, 2020);
        speakNumberAfterTurn(line, 30000000);
    }

    private static void speakNumberAfterTurn(String line, int turnToSpeak) {
        List<Integer> numbers = StreamEx.split(line, ",").map(Integer::parseInt).toList();

        Map<Integer, LastSeen> map = newHashMap();
        EntryStream.of(numbers).forEach(entry ->
                map.put(entry.getValue(), new LastSeen(entry.getKey() + 1)));

        int lastNumber = numbers.get(numbers.size() - 1);
        for (int turn = numbers.size() + 1; turn <= turnToSpeak; turn++) {
            int nextNumber = nextNumber(lastNumber, map);
            LastSeen lastSeen = map.get(nextNumber);
            if (lastSeen == null) {
                map.put(nextNumber, new LastSeen(turn));
            } else {
                lastSeen.updateLastSeen(turn);
            }
            lastNumber = nextNumber;
        }
        log.info(lastNumber);
    }

    private static int nextNumber(int lastNumber, Map<Integer, LastSeen> map) {
        LastSeen lastSeen = map.get(lastNumber);
        if (lastSeen.turnBeforeThat == null) {
            return 0;
        }
        return lastSeen.turn - lastSeen.turnBeforeThat;
    }

    private static class LastSeen {
        private Integer turn;
        private Integer turnBeforeThat;

        public LastSeen(int turn) {
            this.turn = turn;
        }

        public void updateLastSeen(int turn) {
            turnBeforeThat = this.turn;
            this.turn = turn;
        }
    }
}
