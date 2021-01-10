package com.checkraiseit.adventofcode.y2020.d18;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
class SimpleExpression implements Expression {

    private final Expression left;
    private final Operator operator;
    private final Expression right;

    public long evaluate() {
        return operator.evaluate(left.evaluate(), right.evaluate());
    }

    @Override
    public Expression withAdditionGivenPrecedence() {
        if (operator instanceof Add) {
            return expressionWithAdditionGivenPrecedence();
        }
        return new SimpleExpression(left.withAdditionGivenPrecedence(), operator, right.withAdditionGivenPrecedence());
    }

    private Expression expressionWithAdditionGivenPrecedence() {
        Expression newLeft = left.withAdditionGivenPrecedence();
        Expression newRight = right.withAdditionGivenPrecedence();
        if (newLeft instanceof SimpleExpression && newRight instanceof SimpleExpression) {
            SimpleExpression simpleLeft = (SimpleExpression) newLeft;
            SimpleExpression simpleRight = (SimpleExpression) newRight;
            Expression bracketedAddition = new BracketedExpression(new SimpleExpression(simpleLeft.right, this.operator, simpleRight.left));
            Expression newLeftExpression = new SimpleExpression(simpleLeft.left, simpleLeft.operator, bracketedAddition);
            return new SimpleExpression(newLeftExpression, simpleRight.operator, simpleRight.right);
        }
        if (newLeft instanceof SimpleExpression) {
            SimpleExpression simpleLeft = (SimpleExpression) newLeft;
            Expression bracketedAddition = new BracketedExpression(new SimpleExpression(simpleLeft.right, this.operator, newRight));
            return new SimpleExpression(simpleLeft.left, simpleLeft.operator, bracketedAddition);
        }
        if (newRight instanceof SimpleExpression) {
            SimpleExpression simpleRight = (SimpleExpression) newRight;
            Expression bracketedAddition = new BracketedExpression(new SimpleExpression(newLeft, this.operator, simpleRight.left));
            return new SimpleExpression(bracketedAddition, simpleRight.operator, simpleRight.right);
        }
        return new BracketedExpression(new SimpleExpression(newLeft, operator, newRight));
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", left.toString(), operator.toString(), right.toString());
    }
}
