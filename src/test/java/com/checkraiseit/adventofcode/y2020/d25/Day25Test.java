package com.checkraiseit.adventofcode.y2020.d25;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day25Test {

    @Test
    public void transform() {
        assertThat(Day25.transform(17807724L, 8), is(14897079L));
        assertThat(Day25.transform(5764801L, 11), is(14897079L));
    }

    @Test
    public void calculateLoopSize() {
        assertThat(Day25.calculateLoopSize(17807724L), is(11));
        assertThat(Day25.calculateLoopSize(5764801L), is(8));
    }

    @Test
    public void calculateEncryptionKey() {
        assertThat(Day25.calculateEncryptionKey(17807724L, 5764801L), is(14897079L));
    }
}