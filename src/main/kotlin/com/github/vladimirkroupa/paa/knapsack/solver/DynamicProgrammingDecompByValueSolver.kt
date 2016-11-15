package com.github.vladimirkroupa.paa.knapsack.solver

import com.github.vladimirkroupa.paa.knapsack.Knapsack
import com.github.vladimirkroupa.paa.knapsack.Problem

/**
 * https://edux.fit.cvut.cz/courses/MI-PAA/homeworks/knapsack/dynprog
 */
class DynamicProgrammingDecompByValueSolver(problemInstance: Problem) : KnapsackSolver(problemInstance) {

    val rows: Int
    val cols: Int
    val knapsackWeights: Array<IntArray>

    init {
        rows = problemInstance.itemCount + 1

        val totalProblemValue = problemInstance.items.fold(0, { acc, item -> acc + item.value })
        cols = totalProblemValue + 1

        knapsackWeights = Array(rows, { i -> IntArray(cols) })
        knapsackWeights[0][0] = 0
        for (v in (1..cols - 1)) {
            knapsackWeights[0][v] = Int.MAX_VALUE
        }
    }

    override fun solve(): Knapsack {
        computeTable()
        val bestVal = findBestKnapsackValue()
        return itemsFromTable(bestVal)
    }

    fun computeTable() {
        for (i in (1..rows - 1)) {
            val item = problemInstance.getItem(i)
            for (v in (1..cols - 1)) {
                val fewerItemsVal = knapsackWeights[i - 1][v]
                val withoutItemCol = v - item.value
                var withoutItemOrWith: Int
                if (withoutItemCol < 0) {
                    withoutItemOrWith = Int.MAX_VALUE
                } else {
                    val withoutItemVal = knapsackWeights[i - 1][withoutItemCol]
                    if (withoutItemVal == Int.MAX_VALUE) {
                        withoutItemOrWith = Int.MAX_VALUE
                    } else {
                        withoutItemOrWith = withoutItemVal + item.weight
                    }
                }
                val newWeight = if (fewerItemsVal < withoutItemOrWith) fewerItemsVal else withoutItemOrWith
                knapsackWeights[i][v] = newWeight
            }
        }
    }

    fun findBestKnapsackValue(): Int {
        var bestValue = 0
        knapsackWeights.last().forEachIndexed { value, weight ->
            if (weight <= problemInstance.knapsackCapacity) {
                if (value > bestValue) {
                    bestValue = value
                }
            }
        }
        return bestValue
    }

    fun itemsFromTable(bestValue: Int): Knapsack {
        var knapsack = Knapsack(problemInstance)
        var value = bestValue
        for (i in rows - 1 downTo 1) {
            if (knapsackWeights[i][value] != knapsackWeights[i - 1][value]) {
                knapsack = knapsack.add(i)
                val itemValue = problemInstance.getItem(i).value
                value -= itemValue
            }
        }
        return knapsack
    }

}