package com.checkraiseit.adventofcode.y2020.d01;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class Day01 {

    public static void main(String[] args) throws IOException {
        Set<Integer> entries = Resources.readLines(Resources.getResource("y2020/day01.txt"), Charsets.UTF_8)
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        Integer product = Sets.combinations(entries, 3).stream()
                .filter(combination -> combination.stream().reduce(0, Integer::sum) == 2020)
                .findFirst()
                .map(combination -> combination.stream().reduce(1, (a, b) -> a * b))
                .orElseThrow(() -> new IllegalStateException("no combination found"));
        System.out.println(product);
    }
}
