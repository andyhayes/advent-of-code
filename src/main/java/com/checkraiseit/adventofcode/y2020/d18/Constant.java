package com.checkraiseit.adventofcode.y2020.d18;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
class Constant implements Expression {

    private final long value;

    public long evaluate() {
        return value;
    }

    @Override
    public Expression withAdditionGivenPrecedence() {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
