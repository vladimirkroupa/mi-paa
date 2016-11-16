package com.github.vladimirkroupa.paa.knapsack

import com.github.vladimirkroupa.paa.knapsack.solver.*

class SolverFactory(private val type: SolverType, private val desiredRelativeError: Double? = null) {

    fun create(problemInstance: Problem): KnapsackSolver
            = when (type) {
        SolverType.BRUTEFORCE -> BruteforceSolver(problemInstance)
        SolverType.HEURISTIC -> HeuristicSolver(problemInstance)
        SolverType.BRANCHANDBOUND -> BranchAndBoundSolver(problemInstance)
        SolverType.DYNAMICPROGRAMMING_WEIGHT -> DynamicProgrammingDecompByWeightSolver(problemInstance)
        SolverType.DYNAMICPROGRAMMING_VALUE -> DynamicProgrammingDecompByValueSolver(problemInstance)
        SolverType.FPTAS -> FPTASSolver(problemInstance, desiredRelativeError!!)
    }

}

enum class SolverType {

    BRUTEFORCE, HEURISTIC, BRANCHANDBOUND, DYNAMICPROGRAMMING_WEIGHT, DYNAMICPROGRAMMING_VALUE, FPTAS

}