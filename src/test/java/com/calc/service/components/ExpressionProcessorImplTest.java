package com.calc.service.components;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExpressionProcessorImplTest {

    @Autowired
    private final ExpressionProcessor expression;

    ExpressionProcessorImplTest(ExpressionProcessor expression) {
        this.expression = expression;
    }

    @Test
    void simpleOrderTest() {
        BigDecimal result1 = expression.process("2+2*2");
        BigDecimal result2 = expression.process("2+2/2+2*2");
        
        assertEquals(new BigDecimal("6"), result1);
        assertEquals(new BigDecimal("7"), result2);
    }

    @Test
    void bracketTest() {
        BigDecimal result1 = expression.process("((((5))))");
        BigDecimal result2 = expression.process("-(-(-(-(-1))))");
        BigDecimal result3 = expression.process("(2+2)*2");

        assertEquals(new BigDecimal("5"), result1);
        assertEquals(new BigDecimal("-1"), result2);
        assertEquals(new BigDecimal("8"), result3);
    }

    @Test
    void complexOrderTest() {
        BigDecimal result1 = expression.process("-2^2*2");
        BigDecimal result2 = expression.process("2/2-2");

        assertEquals(new BigDecimal("-8"), result1);
        assertEquals(new BigDecimal("-1"), result2);
    }

    @Test
    void floatingPointIssueTest() {
        BigDecimal result1 = expression.process("0.1 + 0.1 + 0.1");
        BigDecimal result2 = expression.process("2.0 - 1.1");

        assertEquals(new BigDecimal("0.3"), result1);
        assertEquals(new BigDecimal("0.9"), result2);
    }

    @Test
    void evaluateComplexExpression() {
        BigDecimal result1 = expression.process("5+5*(2+(3))-2.00001");
        BigDecimal result2 = expression.process("-300.00+52.0*-1+(-5)");

        assertEquals(new BigDecimal("27.99999"), result1);
        assertEquals(new BigDecimal("-357.00"), result2);
    }

    @Test
    void divideByZeroThrowsArithmeticException() {
        Exception thrown = assertThrows(
                ArithmeticException.class,
                () -> expression.process("300/0"),
                "call method to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("zero"));
    }

    @Test
    void invalidBracketsException() {

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> expression.process("(2+2)+2)"),
                "call method to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Unexpected"));
    }

    @Test
    void cosSinTest() {
        BigDecimal result1 = expression.process("cos0");
        BigDecimal result2 = expression.process("cos1");
        BigDecimal result3 = expression.process("sin-13.5");
        BigDecimal result4 = expression.process("sin1");

        assertEquals(new BigDecimal("1"), result1);
        assertEquals(new BigDecimal("0.5403023058681397174009366074429766"), result2);
        assertEquals(new BigDecimal("-0.8037844265516209270154587262414941"), result3);
        assertEquals(new BigDecimal("0.8414709848078965066525023216302990"), result4);
    }
}