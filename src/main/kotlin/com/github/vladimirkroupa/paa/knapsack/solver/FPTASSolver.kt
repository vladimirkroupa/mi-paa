package com.github.vladimirkroupa.paa.knapsack.solver

import com.github.vladimirkroupa.paa.knapsack.Knapsack
import com.github.vladimirkroupa.paa.knapsack.Problem

class FPTASSolver(problemInstance: Problem, relativeError: Double) : KnapsackSolver(transform(problemInstance, relativeError)) {

    val originalProblem = problemInstance

    val solver = DynamicProgrammingDecompByValueSolver(this.problemInstance)

    override fun solve(): Knapsack {
        val transformedInputSolution = solver.solve()
        return Knapsack(originalProblem, transformedInputSolution.items)
    }

}

fun transform(problemInstace: Problem, relativeError: Double): Problem {
    val maxValue = problemInstace.itemValues.max()!!
    val k = relativeError * maxValue.toDouble() / problemInstace.itemCount
    val newItemValues = problemInstace.itemValues.map { itemValue -> Math.floor(itemValue / k).toInt() }
    return Problem(problemInstace.knapsackCapacity, problemInstace.itemWeights, newItemValues)
}