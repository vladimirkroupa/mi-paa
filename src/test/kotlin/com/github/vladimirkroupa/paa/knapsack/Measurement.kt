package com.github.vladimirkroupa.paa.knapsack

import org.junit.Test
import java.time.Duration
import java.time.Instant

class Measurement {

    @Test
    fun measureHeuristicErr() {
        val data = prepareTestData(27)

        var totalRelErr = 0.0
        var maxRelErr = Double.MIN_VALUE

        data.forEach { pair ->
            val optimalValue = pair.second.value

            val solver = HeuristicSolver(pair.first)
            val solution: Knapsack? = solver.solve()

            val absoluteError = optimalValue - (solution!!.totalValue)
            val relativeError = absoluteError.toDouble() / optimalValue

            if (relativeError > maxRelErr) {
                maxRelErr = relativeError
            }

            totalRelErr += relativeError
        }
        val relativeErr = totalRelErr / 50

        println("max rel. err: $maxRelErr")
        println("avg rel. err: $relativeErr")
    }

    @Test
    fun measureHeuristic() {
        val data = prepareTestData(40)


        var minInstMs = Double.MAX_VALUE
        var maxInstMs = Double.MIN_VALUE

        var totalInstMs = 0.0

        val repeat = 10000
        data.forEach { pair ->
            val optimalValue = pair.second.value

            val startInst = Instant.now()
            var solution: Knapsack? = null
            for (i in (1..repeat)) {
                val solver = HeuristicSolver(pair.first)
                solution = solver.solve()
            }

            val endInst = Instant.now()
            val durationInst = Duration.between(startInst, endInst).toMillis().toDouble() / repeat

            if (durationInst < minInstMs) {
                minInstMs = durationInst
            }
            if (durationInst > maxInstMs) {
                maxInstMs = durationInst
            }

            totalInstMs += durationInst
        }
        val avgInstMs = totalInstMs / 50
        println("min: $minInstMs ms")
        println("max: $maxInstMs ms")
        println("avg: $avgInstMs ms")
    }

    @Test
    fun measureBruteForce() {
        val data = prepareTestData(10)

        var minInstMs = Double.MAX_VALUE
        var maxInstMs = Double.MIN_VALUE

        var totalInstMs = 0.0

        val repeat = 1
        data.forEach { pair ->
            val startInst = Instant.now()
            for (i in (1..repeat)) {
                val solver = BruteforceSolver(pair.first)
                solver.solve()
            }
            val endInst = Instant.now()
            val durationInst = Duration.between(startInst, endInst).toMillis().toDouble() / repeat

            if (durationInst < minInstMs) {
                minInstMs = durationInst
            }
            if (durationInst > maxInstMs) {
                maxInstMs = durationInst
            }

            totalInstMs += durationInst
        }
        val avgInstMs = totalInstMs / 50
        println("min: $minInstMs ms")
        println("max: $maxInstMs ms")
        println("avg: $avgInstMs ms")
    }

    private fun prepareTestData(problemSize: Int): List<Pair<Problem, Solution>> {
        val instances = InstanceFileParser().readInstanceFile(problemSize)
        val solutions = SolutionFileParser().readSolutionFile(problemSize)

        val dataCount = instances.size
        val testData = mutableListOf<Pair<Problem, Solution>>()

        for (i in 0..dataCount - 1) {
            val instance = instances[i]
            val solution = solutions[i]
            val pair = Pair(instance, solution)
            testData.add(pair)
        }
        return testData
    }

}