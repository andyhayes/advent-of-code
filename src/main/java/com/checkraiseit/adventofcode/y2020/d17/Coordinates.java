package com.checkraiseit.adventofcode.y2020.d17;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Coordinates {
    private final int x;
    private final int y;
    private final int z;
    private final int w;

    public Set<Coordinates> neighbourhood() {
        HashSet<Coordinates> neighbourhood = newHashSet();
        for (int x1 = x - 1; x1 <= x + 1; x1++) {
            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                for (int z1 = z - 1; z1 <= z + 1; z1++) {
                    neighbourhood.add(new Coordinates(x1, y1, z1, w));
                }
            }
        }
        return neighbourhood;
    }

    public Set<Coordinates> neighbourhood4d() {
        HashSet<Coordinates> neighbourhood = newHashSet();
        for (int x1 = x - 1; x1 <= x + 1; x1++) {
            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                for (int z1 = z - 1; z1 <= z + 1; z1++) {
                    for (int w1 = w - 1; w1 <= w + 1; w1++) {
                        neighbourhood.add(new Coordinates(x1, y1, z1, w1));
                    }
                }
            }
        }
        return neighbourhood;
    }
}
