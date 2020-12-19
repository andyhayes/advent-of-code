package com.checkraiseit.adventofcode.y2020.d17;

import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.google.common.collect.Maps.newHashMap;

@RequiredArgsConstructor
public class PocketDimension {
    private final Map<Coordinates, Cube> cubes;

    public static PocketDimension ofLists(List<List<Cube>> cubeLists) {
        Map<Coordinates, Cube> cubes = newHashMap();
        for (int x = 0; x < cubeLists.size(); x++) {
            List<Cube> cubeLine = cubeLists.get(x);
            for (int y = 0; y < cubeLine.size(); y++) {
                Cube cube = cubeLine.get(y);
                cubes.put(new Coordinates(x, y, 0, 0), cube);
            }
        }
        return new PocketDimension(cubes);
    }

    PocketDimension tick() {
        return tick(Coordinates::neighbourhood);
    }

    PocketDimension tick4d() {
        return tick(Coordinates::neighbourhood4d);
    }

    PocketDimension tick(Function<Coordinates, Set<Coordinates>> determineNeighbourhood) {
        Map<Coordinates, Cube> newCubes = newHashMap();
        for (Coordinates coordinates : cubes.keySet()) {
            Set<Coordinates> neighbourhood = determineNeighbourhood.apply(coordinates);
            neighbourhood.forEach(coords -> {
                Cube next = cubes.getOrDefault(coords, new Inactive()).next(numberOfActiveNeighbours(coords, determineNeighbourhood));
                newCubes.put(coords, next);
            });
        }
        return new PocketDimension(newCubes);
    }

    private int numberOfActiveNeighbours(Coordinates coordinates, Function<Coordinates, Set<Coordinates>> determineNeighbourhood) {
        Map<Coordinates, Cube> neighbouringCubes = StreamEx.of(determineNeighbourhood.apply(coordinates))
                .filter(coords -> !coords.equals(coordinates))
                .toMap(coords -> cubes.getOrDefault(coords, new Inactive()));
        return (int) StreamEx.of(neighbouringCubes.values())
                .filter(cube -> cube instanceof Active)
                .count();
    }

    public long numberOfActiveCubes() {
        return StreamEx.of(cubes.values()).filter(cube -> cube instanceof Active).count();
    }

    @Override
    public String toString() {
        IntSummaryStatistics zSummary = StreamEx.of(cubes.keySet()).mapToInt(Coordinates::getZ).summaryStatistics();
        int minZ = zSummary.getMin();
        int maxZ = zSummary.getMax();
        IntSummaryStatistics xSummary = StreamEx.of(cubes.keySet()).mapToInt(Coordinates::getX).summaryStatistics();
        int minX = xSummary.getMin();
        int maxX = xSummary.getMax();
        IntSummaryStatistics ySummary = StreamEx.of(cubes.keySet()).mapToInt(Coordinates::getY).summaryStatistics();
        int minY = ySummary.getMin();
        int maxY = ySummary.getMax();
        StringBuilder builder = new StringBuilder("PocketDimension: minX=").append(minX).append(", maxX=").append(maxX)
                .append(", minY=").append(minY).append(", maxY=").append(maxY).append('\n');
        for (int z = minZ; z <= maxZ; z++) {
            builder.append("z=").append(z).append('\n');
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    builder.append(cubes.getOrDefault(new Coordinates(x, y, z, 0), new Inactive()));
                }
                builder.append('\n');
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
