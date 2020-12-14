package com.checkraiseit.adventofcode.y2020.d12;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@Log4j2
public class Day12 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource("y2020/day12.txt"), Charsets.UTF_8);
        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        Ferry ferry = new Ferry();
        lines.forEach(line -> {
            char action = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));
            switch (action) {
                case 'N':
                    ferry.moveNorth(value);
                    break;
                case 'S':
                    ferry.moveSouth(value);
                    break;
                case 'E':
                    ferry.moveEast(value);
                    break;
                case 'W':
                    ferry.moveWest(value);
                    break;
                case 'L':
                    repeatEveryNinetyDegrees(value, ferry::turnLeft);
                    break;
                case 'R':
                    repeatEveryNinetyDegrees(value, ferry::turnRight);
                    break;
                case 'F':
                    ferry.moveForward(value);
                    break;
                default:
                    throw new IllegalStateException("unexpected action: " + action);
            }
        });
        log.info(ferry.currentLocation().manhattanDistanceFromOrigin());
    }

    private static void part2(List<String> lines) {
        Ferry ferry = new Ferry();
        lines.forEach(line -> {
            char action = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));
            switch (action) {
                case 'N':
                    ferry.moveWaypointNorth(value);
                    break;
                case 'S':
                    ferry.moveWaypointSouth(value);
                    break;
                case 'E':
                    ferry.moveWaypointEast(value);
                    break;
                case 'W':
                    ferry.moveWaypointWest(value);
                    break;
                case 'L':
                    repeatEveryNinetyDegrees(value, ferry::rotateWaypointLeft);
                    break;
                case 'R':
                    repeatEveryNinetyDegrees(value, ferry::rotateWaypointRight);
                    break;
                case 'F':
                    ferry.moveTowardWaypoint(value);
                    break;
                default:
                    throw new IllegalStateException("unexpected action: " + action);
            }
        });
        log.info(ferry.currentLocation().manhattanDistanceFromOrigin());
    }

    private static void repeatEveryNinetyDegrees(int value, Runnable action) {
        IntStream.rangeClosed(1, 3).limit(value / 90).forEach(i -> action.run());
    }
}
