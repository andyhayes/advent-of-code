package com.checkraiseit.adventofcode.y2020.d19;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@EqualsAndHashCode
public class Rule {

    private static final Pattern SINGLE_RULE_PATTERN = Pattern.compile("^(\\d+)$");
    private static final Pattern DOUBLE_RULE_PATTERN = Pattern.compile("^(\\d+) (\\d+)$");
    private static final Pattern SINGLE_OR_RULE_PATTERN = Pattern.compile("^(\\d+) \\| (\\d+)$");
    private static final Pattern DOUBLE_OR_RULE_PATTERN = Pattern.compile("^(\\d+) (\\d+) \\| (\\d+) (\\d+)$");

    private final int id;
    private final String definition;
    private final boolean part2;

    public Rule(int id, String definition, boolean part2) {
        this.id = id;
        this.definition = definition;
        this.part2 = part2;
    }

    public String toRegEx(Map<Integer, Rule> ruleMap) {
        if (part2) {
            if (id == 8) {
                return "(" + toRegEx(ruleMap, "42") + ")+";
            }
            if (id == 11) {
                String regex42 = toRegEx(ruleMap, "42");
                String regex31 = toRegEx(ruleMap, "31");
                return "(" +
                        regex42 + regex31 + "|" +
                        regex42 + regex42 + regex31 + regex31 + "|" +
                        regex42 + regex42 + regex42 + regex31 + regex31 + regex31 + "|" +
                        regex42 + regex42 + regex42 + regex42 + regex31 + regex31 + regex31 + regex31 +
                        ")";
            }
        }
        if (definition.contains("a")) {
            return "a";
        }
        if (definition.contains("b")) {
            return "b";
        }
        Matcher matcher = SINGLE_RULE_PATTERN.matcher(definition);
        if (matcher.matches()) {
            String ruleNumber = matcher.group(1);
            return toRegEx(ruleMap, ruleNumber);
        }
        matcher = DOUBLE_RULE_PATTERN.matcher(definition);
        if (matcher.matches()) {
            return toRegEx(ruleMap, matcher.group(1)) + toRegEx(ruleMap, matcher.group(2));
        }
        matcher = SINGLE_OR_RULE_PATTERN.matcher(definition);
        if (matcher.matches()) {
            return String.format("(%s|%s)", toRegEx(ruleMap, matcher.group(1)), toRegEx(ruleMap, matcher.group(2)));
        }
        matcher = DOUBLE_OR_RULE_PATTERN.matcher(definition);
        if (matcher.matches()) {
            String firstRegex = toRegEx(ruleMap, matcher.group(1)) + toRegEx(ruleMap, matcher.group(2));
            String secondRegex = toRegEx(ruleMap, matcher.group(3)) + toRegEx(ruleMap, matcher.group(4));
            return String.format("(%s|%s)", firstRegex, secondRegex);
        }
        throw new IllegalStateException("uh-oh: " + definition);
    }

    private String toRegEx(Map<Integer, Rule> ruleMap, String ruleNumber) {
        return ruleMap.get(Integer.parseInt(ruleNumber)).toRegEx(ruleMap);
    }
}
