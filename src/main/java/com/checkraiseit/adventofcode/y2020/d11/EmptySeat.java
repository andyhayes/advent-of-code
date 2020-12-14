package com.checkraiseit.adventofcode.y2020.d11;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class EmptySeat implements WaitingAreaSpace {
    private final int occupiedNeighboursRequiredToVacate;

    @Override
    public WaitingAreaSpace next(int occupiedNeighbours) {
        if (occupiedNeighbours == 0) {
            return new OccupiedSeat(occupiedNeighboursRequiredToVacate);
        }
        return this;
    }

    @Override
    public String toString() {
        return "L";
    }
}
