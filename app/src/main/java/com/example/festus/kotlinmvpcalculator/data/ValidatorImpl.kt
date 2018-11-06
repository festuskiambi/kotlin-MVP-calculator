package com.example.festus.kotlinmvpcalculator.data

import com.example.festus.kotlinmvpcalculator.domain.repository.IValidator

/**
 * Created by Festus Kiambi on 11/5/18.
 */
class ValidatorImpl : IValidator {
    override fun validateExpression(expression: String): Boolean {
        //check for valid starting/ending chars
        if (invalidStart(expression)) return false
        if (invalidEnd(expression)) return false

        //Check for concurrent decimals and operators like "2++2"
        if (hasConcurrentOperators(expression)) return false
        if (hasConcurrentDecimals(expression)) return false

        return true
    }

    private fun invalidEnd(expression: String): Boolean {

        return when {
            expression.endsWith("+") -> true
            expression.endsWith("-") -> true
            expression.endsWith("/") -> true
            expression.endsWith("*") -> true
            else -> false
        }
    }

    private fun invalidStart(expression: String): Boolean {
        return when {
            expression.startsWith("+") -> true
            expression.startsWith("-") -> true
            expression.startsWith("/") -> true
            expression.startsWith("*") -> true
            else -> false
        }

    }


    private fun hasConcurrentDecimals(expression: String): Boolean {
        expression.indices
                .forEach {
                    if (it < expression.lastIndex) {
                        if (isConcurrentDecimal(expression[it], expression[it + 1])) {
                            return true
                        }
                    }
                }
        return false
    }

    private fun isConcurrentDecimal(current: Char, next: Char): Boolean {
        if (current == '.' && next == '.') {
            return true
        }
        return false
    }

    private fun hasConcurrentOperators(expression: String): Boolean {

        expression.indices
                .forEach {
                    if (it < expression.lastIndex) {
                        if (isConcurrentOperator(expression[it], expression[it + 1])) {
                            return true

                        }
                    }
                }

        return false
    }

    private fun isConcurrentOperator(current: Char, next: Char): Boolean {
        if (isOperator(current) && isOperator(next)) {
            return true
        }
        return false
    }

    private fun isOperator(current: Char): Boolean {
        return when (current) {
            '+' -> true
            '-' -> true
            '/' -> true
            '*' -> true
            else -> false
        }

    }
}