package com.example.festus.kotlinmvpcalculator.domain.repository

import com.example.festus.kotlinmvpcalculator.data.datamodel.ExpressionDataModel
import io.reactivex.Flowable

/**
 * Created by Festus Kiambi on 11/5/18.
 */

interface ICalculator {

    fun evaluateExpression(expression: String): Flowable<ExpressionDataModel>
}