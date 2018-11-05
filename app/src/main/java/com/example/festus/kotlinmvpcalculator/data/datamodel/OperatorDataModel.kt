package com.example.festus.kotlinmvpcalculator.data.datamodel

import java.lang.IllegalArgumentException

/**
 * Created by Festus Kiambi on 11/5/18.
 */

data class OperatorDataModel(val operatorValue: String) {
    val evaluateFirst: Boolean = checkPriority(operatorValue)

    private fun checkPriority(operatorValue: String): Boolean {
        return when (operatorValue) {
            "*" -> true
            "/" -> true
            "+" -> false
            "-" -> false
            else -> throw IllegalArgumentException("unknown operatordatamodel")
        }

    }
}