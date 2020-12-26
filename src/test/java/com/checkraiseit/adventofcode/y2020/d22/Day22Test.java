package com.checkraiseit.adventofcode.y2020.d22;

import com.checkraiseit.adventofcode.y2020.d22.Day22.Card;
import com.checkraiseit.adventofcode.y2020.d22.Day22.GameResult;
import com.checkraiseit.adventofcode.y2020.d22.Day22.Hand;
import com.checkraiseit.adventofcode.y2020.d22.Day22.RecursiveCombatGame;
import one.util.streamex.StreamEx;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Day22Test {

    @Test
    public void part2Example() {
        Hand player1 = new Hand(StreamEx.of("9", "2", "6", "3", "1").map(Card::new).toList());
        Hand player2 = new Hand(StreamEx.of("5", "8", "4", "7", "10").map(Card::new).toList());
        GameResult gameResult = new RecursiveCombatGame(player1, player2).play();
        assertThat(gameResult.hand.score(), is(291L));
    }
}
