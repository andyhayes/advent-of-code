package com.checkraiseit.adventofcode.y2020.d18;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
class BracketedExpression implements Expression {

    private final Expression expression;

    public long evaluate() {
        return expression.evaluate();
    }

    @Override
    public Expression withAdditionGivenPrecedence() {
        Expression newExpression = this.expression.withAdditionGivenPrecedence();
        if (newExpression instanceof BracketedExpression) {
            return newExpression;
        }
        return new BracketedExpression(newExpression);
    }

    @Override
    public String toString() {
        return String.format("(%s)", expression.toString());
    }
}
