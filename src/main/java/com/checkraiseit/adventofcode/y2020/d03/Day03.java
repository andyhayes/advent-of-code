package com.checkraiseit.adventofcode.y2020.d03;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Day03 {

    public static void main(String[] args) throws IOException {
        AtomicInteger x = new AtomicInteger(-1);
        AtomicInteger y = new AtomicInteger(0);
        AtomicInteger numberOfTrees = new AtomicInteger(0);
        Resources.readLines(Resources.getResource("y2020/day03.txt"), Charsets.UTF_8).stream()
            .forEach(row -> {
                x.incrementAndGet();
                if (x.get() % 2 == 1) {
                    return;
                }
                int oldY = y.get();
                if (row.charAt(oldY) == '#') {
                    numberOfTrees.incrementAndGet();
                }
                int newY = (oldY + 1) % row.length();
                y.set(newY);
            });
        System.out.println(numberOfTrees);
    }
}
