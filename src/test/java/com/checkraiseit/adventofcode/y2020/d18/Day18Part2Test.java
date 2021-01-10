package com.checkraiseit.adventofcode.y2020.d18;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day18Part2Test {

    private final ExpressionFactory expressionFactory = new ExpressionFactory();

    @Test
    public void part2() {
        assertThat(Day18.evaluatePart2("1 + (2 * 3) + (4 * (5 + 6))"), is(51L));
        assertThat(Day18.evaluatePart2("2 * 3 + (4 * 5)"), is(46L));
        assertThat(Day18.evaluatePart2("5 + (8 * 3 + 9 + 3 * 4 * 3)"), is(1445L));
        assertThat(Day18.evaluatePart2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), is(669060L));
        assertThat(Day18.evaluatePart2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), is(23340L));
    }

    @Test
    public void expressionToString() {
        assertThat(expressionFactory.parse("1 + (2 * 3) + (4 * (5 + 6))").toString(), is("1 + (2 * 3) + (4 * (5 + 6))"));
        assertThat(expressionFactory.parse("2 * 3 + (4 * 5)").toString(), is("2 * 3 + (4 * 5)"));
    }

    @Test
    public void additionOnLeftGivenPrecedence() {
        assertThat(expressionFactory.parse("1 + 2 * 3").withAdditionGivenPrecedence(), is(expressionFactory.parse("(1 + 2) * 3")));
    }

    @Test
    public void additionOnRightGivenPrecedence() {
        assertThat(expressionFactory.parse("1 * 2 + 3").withAdditionGivenPrecedence(), is(expressionFactory.parse("1 * (2 + 3)")));
        assertThat(expressionFactory.parse("2 * 3 + (4 * 5)").withAdditionGivenPrecedence(), is(expressionFactory.parse("2 * (3 + (4 * 5))")));
    }

    @Test
    public void bracketedAdditionGivenPrecedence() {
        assertThat(expressionFactory.parse("2 * 3 + (4 * 5)").withAdditionGivenPrecedence(), is(expressionFactory.parse("2 * (3 + (4 * 5))")));
    }

    @Test
    public void additionInMiddleGivenPrecedence() {
        assertThat(expressionFactory.parse("2 * 3 + 4 * 5").withAdditionGivenPrecedence(), is(expressionFactory.parse("2 * (3 + 4) * 5")));
    }

    @Test
    public void complicatedAdditionGivenPrecedence() {
        assertThat(expressionFactory.parse("1 + (2 * 3) + (4 * (5 + 6))").withAdditionGivenPrecedence(), is(expressionFactory.parse("((1 + (2 * 3)) + (4 * (5 + 6)))")));
    }

    @Test
    public void multipleAdditionGivenPrecedence() {
        assertThat(expressionFactory.parse("5 + (8 * 3 + 9 + 3 * 4 * 3)").withAdditionGivenPrecedence(), is(expressionFactory.parse("(5 + (8 * ((3 + 9) + 3) * 4 * 3))")));
    }

    @Test
    public void part2WithAddedBrackets() {
        assertThat(Day18.evaluatePart2("((1 + (2 * 3)) + (4 * (5 + 6)))"), is(51L));
        assertThat(Day18.evaluatePart2("2 * (3 + (4 * 5))"), is(46L));
        assertThat(Day18.evaluatePart2("(5 + (8 * ((3 + 9) + 3) * 4 * 3))"), is(1445L));
        assertThat(Day18.evaluatePart2("5 * 9 * (7 * 3 * (3 + 9) * (3 + ((8 + 6) * 4)))"), is(669060L));
        assertThat(Day18.evaluatePart2("(((((2 + 4) * 9) * (((6 + 9) * (8 + 6)) + 6)) + 2) + 4) * 2"), is(23340L));
    }
}
