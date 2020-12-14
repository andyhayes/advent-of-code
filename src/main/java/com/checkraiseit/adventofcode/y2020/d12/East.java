package com.checkraiseit.adventofcode.y2020.d12;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class East implements Direction {

    @Override
    public Direction left() {
        return new North();
    }

    @Override
    public Direction right() {
        return new South();
    }

    @Override
    public Coordinates move(Coordinates location, int value) {
        return location.east(value);
    }
}
