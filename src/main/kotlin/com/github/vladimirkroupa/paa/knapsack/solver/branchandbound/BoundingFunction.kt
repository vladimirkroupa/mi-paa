package com.github.vladimirkroupa.paa.knapsack.solver.branchandbound

interface BoundingFunction {

    fun computeUpperBound(node: Node): Int

}