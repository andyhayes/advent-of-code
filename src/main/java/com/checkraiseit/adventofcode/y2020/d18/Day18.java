package com.checkraiseit.adventofcode.y2020.d18;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class Day18 {

    public static final Pattern SIMPLE_EXPRESSION_PATTERN = Pattern.compile("^\\(?([0-9]+) ([+*]) ([0-9]+)\\)?$");
    public static final Pattern ENDS_WITH_NUMBERS_PATTERN = Pattern.compile(".*([+*]) ([0-9]+)$");

    public static void main(String[] args) throws IOException {
        List<String> expressions = Resources.readLines(Resources.getResource("y2020/day18.txt"), Charsets.UTF_8);
        long sum = StreamEx.of(expressions).mapToLong(Day18::evaluate).sum();
        log.info("part1: {}", sum);
    }

    static long evaluate(String expression) {
        try {
            return Long.parseLong(expression);
        } catch (NumberFormatException ignored) { }

        if (expression.startsWith("(") && expression.endsWith(")")) {
            String matchClosingBracket = matchClosingBracket(expression);
            if (expression.length() - matchClosingBracket.length() == 2) {
                return evaluate(matchClosingBracket);
            }
        }

        return getOperator(expression).evaluate(getLeft(expression), getRight(expression));
    }

    private static long getLeft(String expression) {
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(expression);
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(1));
        }
        if (expression.endsWith(")")) {
            String matchClosingBracket = matchClosingBracket(expression);
            return evaluate(expression.substring(0, expression.lastIndexOf(matchClosingBracket) - 4));
        }
        int lastOperatorIndex = Math.max(expression.lastIndexOf(" +"), expression.lastIndexOf(" *"));
        return evaluate(expression.substring(0, lastOperatorIndex));
    }

    private static Operator getOperator(String expression) {
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(expression);
        if (matcher.matches()) {
            return getOperator(matcher.group(2).charAt(0));
        }
        Matcher endsWithNumbersMatcher = ENDS_WITH_NUMBERS_PATTERN.matcher(expression);
        if (endsWithNumbersMatcher.matches()) {
            return getOperator(endsWithNumbersMatcher.group(1).charAt(0));
        }
        if (expression.endsWith(")")) {
            return getOperator(expression.charAt(expression.lastIndexOf(matchClosingBracket(expression)) - 3));
        }
        int lastOperatorIndex = Math.max(expression.lastIndexOf("+"), expression.lastIndexOf("*"));
        return getOperator(expression.charAt(lastOperatorIndex));
    }

    private static Operator getOperator(char ch) {
        switch (ch) {
            case '+':
                return new Add();
            case '*':
                return new Multiply();
            default:
                throw new IllegalStateException("uh-oh: " + ch);
        }
    }

    private static long getRight(String expression) {
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(expression);
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(3));
        }
        Matcher endsWithNumbersMatcher = ENDS_WITH_NUMBERS_PATTERN.matcher(expression);
        if (endsWithNumbersMatcher.matches()) {
            return Long.parseLong(endsWithNumbersMatcher.group(2));
        }
        if (expression.endsWith(")")) {
            return evaluate(matchClosingBracket(expression));
        }
        throw new IllegalStateException("uh-oh: " + expression);
    }

    private static String matchClosingBracket(String expression) {
        int bracketCount = 0;
        char[] chars = expression.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char ch = chars[i];
            if (ch == ')') {
                bracketCount++;
            } else if (ch == '(' && bracketCount > 1) {
                bracketCount--;
            } else if (ch == '(' && bracketCount == 1) {
                return expression.substring(i + 1, expression.length() - 1);
            }
        }
        throw new IllegalStateException("uh-oh: " + expression);
    }

    private interface Operator {
        long evaluate(long left, long right);
    }

    private static class Add implements Operator {

        @Override
        public long evaluate(long left, long right) {
            return left + right;
        }
    }

    private static class Multiply implements Operator {
        @Override
        public long evaluate(long left, long right) {
            return left * right;
        }
    }
}
