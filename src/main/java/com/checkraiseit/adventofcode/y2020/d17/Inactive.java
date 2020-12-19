package com.checkraiseit.adventofcode.y2020.d17;

public class Inactive implements Cube {

    @Override
    public Cube next(int activeNeighbours) {
        if (activeNeighbours == 3) {
            return new Active();
        }
        return this;
    }

    @Override
    public String toString() {
        return ".";
    }
}
