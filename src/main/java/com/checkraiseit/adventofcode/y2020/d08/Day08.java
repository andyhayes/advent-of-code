package com.checkraiseit.adventofcode.y2020.d08;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newHashMap;

@Log4j2
public class Day08 {

    public static void main(String[] args) throws IOException {
        List<Instruction> instructions = Resources.readLines(Resources.getResource("y2020/day08.txt"), Charsets.UTF_8).stream()
                .map(line -> {
                    String[] split = line.split(" ");
                    String operation = split[0];
                    int param = Integer.parseInt(split[1]);
                    if ("acc".equals(operation)) {
                        return new Accumulate(param);
                    } else if ("jmp".equals(operation)) {
                        return new Jump(param);
                    }
                    return new NoOp(param);
                })
                .collect(Collectors.toList());
        part1(instructions);
        part2(instructions);
    }

    private static void part1(List<Instruction> instructions) {
        BootCode bootCode = new BootCode(instructions);
        bootCode.execute();
        log.info(bootCode.getAccumulator());
    }

    private static void part2(List<Instruction> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            if (instruction instanceof Jump) {
                List<Instruction> updatedInstructions = new ArrayList<>(instructions);
                updatedInstructions.set(i, new NoOp(instruction.getParam()));
                if (executeInstructions(updatedInstructions)) return;
            } else if (instruction instanceof NoOp) {
                List<Instruction> updatedInstructions = new ArrayList<>(instructions);
                updatedInstructions.set(i, new Jump(instruction.getParam()));
                if (executeInstructions(updatedInstructions)) return;
            }
        }
    }

    private static boolean executeInstructions(List<Instruction> instructions) {
        BootCode bootCode = new BootCode(instructions);
        if (bootCode.execute()) {
            log.info(bootCode.getAccumulator());
            return true;
        }
        return false;
    }
}
