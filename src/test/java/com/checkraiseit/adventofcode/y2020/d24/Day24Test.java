package com.checkraiseit.adventofcode.y2020.d24;

import com.checkraiseit.adventofcode.y2020.d24.Day24.Floor;
import com.checkraiseit.adventofcode.y2020.d24.Day24.Path;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day24Test {

    @Test
    public void singleStep() {
        Floor floor = new Floor();
        newArrayList(new Path("ne"))
                .forEach(path -> path.flipTileAtEndOfPath(floor));
        assertThat(floor.numberOfBlackTiles(), is(1L));
    }

    @Test
    public void stepBack() {
        Floor floor = new Floor();
        newArrayList(new Path("ne"), new Path("nenesw"))
                .forEach(path -> path.flipTileAtEndOfPath(floor));
        assertThat(floor.numberOfBlackTiles(), is(0L));
    }

    @Test
    public void circularPath() {
        Floor floor = new Floor();
        newArrayList(new Path(""), new Path("neseswnw"))
                .forEach(path -> path.flipTileAtEndOfPath(floor));
        assertThat(floor.numberOfBlackTiles(), is(0L));
    }
}
