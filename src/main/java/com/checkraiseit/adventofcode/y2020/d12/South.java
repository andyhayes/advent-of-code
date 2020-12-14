package com.checkraiseit.adventofcode.y2020.d12;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class South implements Direction {

    @Override
    public Direction left() {
        return new East();
    }

    @Override
    public Direction right() {
        return new West();
    }

    @Override
    public Coordinates move(Coordinates location, int value) {
        return location.south(value);
    }
}
