package com.checkraiseit.adventofcode.y2020.d19;

import lombok.ToString;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Maps.newHashMap;

@ToString
public class Rules {

    private static final Pattern RULE_DEFINITION_PATTERN = Pattern.compile("^(\\d+): (.+)$");
    private final Map<Integer, Rule> ruleMap = newHashMap();

    public Rules(List<String> ruleDefinitions, boolean part2) {
        StreamEx.of(ruleDefinitions).forEach(definition -> {
            Matcher matcher = RULE_DEFINITION_PATTERN.matcher(definition);
            if (matcher.matches()) {
                int ruleId = Integer.parseInt(matcher.group(1));
                ruleMap.put(ruleId, new Rule(ruleId, matcher.group(2), part2));
            } else {
                throw new IllegalStateException("uh-oh: " + definition);
            }
        });
    }

    public boolean matchRuleZero(String message) {
        return matchRule(message, 0);
    }

    public boolean matchRule(String message, int ruleId) {
        return message.matches(getRegexForRule(ruleId));
    }

    public String getRegexForRule(int ruleId) {
        return ruleMap.get(ruleId).toRegEx(ruleMap);
    }
}
