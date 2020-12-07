package com.checkraiseit.adventofcode.y2020.d05;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardingPassTest {

    @Test
    public void test() {
        assertThat(new BoardingPass("BFFFBBFRRR").toSeatId(), is(567));
    }
}
