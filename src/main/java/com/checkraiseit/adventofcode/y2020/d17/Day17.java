package com.checkraiseit.adventofcode.y2020.d17;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class Day17 {

    public static void main(String[] args) throws IOException {
        List<List<Cube>> cubes = Resources.readLines(Resources.getResource("y2020/day17.txt"), Charsets.UTF_8).stream()
                .map(line -> Arrays.asList(line.split("")))
                .map(chars -> chars.stream().map(ch -> {
                    if (".".equals(ch)) {
                        return new Inactive();
                    }
                    return new Active();
                }).collect(Collectors.toList()))
                .collect(Collectors.toList());
        runSixCycles(cubes, PocketDimension::tick);
        runSixCycles(cubes, PocketDimension::tick4d);
    }

    private static void runSixCycles(List<List<Cube>> cubes, Function<PocketDimension, PocketDimension> tickFunction) {
        PocketDimension pocketDimension = PocketDimension.ofLists(cubes);
        for (int i = 0; i < 6; i++) {
            pocketDimension = tickFunction.apply(pocketDimension);
        }
        log.info(pocketDimension.numberOfActiveCubes());
    }
}
