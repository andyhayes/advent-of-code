package com.checkraiseit.adventofcode.y2020.d17;

public class Active implements Cube {

    @Override
    public Cube next(int activeNeighbours) {
        if (activeNeighbours == 2 || activeNeighbours == 3) {
            return this;
        }
        return new Inactive();
    }

    @Override
    public String toString() {
        return "#";
    }
}
