package com.checkraiseit.adventofcode.y2020.d22;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@Log4j2
public class Day22 {

    public static void main(String[] args) throws IOException {
        List<Card> player1Cards = StreamEx.of(Resources.readLines(Resources.getResource("y2020/day22-player1.txt"), Charsets.UTF_8))
                .map(Card::new)
                .toList();
        List<Card> player2Cards = StreamEx.of(Resources.readLines(Resources.getResource("y2020/day22-player2.txt"), Charsets.UTF_8))
                .map(Card::new)
                .toList();

        Hand winningHand = new CombatGame(new Hand(player1Cards), new Hand(player2Cards)).play();
        log.info("part 1: {}", winningHand.score());

        GameResult gameResult = new RecursiveCombatGame(new Hand(player1Cards), new Hand(player2Cards)).play();
        log.info("part 2: {}", gameResult.hand.score());
    }

    @ToString
    @EqualsAndHashCode
    static class Card {
        private final int value;

        Card(String value) {
            this.value = Integer.parseInt(value);
        }

        public boolean beats(Card card) {
            return value > card.value;
        }
    }

    @EqualsAndHashCode
    static class Hand {
        private final List<Card> cards;

        public Hand(List<Card> cards) {
            this.cards = newArrayList(cards);
        }

        public Hand clone() {
            return new Hand(cards);
        }

        public Card topCard() {
            return cards.remove(0);
        }

        public void collect(Card card1, Card card2) {
            cards.add(card1);
            cards.add(card2);
        }

        public boolean hasCardsRemaining() {
            return !cards.isEmpty();
        }

        public int cardsRemaining() {
            return cards.size();
        }

        public long score() {
            return EntryStream.of(Lists.reverse(cards)).mapToLong(entry -> (entry.getKey() + 1L) * entry.getValue().value).sum();
        }

        public Hand firstFewCards(int numberOfCards) {
            return new Hand(cards.subList(0, numberOfCards));
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class CombatGame {
        private final Hand player1;
        private final Hand player2;

        public Hand play() {
            while (player1.hasCardsRemaining() && player2.hasCardsRemaining()) {
                Card player1Card = player1.topCard();
                Card player2Card = player2.topCard();
                if (player1Card.beats(player2Card)) {
                    player1.collect(player1Card, player2Card);
                } else {
                    player2.collect(player2Card, player1Card);
                }
            }
            if (player1.hasCardsRemaining()) {
                return player1;
            }
            return player2;
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    static class RecursiveCombatGame {
        private final Hand player1;
        private final Hand player2;

        @EqualsAndHashCode.Exclude
        private final Set<RecursiveCombatGame> previouslySeenGames = newHashSet();

        public RecursiveCombatGame clone() {
            return new RecursiveCombatGame(player1.clone(), player2.clone());
        }

        public GameResult play() {
            while (player1.hasCardsRemaining() && player2.hasCardsRemaining()) {
                if (previouslySeenGames.contains(this)) {
                    return new GameResult(1, player1);
                } else {
                    previouslySeenGames.add(new RecursiveCombatGame(player1.clone(), player2.clone()));
                }

                Card player1Card = player1.topCard();
                Card player2Card = player2.topCard();

                GameResult subGameResult = createSubGame(player1Card, player2Card).play();
                if (subGameResult.winningPlayer == 1) {
                    player1.collect(player1Card, player2Card);
                } else {
                    player2.collect(player2Card, player1Card);
                }
            }
            if (player1.hasCardsRemaining()) {
                return new GameResult(1, player1);
            }
            return new GameResult(2, player2);
        }

        private SubGame createSubGame(Card player1Card, Card player2Card) {
            if (shouldPlayRecursiveSubGame(player1Card, player2Card)) {
                return new RecursiveSubGame(player1.firstFewCards(player1Card.value), player2.firstFewCards(player2Card.value));
            }
            return new CardSubGame(player1Card, player2Card);
        }

        private boolean shouldPlayRecursiveSubGame(Card player1Card, Card player2Card) {
            return player1.cardsRemaining() >= player1Card.value && player2.cardsRemaining() >= player2Card.value;
        }
    }

    interface SubGame {
        GameResult play();
    }

    @RequiredArgsConstructor
    static class RecursiveSubGame implements SubGame {
        private static final Map<RecursiveCombatGame, GameResult> recursiveGameCache = newHashMap();

        private final Hand player1;
        private final Hand player2;

        public GameResult play() {
            RecursiveCombatGame recursiveCombatGame = new RecursiveCombatGame(player1, player2);
            GameResult subGameResult = recursiveGameCache.get(recursiveCombatGame);
            if (subGameResult == null) {
                subGameResult = recursiveCombatGame.clone().play();
            }
            recursiveGameCache.put(recursiveCombatGame, subGameResult);
            return subGameResult;
        }
    }

    @RequiredArgsConstructor
    static class CardSubGame implements SubGame {
        private final Card player1Card;
        private final Card player2Card;

        public GameResult play() {
            if (player1Card.beats(player2Card)) {
                return new GameResult(1, new Hand(newArrayList(player1Card)));
            }
            return new GameResult(2, new Hand(newArrayList(player2Card)));
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    static class GameResult {
        private final int winningPlayer;
        final Hand hand;
    }
}
