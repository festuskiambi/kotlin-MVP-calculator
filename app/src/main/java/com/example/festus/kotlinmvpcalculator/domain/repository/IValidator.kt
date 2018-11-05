package com.example.festus.kotlinmvpcalculator.domain.repository

/**
 * Created by Festus Kiambi on 11/5/18.
 */

interface IValidator {

    fun validateExpression(expression: String): Boolean

}