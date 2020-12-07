package com.checkraiseit.adventofcode.y2020.d05;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day05 {

    public static void main(String[] args) throws IOException {
        Set<BoardingPass> boardingPasses = Resources.readLines(Resources.getResource("y2020/day05.txt"), Charsets.UTF_8).stream()
                .map(BoardingPass::new)
                .collect(Collectors.toSet());
        int maxSeatId = boardingPasses.stream()
                .mapToInt(BoardingPass::toSeatId)
                .max()
                .orElse(-1);
        System.out.println(maxSeatId);

        List<Integer> sortedSeatIds = boardingPasses.stream()
                .map(BoardingPass::toSeatId)
                .sorted()
                .collect(Collectors.toList());
        int previousSeatId = 0;
        for (Integer seatId : sortedSeatIds) {
            if (seatId - previousSeatId == 2) {
                System.out.println(previousSeatId + 1);
            }
            previousSeatId = seatId;
        }
    }
}
