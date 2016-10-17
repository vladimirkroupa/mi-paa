package com.github.vladimirkroupa.paa.knapsack

fun <T> Collection<T>.pairwise() : Pair<Collection<T>, Collection<T>> {
    if (size % 2 == 0) {
        throw IllegalArgumentException("Collection of odd length cannot be split int two.")
    }

    val it = this.iterator()
    while (it.hasNext()) {
        val next = it.next()
        val nextNext = it.next()
    }
}

fun main(args: Array<String>) {
    val p9000 = Problem(100, listOf(18, 42, 88, 3), listOf(114, 136, 192, 223))
    println(p9000)
    println()
    val solver = HeuristicSolver()
    val solution = solver.solve(p9000)
    println()
    println("Solution: $solution")
}