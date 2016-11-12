package com.github.vladimirkroupa.paa.knapsack.solver

import com.github.vladimirkroupa.paa.knapsack.Knapsack
import com.github.vladimirkroupa.paa.knapsack.Problem

abstract class KnapsackSolver(protected val problemInstance: Problem) {

    abstract fun solve(): Knapsack

}