package com.checkraiseit.adventofcode.y2020.d23;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day23Test {

    @Test
    public void part1Example() {
        assertThat(Day23.labelAfterMoves("389125467", 10), is("92658374"));
    }

    @Test
    public void part1() {
        assertThat(Day23.labelAfterMoves("219347865", 100), is("36472598"));
    }

    @Test
    public void part2Example() {
        assertThat(Day23.labelAfterTenMillionMoves("389125467"), is(149245887792L));
    }
}
