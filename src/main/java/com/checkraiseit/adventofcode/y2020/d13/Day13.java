package com.checkraiseit.adventofcode.y2020.d13;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Log4j2
public class Day13 {

    private static final int EARLIEST_DEPARTURE_TIMESTAMP = 1000067;
    private static final String BUS_IDS = "17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,439,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,787,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19";

    public static void main(String[] args) throws IOException {
        part1();
        part2("17,x,13,19");
        part2("1789,37,47,1889");
        part2(BUS_IDS);
    }

    private static void part1() {
        Bus firstBus = Arrays.stream(BUS_IDS.split(","))
                .filter(id -> !id.equals("x"))
                .map(Integer::parseInt)
                .map(Bus::new)
                .min(Comparator.comparing(bus -> bus.waitTime(EARLIEST_DEPARTURE_TIMESTAMP)))
                .orElseThrow();
        log.info("Part 1: {}", firstBus.part1());
    }

    private static void part2(String busIds) {
        List<Bus> buses = EntryStream.of(busIds.split(","))
                .filter(entry -> !entry.getValue().equals("x"))
                .map(entry -> new Bus(Integer.parseInt(entry.getValue()), entry.getKey()))
                .toList();
        Bus firstBus = buses.get(0);
        long earliestTimestamp = LongStreamEx.iterate(0, l -> l += firstBus.id)
                .findFirst(l -> StreamEx.of(buses).allMatch(bus -> bus.matchesDesiredWaitTime(l)))
                .orElseThrow();
        log.info("Part 2: {}", earliestTimestamp);
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class Bus {
        private final int id;
        private int desiredWaitTime;

        public long waitTime(long timestamp) {
            return (((long) Math.ceil(((double) timestamp) / id)) * id) - timestamp;
        }

        private boolean matchesDesiredWaitTime(long timestamp) {
            return (timestamp) % id == (id - desiredWaitTime);
        }

        public long part1() {
            return id * waitTime(EARLIEST_DEPARTURE_TIMESTAMP);
        }
    }
}
