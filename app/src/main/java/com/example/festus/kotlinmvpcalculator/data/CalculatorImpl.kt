package com.example.festus.kotlinmvpcalculator.data

import com.example.festus.kotlinmvpcalculator.data.datamodel.ExpressionDataModel
import com.example.festus.kotlinmvpcalculator.data.datamodel.OperandDataModel
import com.example.festus.kotlinmvpcalculator.data.datamodel.OperatorDataModel
import com.example.festus.kotlinmvpcalculator.domain.repository.ICalculator
import io.reactivex.Flowable

/**
 * Created by Festus Kiambi on 11/6/18.
 */
class CalculatorImpl : ICalculator {
    override fun evaluateExpression(expression: String): Flowable<ExpressionDataModel> {
        return evaluate(expression)
    }

    private fun evaluate(expression: String): Flowable<ExpressionDataModel> {

        val operatorDataModels: MutableList<OperatorDataModel> = getOperators(expression)
        val operands: MutableList<OperandDataModel> = getOperands(expression)

        while (operands.size > 1){
            val firstOperand = operands[0]
            val secondOperand = operands[1]
            val firstOperator = operatorDataModels[0]


            if (firstOperator.evaluateFirst ||
                    operatorDataModels.elementAtOrNull(1) == null ||
                    !operatorDataModels[1].evaluateFirst) {
                val result = OperandDataModel(evaluatePair(firstOperand, secondOperand, firstOperator))
                operatorDataModels.remove(firstOperator)
                operands.remove(firstOperand)
                operands.remove(secondOperand)

                operands.add(0, result)
            } else {

                val thirdOperand = operands[2]
                val secondOperator = operatorDataModels[1]
                val result = OperandDataModel(evaluatePair(secondOperand, thirdOperand, secondOperator))

                operatorDataModels.remove(secondOperator)
                operands.remove(secondOperand)
                operands.remove(thirdOperand)

                operands.add(1, result)
            }


        }

        return Flowable.just(ExpressionDataModel(operands[0].value, true))
    }

    private fun getOperands(expression: String): MutableList<OperandDataModel> {
        val operands = expression.split("+", "-", "/", "*")
        val output: MutableList<OperandDataModel> = arrayListOf()

        operands.indices.mapTo(output) {
            OperandDataModel(operands[it])
        }

        return output
    }

    private fun getOperators(expression: String): MutableList<OperatorDataModel> {
        val operators = expression.split("\\d+(?:\\.\\d+)?".toRegex())
        val output: MutableList<OperatorDataModel> = arrayListOf()

        operators.indices.mapTo(output) {
            OperatorDataModel(operators[it])
        }
        return output
    }

    internal fun evaluatePair(firstOperand: OperandDataModel, secondOperand: OperandDataModel, operatorDataModel: OperatorDataModel): String {
        when (operatorDataModel.operatorValue) {
            "+" -> return (firstOperand.value.toFloat() + secondOperand.value.toFloat()).toString()
            "-" -> return (firstOperand.value.toFloat() - secondOperand.value.toFloat()).toString()
            "/" -> return (firstOperand.value.toFloat() / secondOperand.value.toFloat()).toString()
            "*" -> return (firstOperand.value.toFloat() * secondOperand.value.toFloat()).toString()
        }
        throw  IllegalArgumentException("Illegal Operator.")
    }

}