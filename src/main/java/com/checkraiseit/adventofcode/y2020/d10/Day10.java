package com.checkraiseit.adventofcode.y2020.d10;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class Day10 {

    private static Map<Integer, Set<Integer>> joltsWithinRangeCache = Maps.newHashMap();
    private static Map<Integer, Long> numberOfPathsFromCache = Maps.newHashMap();
    private static List<Integer> numbers;

    public static void main(String[] args) throws IOException {
        numbers = Resources.readLines(Resources.getResource("y2020/day10.txt"), Charsets.UTF_8).stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());
        part2(numbers);
    }

    private static void part2(List<Integer> ers) {
        log.info(1L + numberOfPathsFrom(0));
    }

    private static long numberOfPathsFrom(int jolt) {
        Long cached = numberOfPathsFromCache.get(jolt);
        if (cached != null) {
            return cached;
        }
        Set<Integer> joltsWithinRange = joltsWithinRange(jolt);
        long numberOfPathsFrom = Math.max(joltsWithinRange.size() - 1, 0) + joltsWithinRange.stream()
                .map(Day10::numberOfPathsFrom)
                .reduce(0L, Long::sum);
        numberOfPathsFromCache.put(jolt, numberOfPathsFrom);
        return numberOfPathsFrom;
    }

    private static Set<Integer> joltsWithinRange(int jolt) {
        return joltsWithinRangeCache.computeIfAbsent(jolt,
                j -> numbers.stream().filter(number -> ((number > j) && (number - j <= 3))).collect(Collectors.toSet()));
    }
}
