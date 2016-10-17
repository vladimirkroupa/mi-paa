package com.github.vladimirkroupa.paa.knapsack

fun main(args: Array<String>) {
    val p9000 = Problem(100, listOf(18, 42, 88, 3), listOf(114, 136, 192, 223))
    println(p9000)
    println()
    val solver = HeuristicSolver()
    val solution = solver.solve(p9000)
    println()
    println("Solution: $solution")
}