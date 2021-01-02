package com.checkraiseit.adventofcode.y2020.d13;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

@Log4j2
public class Day13 {

    private static final int EARLIEST_DEPARTURE_TIMESTAMP = 1000067;
    private static final String BUS_IDS = "17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,439,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,787,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19";

    public static void main(String[] args) throws IOException {
        part1(BUS_IDS);
        log.info("Part 2 example 1: {}", part2("17,x,13,19"));
        log.info("Part 2 example 2: {}", part2("1789,37,47,1889"));
        log.info("Part 2: {}", part2(BUS_IDS));
    }

    private static void part1(String busIds) {
        Bus firstBus = Arrays.stream(busIds.split(","))
                .filter(id -> !id.equals("x"))
                .map(Integer::parseInt)
                .map(Bus::new)
                .min(Comparator.comparing(bus -> bus.waitTime(EARLIEST_DEPARTURE_TIMESTAMP)))
                .orElseThrow();
        log.info("Part 1: {}", firstBus.part1());
    }

    private static long part2(String busIds) {
        List<Bus> buses = EntryStream.of(busIds.split(","))
                .filter(entry -> !entry.getValue().equals("x"))
                .map(entry -> new Bus(Integer.parseInt(entry.getValue()), entry.getKey()))
                .sorted(Comparator.comparingInt(Bus::getId).reversed())
                .toList();

        List<Bus> busesToMatch = newArrayList(buses.get(0));
        Bus busWithHighestId = buses.get(0);
        Function<Long, Long> increment = l -> (l * busWithHighestId.id) - busWithHighestId.desiredWaitTime;
        for (long l = 1; true; l++) {
            long nextTimestamp = increment.apply(l);
            if (allMatchDesiredWaitTime(busesToMatch, nextTimestamp)) {
                if (busesToMatch.size() == buses.size()) {
                    return nextTimestamp;
                }
                long productOfMatchedBusIds = productOfBusIds(busesToMatch);
                increment = l1 -> nextTimestamp + (l1 * productOfMatchedBusIds);
                l = 0;
                busesToMatch.add(buses.get(busesToMatch.size()));
            }
        }
    }

    private static boolean allMatchDesiredWaitTime(List<Bus> buses, long timestamp) {
        return StreamEx.of(buses).allMatch(bus -> bus.matchesDesiredWaitTime(timestamp));
    }

    private static long productOfBusIds(List<Bus> busesToMatch) {
        return StreamEx.of(busesToMatch).mapToLong(Bus::getId).reduce(1, (a, b) -> a * b);
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class Bus {
        @Getter
        private final int id;
        private int desiredWaitTime;

        public long waitTime(long timestamp) {
            return (((long) Math.ceil(((double) timestamp) / id)) * id) - timestamp;
        }

        private boolean matchesDesiredWaitTime(long timestamp) {
            return (timestamp + desiredWaitTime) % id == 0;
        }

        public long part1() {
            return id * waitTime(EARLIEST_DEPARTURE_TIMESTAMP);
        }
    }
}
