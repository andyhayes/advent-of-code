package com.checkraiseit.adventofcode.y2020.d18;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day18Test {

    @Test
    public void simpleAddition() {
        assertThat(Day18.evaluate("1 + 2"), is(3L));
    }

    @Test
    public void simpleMultiplication() {
        assertThat(Day18.evaluate("2 * 3"), is(6L));
    }

    @Test
    public void multipleAddition() {
        assertThat(Day18.evaluate("1 + 2 + 3"), is(6L));
    }

    @Test
    public void additionThenMultiplication() {
        assertThat(Day18.evaluate("1 + 2 * 3"), is(9L));
    }

    @Test
    public void multipleAdditionsAndMultiplications() {
        assertThat(Day18.evaluate("1 + 2 * 3 + 4 * 5 + 6"), is(71L));
    }

    @Test
    public void simpleBrackets() {
        assertThat(Day18.evaluate("(2 * 3)"), is(6L));
    }

    @Test
    public void brackets() {
        assertThat(Day18.evaluate("2 * 3 + (4 * 5)"), is(26L));
    }

    @Test
    public void longBrackets() {
        assertThat(Day18.evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)"), is(437L));
    }

    @Test
    public void multiBrackets() {
        assertThat(Day18.evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), is(12240L));
    }

    @Test
    public void crazyBrackets1() {
        assertThat(Day18.evaluate("1 + (2 * 3) + (4 * (5 + 6))"), is(51L));
    }

    @Test
    public void crazyBrackets2() {
        assertThat(Day18.evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), is(13632L));
    }

    @Test
    public void crazyBrackets3() {
        assertThat(Day18.evaluate("9 * 4 + 2 * 7 * (5 * (4 * 5 * 6) * 3 * 4 + 4) * (4 + 4)"), is(15330112L));
    }
}
