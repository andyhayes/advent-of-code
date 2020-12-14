package com.checkraiseit.adventofcode.y2020.d11;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class Day11 {

    public static void main(String[] args) throws IOException {
        part1(parseWaitingAreaWithNumberOfOccupiedNeighboursRequiredToVacateAnOccupiedSeat(4));
        part2(parseWaitingAreaWithNumberOfOccupiedNeighboursRequiredToVacateAnOccupiedSeat(5));
    }

    private static WaitingArea parseWaitingAreaWithNumberOfOccupiedNeighboursRequiredToVacateAnOccupiedSeat(int occupiedNeighboursRequiredToVacate) throws IOException {
        List<List<WaitingAreaSpace>> waitingAreaLists = Resources.readLines(Resources.getResource("y2020/day11.txt"), Charsets.UTF_8).stream()
                .map(line -> Arrays.asList(line.split("")))
                .map(chars -> chars.stream().map(ch -> {
                    if ("L".equals(ch)) {
                        return new EmptySeat(occupiedNeighboursRequiredToVacate);
                    } else if (".".equals(ch)) {
                        return new Floor();
                    }
                    return new OccupiedSeat(occupiedNeighboursRequiredToVacate);
                }).collect(Collectors.toList()))
                .collect(Collectors.toList());
        return new WaitingArea(waitingAreaLists);
    }

    private static void part1(WaitingArea waitingArea) {
        findNumberOfOccupiedSeatsAtEquilibrium(waitingArea, WaitingArea::tickWithOccupiedNeighbours);
    }

    private static void part2(WaitingArea waitingArea) {
        findNumberOfOccupiedSeatsAtEquilibrium(waitingArea, WaitingArea::tickWithVisiblyOccupiedNeighbours);
    }

    private static void findNumberOfOccupiedSeatsAtEquilibrium(WaitingArea waitingArea, Function<WaitingArea, WaitingArea> tick) {
        while (true) {
            WaitingArea newWaitingArea = tick.apply(waitingArea);
            if (waitingArea.equals(newWaitingArea)) {
                break;
            }
            waitingArea = newWaitingArea;
        }
        log.info(waitingArea.numberOfOccupiedSeats());
    }
}
