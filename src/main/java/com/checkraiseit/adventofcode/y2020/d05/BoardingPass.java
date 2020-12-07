package com.checkraiseit.adventofcode.y2020.d05;

public class BoardingPass {

    private String binarySpacePartition;

    public BoardingPass(String binarySpacePartition) {
        this.binarySpacePartition = binarySpacePartition;
    }

    public int toSeatId() {
        int row = Integer.parseInt(binarySpacePartition.substring(0, 7)
                .replaceAll("F", "0")
                .replaceAll("B", "1"), 2);
        int column = Integer.parseInt(binarySpacePartition.substring(7)
                .replaceAll("L", "0")
                .replaceAll("R", "1"), 2);
        return (row * 8) + column;
    }
}
