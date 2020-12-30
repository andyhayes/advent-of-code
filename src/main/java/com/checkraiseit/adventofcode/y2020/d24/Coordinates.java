package com.checkraiseit.adventofcode.y2020.d24;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Coordinates {

    private final int x;
    private final int y;

    public Coordinates east() {
        return new Coordinates(x + 2, y);
    }

    public Coordinates northEast() {
        return new Coordinates(x + 1, y + 1);
    }

    public Coordinates northWest() {
        return new Coordinates(x - 1, y + 1);
    }

    public Coordinates west() {
        return new Coordinates(x - 2, y);
    }

    public Coordinates southWest() {
        return new Coordinates(x - 1, y - 1);
    }

    public Coordinates southEast() {
        return new Coordinates(x + 1, y - 1);
    }

    public Set<Coordinates> neighbours() {
        return Set.of(east(), northEast(), northWest(), west(), southWest(), southEast());
    }
}
