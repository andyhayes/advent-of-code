package com.checkraiseit.adventofcode.y2020.d16;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        Ticket yourTicket = new Ticket(Resources.toString(Resources.getResource("y2020/day16-your-ticket.txt"), Charsets.UTF_8));

        part1(rules, nearbyTickets);
        part2(rules, nearbyTickets, yourTicket);
    }

    private static void part1(List<Rule> rules, List<Ticket> nearbyTickets) {
        int ticketScanningErrorRate = StreamEx.of(nearbyTickets)
                .map(Ticket::getFields)
                .flatMapToInt(IntStreamEx::of)
                .filter(ticketField -> StreamEx.of(rules).allMatch(rule -> rule.isInvalid(ticketField)))
                .sum();
        log.info(ticketScanningErrorRate);
    }

    private static void part2(List<Rule> rulesList, List<Ticket> nearbyTickets, Ticket yourTicket) {
        Rules rules = new Rules(rulesList);
        List<Ticket> validTickets = StreamEx.of(nearbyTickets).filter(rules::isValid).toList();
        log.info("tickets: {}, validTickets: {}", nearbyTickets.size(), validTickets.size());
        Map<Integer, Set<Rule>> fieldPossibleRules = newHashMap();
        for (int i = 0; i < rulesList.size(); i++) {
            fieldPossibleRules.put(i, rules.possibleRulesForTicketField(validTickets, i));
        }

        while (hasMoreThanOneRulePerField(fieldPossibleRules)) {
            Set<Rule> uniqueRules = EntryStream.of(fieldPossibleRules)
                    .filter(entry -> entry.getValue().size() == 1)
                    .values()
                    .flatMap(Set::stream)
                    .toSet();
            EntryStream.of(fieldPossibleRules)
                    .filter(entry -> entry.getValue().size() > 1)
                    .forEach(entry -> entry.getValue().removeAll(uniqueRules));
        }
        log(fieldPossibleRules);

        List<Integer> departureFields = EntryStream.of(fieldPossibleRules)
                .filter(entry -> Iterables.get(entry.getValue(), 0).isDepartureRule())
                .keys().toList();
        log.info(yourTicket.productOfFields(departureFields));
    }

    private static boolean hasMoreThanOneRulePerField(Map<Integer, Set<Rule>> fieldPossibleRules) {
        return StreamEx.of(fieldPossibleRules.values()).anyMatch(rules -> rules.size() > 1);
    }

    private static void log(Map<Integer, Set<Rule>> fieldPossibleRules) {
        log.info("---");
        fieldPossibleRules.forEach((key, value) -> log.info("index: {}, ruleCount: {}", key, value.size()));
    }

    @RequiredArgsConstructor
    private static class Rules {
        private final List<Rule> rules;

        public boolean isValid(Ticket ticket) {
            return StreamEx.of(ticket.fields).allMatch(this::isValidForAnyRule);
        }

        private boolean isValidForAnyRule(Integer field) {
            return StreamEx.of(rules).anyMatch(rule -> rule.isValid(field));
        }

        private Set<Rule> possibleRulesForTicketField(List<Ticket> validTickets, int fieldIndex) {
            List<Integer> fieldValues = StreamEx.of(validTickets).map(ticket -> ticket.getField(fieldIndex)).toList();
            return StreamEx.of(rules).filter(rule -> rule.isAllValid(fieldValues)).toSet();
        }
    }

    @EqualsAndHashCode
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

        private boolean isAllValid(List<Integer> fields) {
            return fields.stream().allMatch(this::isValid);
        }

        public boolean isDepartureRule() {
            return name.startsWith("departure");
        }
    }

    @Getter
    @ToString
    private static class Ticket {
        private final List<Integer> fields;

        private Ticket(String commaSeparatedFields) {
            fields = StreamEx.split(commaSeparatedFields, ",").mapToInt(Integer::parseInt).boxed().toList();
        }

        private int getField(int index) {
            return fields.get(index);
        }

        public long productOfFields(List<Integer> departureFields) {
            return IntStreamEx.of(departureFields).map(this::getField).asLongStream().reduce(1, (a,b) -> a * b);
        }
    }
}
