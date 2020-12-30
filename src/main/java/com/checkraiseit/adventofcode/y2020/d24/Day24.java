package com.checkraiseit.adventofcode.y2020.d24;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

@Log4j2
public class Day24 {

    public static void main(String[] args) throws IOException {
        Floor floor = new Floor();
        StreamEx.of(Resources.readLines(Resources.getResource("y2020/day24.txt"), Charsets.UTF_8))
                .map(Path::new)
                .forEach(path -> path.flipTileAtEndOfPath(floor));
        log.info("part 1: {}", floor.numberOfBlackTiles());
        IntStreamEx.range(100).forEach(i -> floor.nextDay());
        log.info("part 2: {}", floor.numberOfBlackTiles());
    }

    static class Floor {
        private Map<Coordinates, Tile> tiles = newHashMap();

        public void flipTileAt(Coordinates coordinates) {
            tiles.computeIfAbsent(coordinates, c -> new Tile()).flip();
        }

        public long numberOfBlackTiles() {
            return StreamEx.of(tiles.values()).filter(Tile::isBlack).count();
        }

        public void nextDay() {
            addNeighboursForBlackTiles();
            Map<Coordinates, Tile> newTiles = newHashMap(tiles);
            EntryStream.of(tiles)
                    .forEach(entry -> {
                        Coordinates coordinates = entry.getKey();
                        Tile tile = entry.getValue();
                        long blackTiles = StreamEx.of(coordinates.neighbours()).map(tiles::get).nonNull().filter(Tile::isBlack).count();
                        if ((tile.isBlack() && (blackTiles == 0 || blackTiles > 2)) ||
                                (!tile.isBlack() && blackTiles == 2)) {
                            newTiles.put(coordinates, tile.flipped());
                        }
                    });
            tiles = newTiles;
        }

        private void addNeighboursForBlackTiles() {
            Set<Coordinates> neighboursOfBlackTiles = EntryStream.of(tiles)
                    .filter(entry -> entry.getValue().isBlack())
                    .map(Map.Entry::getKey)
                    .flatCollection(Coordinates::neighbours)
                    .toSet();
            neighboursOfBlackTiles.forEach(coordinates -> tiles.computeIfAbsent(coordinates, c -> new Tile()));
        }
    }

    @ToString
    static class Path {
        private final List<Direction> directions = newArrayList();

        Path(String steps) {
            for (int i = 0; i < steps.length(); i++) {
                char ch = steps.charAt(i);
                switch (ch) {
                    case 'e':
                        directions.add(new East());
                        continue;
                    case 's':
                        if (steps.charAt(++i) == 'e') {
                            directions.add(new SouthEast());
                        } else {
                            directions.add(new SouthWest());
                        }
                        continue;
                    case 'w':
                        directions.add(new West());
                        continue;
                    case 'n':
                        if (steps.charAt(++i) == 'e') {
                            directions.add(new NorthEast());
                        } else {
                            directions.add(new NorthWest());
                        }
                }
            }
        }

        public void flipTileAtEndOfPath(Floor floor) {
            Coordinates coordinates = new Coordinates(0, 0);
            for (Direction direction : directions) {
                coordinates = direction.next(coordinates);
            }
            floor.flipTileAt(coordinates);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    static class Tile {
        private boolean black = false;

        public void flip() {
            black = !black;
        }

        public Tile flipped() {
            return new Tile(!black);
        }

        public boolean isBlack() {
            return black;
        }
    }

    private interface Direction {
        Coordinates next(Coordinates coords);
    }

    @ToString
    private static class East implements Direction {
        @Override
        public Coordinates next(Coordinates coords) {
            return coords.east();
        }
    }

    @ToString
    private static class SouthEast implements Direction {
        @Override
        public Coordinates next(Coordinates coords) {
            return coords.southEast();
        }
    }

    @ToString
    private static class SouthWest implements Direction {
        @Override
        public Coordinates next(Coordinates coords) {
            return coords.southWest();
        }
    }

    @ToString
    private static class West implements Direction {
        @Override
        public Coordinates next(Coordinates coords) {
            return coords.west();
        }
    }

    @ToString
    private static class NorthWest implements Direction {
        @Override
        public Coordinates next(Coordinates coords) {
            return coords.northWest();
        }
    }

    @ToString
    private static class NorthEast implements Direction {
        @Override
        public Coordinates next(Coordinates coords) {
            return coords.northEast();
        }
    }
}
