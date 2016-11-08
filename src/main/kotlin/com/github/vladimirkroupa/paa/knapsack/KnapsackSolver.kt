package com.github.vladimirkroupa.paa.knapsack

abstract class KnapsackSolver(protected val problemInstance: Problem) {

    abstract fun solve(): Knapsack?

}