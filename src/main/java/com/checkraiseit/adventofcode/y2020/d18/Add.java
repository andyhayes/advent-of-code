package com.checkraiseit.adventofcode.y2020.d18;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class Add implements Operator {
    @Override
    public long evaluate(long left, long right) {
        return left + right;
    }

    @Override
    public String toString() {
        return "+";
    }
}
