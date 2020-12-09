package com.checkraiseit.adventofcode.y2020.d09;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.google.common.collect.Sets.newHashSet;

@Log4j2
public class Day09 {

    public static void main(String[] args) throws IOException {
        List<Long> numbers = Resources.readLines(Resources.getResource("y2020/day09.txt"), Charsets.UTF_8).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
        part1(numbers);
        part2(numbers);
    }

    private static void part1(List<Long> numbers) {
        int preamble = 25;
        for (int i = preamble; i < numbers.size(); i++) {
            Long currentNumber = numbers.get(i);
            Set<Long> sumOfAllPairsOfPrevious25Numbers = sumOfAllPairsOfPreviousNumbers(numbers, preamble, i);
            if (!sumOfAllPairsOfPrevious25Numbers.contains(currentNumber)) {
                log.info(currentNumber);
                return;
            }
        }
    }

    private static void part2(List<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                List<Long> subList = numbers.subList(i, j);
                Long sum = subList.stream().reduce(0L, Long::sum);
                if (1504371145L == sum) {
                    List<Long> sortedSubList = subList.stream().sorted().collect(Collectors.toList());
                    log.info(sortedSubList.get(0) + sortedSubList.get(sortedSubList.size() - 1));
                    return;
                }
            }
        }
    }

    private static Set<Long> sumOfAllPairsOfPreviousNumbers(List<Long> numbers, int n, int i) {
        return Sets.combinations(newHashSet(numbers.subList(i - n, i)), 2).stream()
                .map(set -> set.stream().reduce(0L, Long::sum))
                .collect(Collectors.toSet());
    }

}
