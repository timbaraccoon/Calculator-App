package com.calc.service.components;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static ch.obermuhlner.math.big.DefaultBigDecimalMath.cos;
import static ch.obermuhlner.math.big.DefaultBigDecimalMath.sin;

@Service
public class ExpressionEvaluatorImpl implements ExpressionEvaluator {

    @Override
    public BigDecimal evaluate(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean read(int charToRead) {
                while (ch == ' ') nextChar();
                if (ch == charToRead) {
                    nextChar();
                    return true;
                }
                return false;
            }

            BigDecimal parse() {
                nextChar();
                BigDecimal x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            BigDecimal parseExpression() {
                BigDecimal x = parseTerm();
                while (true) {
                    if      (read('+')) x = x.add(parseTerm()); // addition
                    else if (read('-')) x = x.add(parseTerm().negate()); // subtraction
                    else return x;
                }
            }

            BigDecimal parseTerm() {
                BigDecimal x = parseFactor();
                while (true) {
                    if      (read('*')) x = x.multiply(parseFactor()); // multiplication
                    else if (read('/')) x = x.divide(parseFactor(), RoundingMode.HALF_UP); // division
                    else return x;
                }
            }

            BigDecimal parseFactor() {
                if (read('+')) return parseFactor(); // unary plus
                if (read('-')) return parseFactor().negate(); // unary minus

                BigDecimal x = new BigDecimal(0);
                int startPos = this.pos;
                if (read('(')) { // parentheses
                    x = parseExpression();
                    read(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = new BigDecimal(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    x = switch (func) {
                        case "sqrt" -> x.sqrt(MathContext.DECIMAL64);
                        case "sin" -> sin(x);
                        case "cos" -> cos(x);
                        default -> throw new RuntimeException("Unknown function: " + func);
                    };
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (read('^')) x = x.pow(parseFactor().intValue()); // exponentiation

                return x;
            }
        }.parse();
    }
}
