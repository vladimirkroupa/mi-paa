package com.github.vladimirkroupa.paa.knapsack.solver

import com.github.vladimirkroupa.paa.knapsack.Knapsack
import com.github.vladimirkroupa.paa.knapsack.Problem

/**
 * http://cse.unl.edu/~goddard/Courses/CSCE310J/Lectures/Lecture8-DynamicProgramming.pdf
 */
class DynamicProgrammingSolver(problemInstance: Problem) : KnapsackSolver(problemInstance) {

    var knapsackVals: Array<IntArray>

    init {
        knapsackVals = Array(problemInstance.itemCount + 1, { i -> IntArray(problemInstance.knapsackCapacity + 1, { 0 }) })
    }

    override fun solve(): Knapsack {
        computeTable()
        return itemsFromTable()
    }

    fun computeTable() {
        for (i in (1..problemInstance.itemCount)) {
            for (w in (0..problemInstance.knapsackCapacity)) {
                val itemI = problemInstance.getItem(i)
                if (itemI.weight <= w) { // does item fit in?
                    val smallerKnapsack = knapsackVals[i - 1][w - itemI.weight]
                    val withoutItem = knapsackVals[i - 1][w]
                    val smallerKwithItem = itemI.value + smallerKnapsack
                    if (smallerKwithItem > withoutItem) {
                        knapsackVals[i][w] = itemI.value + knapsackVals[i - 1][w - itemI.weight]
                    } else {
                        knapsackVals[i][w] = knapsackVals[i - 1][w]
                    }
                } else { // item does not fit in
                    knapsackVals[i][w] = knapsackVals[i - 1][w]
                }
            }
        }
        println(knapsackVals[problemInstance.itemCount][problemInstance.knapsackCapacity])
    }

    fun itemsFromTable(): Knapsack {
        var knapsack = Knapsack(problemInstance)
        var k = problemInstance.knapsackCapacity
        for (i in problemInstance.itemCount downTo 1) {
            if (knapsackVals[i][k] != knapsackVals[i - 1][k]) {
                knapsack = knapsack.add(i)
                val itemWeight = problemInstance.getItem(i).weight
                k -= itemWeight
            }
        }
        return knapsack
    }

}