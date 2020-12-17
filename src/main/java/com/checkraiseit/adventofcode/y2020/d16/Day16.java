package com.checkraiseit.adventofcode.y2020.d16;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Maps.newHashMap;

@Log4j2
public class Day16 {

    public static void main(String[] args) throws IOException {
        List<String> ruleDefinitions = Resources.readLines(Resources.getResource("y2020/day16-rules.txt"), Charsets.UTF_8);
        List<Rule> rules = StreamEx.of(ruleDefinitions).map(Rule::new).toList();
        log.info(rules);

        List<String> nearbyTicketLines = Resources.readLines(Resources.getResource("y2020/day16-nearby-tickets.txt"), Charsets.UTF_8);
        List<Ticket> nearbyTickets = StreamEx.of(nearbyTicketLines)
                .map(Ticket::new)
                .toList();

        part1(rules, nearbyTickets);
    }

    private static void part1(List<Rule> rules, List<Ticket> nearbyTickets) {
        int ticketScanningErrorRate = StreamEx.of(nearbyTickets)
                .map(Ticket::getFields)
                .flatMapToInt(IntStreamEx::of)
                .filter(ticketField -> StreamEx.of(rules).allMatch(rule -> rule.isInvalid(ticketField)))
                .sum();
        log.info(ticketScanningErrorRate);
    }

    @ToString
    private static class Rule {
        static Pattern definitionPattern = Pattern.compile("(.*): (\\d+)-(\\d+) or (\\d+)-(\\d+)");

        private final String name;
        private final int low1;
        private final int high1;
        private final int low2;
        private final int high2;

        private Rule(String definition) {
            Matcher matcher = definitionPattern.matcher(definition);
            if (!matcher.matches()) {
                throw new IllegalStateException(definition);
            }
            name = matcher.group(1);
            low1 = Integer.parseInt(matcher.group(2));
            high1 = Integer.parseInt(matcher.group(3));
            low2 = Integer.parseInt(matcher.group(4));
            high2 = Integer.parseInt(matcher.group(5));
        }

        public boolean isInvalid(int value) {
            return !isValid(value);
        }

        private boolean isValid(int value) {
            return (value >= low1 && value <= high1) || (value >= low2 && value <= high2);
        }
    }

    @Getter
    @ToString
    private static class Ticket {
        private List<Integer> fields;

        private Ticket(String commaSeparatedFields) {
            fields = StreamEx.split(commaSeparatedFields, ",").mapToInt(Integer::parseInt).boxed().toList();
        }
    }
}
