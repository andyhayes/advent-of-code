package com.checkraiseit.adventofcode.y2020.d18;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionFactory {

    public static final Pattern SIMPLE_EXPRESSION_PATTERN = Pattern.compile("^\\(?([0-9]+) ([+*]) ([0-9]+)\\)?$");
    public static final Pattern ENDS_WITH_NUMBERS_PATTERN = Pattern.compile(".*([+*]) ([0-9]+)$");

    public Expression parse(String expression) {
        try {
            return new Constant(Long.parseLong(expression));
        } catch (NumberFormatException ignored) { }

        if (expression.startsWith("(") && expression.endsWith(")")) {
            String matchClosingBracket = matchClosingBracket(expression);
            if (expression.length() == matchClosingBracket.length()) {
                return new BracketedExpression(parse(matchClosingBracket.substring(1, matchClosingBracket.length() - 1)));
            }
        }

        return new SimpleExpression(parseLeft(expression), getOperator(expression), parseRight(expression));
    }

    private Expression parseLeft(String expression) {
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(expression);
        if (matcher.matches()) {
            return new Constant(Long.parseLong(matcher.group(1)));
        }
        if (expression.endsWith(")")) {
            String matchClosingBracket = matchClosingBracket(expression);
            return parse(expression.substring(0, expression.lastIndexOf(matchClosingBracket) - 3));
        }
        int lastOperatorIndex = Math.max(expression.lastIndexOf(" +"), expression.lastIndexOf(" *"));
        return parse(expression.substring(0, lastOperatorIndex));
    }

    private Expression parseRight(String expression) {
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(expression);
        if (matcher.matches()) {
            return new Constant(Long.parseLong(matcher.group(3)));
        }
        Matcher endsWithNumbersMatcher = ENDS_WITH_NUMBERS_PATTERN.matcher(expression);
        if (endsWithNumbersMatcher.matches()) {
            return new Constant(Long.parseLong(endsWithNumbersMatcher.group(2)));
        }
        if (expression.endsWith(")")) {
            return parse(matchClosingBracket(expression));
        }
        throw new IllegalStateException("uh-oh: " + expression);
    }

    private Operator getOperator(String expression) {
        Matcher matcher = SIMPLE_EXPRESSION_PATTERN.matcher(expression);
        if (matcher.matches()) {
            return getOperator(matcher.group(2).charAt(0));
        }
        Matcher endsWithNumbersMatcher = ENDS_WITH_NUMBERS_PATTERN.matcher(expression);
        if (endsWithNumbersMatcher.matches()) {
            return getOperator(endsWithNumbersMatcher.group(1).charAt(0));
        }
        if (expression.endsWith(")")) {
            return getOperator(expression.charAt(expression.lastIndexOf(matchClosingBracket(expression)) - 2));
        }
        int lastOperatorIndex = Math.max(expression.lastIndexOf("+"), expression.lastIndexOf("*"));
        return getOperator(expression.charAt(lastOperatorIndex));
    }

    private Operator getOperator(char ch) {
        switch (ch) {
            case '+':
                return new Add();
            case '*':
                return new Multiply();
            default:
                throw new IllegalStateException("uh-oh: " + ch);
        }
    }

    private String matchClosingBracket(String expression) {
        int bracketCount = 0;
        char[] chars = expression.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char ch = chars[i];
            if (ch == ')') {
                bracketCount++;
            } else if (ch == '(' && bracketCount > 1) {
                bracketCount--;
            } else if (ch == '(' && bracketCount == 1) {
                return expression.substring(i);
            }
        }
        throw new IllegalStateException("uh-oh: " + expression);
    }
}
