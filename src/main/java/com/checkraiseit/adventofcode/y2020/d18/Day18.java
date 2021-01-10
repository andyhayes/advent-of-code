package com.checkraiseit.adventofcode.y2020.d18;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;

@Log4j2
public class Day18 {

    private static final ExpressionFactory expressionFactory = new ExpressionFactory();

    public static void main(String[] args) throws IOException {
        List<String> expressions = Resources.readLines(Resources.getResource("y2020/day18.txt"), Charsets.UTF_8);
        log.info("part1: {}", StreamEx.of(expressions).mapToLong(Day18::evaluate).sum());
        log.info("part2: {}", StreamEx.of(expressions).mapToLong(Day18::evaluatePart2).sum());
    }

    static long evaluate(String expression) {
        return expressionFactory.parse(expression).evaluate();
    }

    static long evaluatePart2(String expression) {
        return expressionFactory.parse(expression).withAdditionGivenPrecedence().evaluate();
    }
}
