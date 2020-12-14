package com.checkraiseit.adventofcode.y2020.d11;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class OccupiedSeat implements WaitingAreaSpace {
    private final int occupiedNeighboursRequiredToVacate;

    @Override
    public WaitingAreaSpace next(int occupiedNeighbours) {
        if (occupiedNeighbours >= occupiedNeighboursRequiredToVacate) {
            return new EmptySeat(occupiedNeighboursRequiredToVacate);
        }
        return this;
    }

    @Override
    public String toString() {
        return "#";
    }
}
