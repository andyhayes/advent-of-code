package com.checkraiseit.adventofcode.y2020.d11;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Floor implements WaitingAreaSpace {

    @Override
    public WaitingAreaSpace next(int occupiedNeighbours) {
        return this;
    }

    @Override
    public String toString() {
        return ".";
    }
}
