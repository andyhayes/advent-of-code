package com.checkraiseit.adventofcode.y2020.d08;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BootCode {
    @Getter
    private int accumulator = 0;
    private int instructionPointer = 0;
    private final List<Instruction> instructions;

    public boolean execute() {
        instructions.forEach(Instruction::reset);
        while (instructionPointer < instructions.size()) {
            Instruction instruction = instructions.get(instructionPointer);
            if (instruction.hasRun()) {
                return false;
            }
            instruction.execute(this);
        }
        return true;
    }

    public void accumulate(int param) {
        accumulator += param;
    }

    public void jump(int param) {
        instructionPointer += param;
    }
}
