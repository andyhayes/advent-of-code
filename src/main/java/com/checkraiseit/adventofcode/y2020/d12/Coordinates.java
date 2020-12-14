package com.checkraiseit.adventofcode.y2020.d12;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Coordinates {

    private final int x;
    private final int y;

    public Coordinates north(int value) {
        return new Coordinates(x, y + value);
    }

    public Coordinates west(int value) {
        return new Coordinates(x - value, y);
    }

    public Coordinates south(int value) {
        return new Coordinates(x, y - value);
    }

    public Coordinates east(int value) {
        return new Coordinates(x + value, y);
    }

    public Coordinates rotateLeftAboutOrigin() {
        return new Coordinates(-y, x);
    }

    public Coordinates rotateRightAboutOrigin() {
        return new Coordinates(y, -x);
    }

    public int manhattanDistanceFromOrigin() {
        return Math.abs(x) + Math.abs(y);
    }

    public Coordinates moveToward(Coordinates value) {
        return new Coordinates(x + value.x, y + value.y);
    }
}
