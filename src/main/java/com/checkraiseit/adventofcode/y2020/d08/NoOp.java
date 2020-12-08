package com.checkraiseit.adventofcode.y2020.d08;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoOp implements Instruction {
    @Getter
    private final int param;
    private boolean run = false;

    @Override
    public void execute(BootCode bootCode) {
        bootCode.jump(1);
        run = true;
    }

    @Override
    public boolean hasRun() {
        return run;
    }

    @Override
    public void reset() {
        run = false;
    }
}
