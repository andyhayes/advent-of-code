package com.checkraiseit.adventofcode.y2020.d08;

public interface Instruction {
    void execute(BootCode bootCode);

    boolean hasRun();

    int getParam();

    void reset();
}
