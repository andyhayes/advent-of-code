package com.checkraiseit.adventofcode.y2020.d20;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.io.Resources;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

@Log4j2
public class Day20 {

    public static void main(String[] args) throws IOException {
        List<Tile> tiles = parseTiles();
        StreamEx<Tile> cornerTiles = StreamEx.of(tiles).filter(tile -> {
            Set<String> allOtherEdges = StreamEx.of(tiles).filter(t -> tile.tileId != t.tileId).flatCollection(t -> t.allPossibleEdges).toSet();
            SetView<String> intersection = Sets.intersection(tile.allPossibleEdges, allOtherEdges);
            return intersection.size() == 4; // two unmatched edges
        });
        long productOfTileIds = cornerTiles.mapToLong(tile -> tile.tileId).reduce(1, (a, b) -> a * b);
        log.info("part 1: {}", productOfTileIds);
    }

    private static List<Tile> parseTiles() throws IOException {
        List<Tile> tiles = newArrayList();
        List<String> tileDefinitions = Resources.readLines(Resources.getResource("y2020/day20.txt"), Charsets.UTF_8);
        int tileId = -1;
        List<String> tileLines = newArrayList();
        for (String tileDefinition : tileDefinitions) {
            if (tileDefinition.isBlank()) {
                tiles.add(new Tile(tileId, tileLines));
                tileLines = newArrayList();
                continue;
            }
            if (tileDefinition.startsWith("Tile")) {
                tileId = Integer.parseInt(tileDefinition.substring(5, 9));
                continue;
            }
            tileLines.add(tileDefinition);
        }
        return tiles;
    }

    @ToString
    @EqualsAndHashCode
    private static class Tile {
        private final int tileId;
        private final List<String> tileLines;
        private final Set<String> allPossibleEdges;

        private Tile(int tileId, List<String> tileLines) {
            this.tileId = tileId;
            this.tileLines = tileLines;
            this.allPossibleEdges = allPossibleEdges();
        }

        private Set<String> allPossibleEdges() {
            String top = topEdge();
            String bottom = bottomEdge();
            String left = leftEdge();
            String right = rightEdge();
            return newHashSet(top, reverse(top), bottom, reverse(bottom), left, reverse(left), right, reverse(right));
        }

        private String topEdge() {
            return tileLines.get(0);
        }

        private String bottomEdge() {
            return tileLines.get(tileLines.size() - 1);
        }

        private String leftEdge() {
            return StreamEx.of(tileLines).map(line -> line.charAt(0)).joining();
        }

        private String rightEdge() {
            return StreamEx.of(tileLines).map(line -> line.charAt(line.length() - 1)).joining();
        }

        private String reverse(String str) {
            return new StringBuilder(str).reverse().toString();
        }
    }
}
