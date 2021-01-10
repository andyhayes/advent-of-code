package com.checkraiseit.adventofcode.y2020.d18;

interface Expression {

    long evaluate();

    Expression withAdditionGivenPrecedence();
}
