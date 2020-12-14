package com.checkraiseit.adventofcode.y2020.d12;

public interface Direction {

    Direction left();

    Direction right();

    Coordinates move(Coordinates location, int value);
}
