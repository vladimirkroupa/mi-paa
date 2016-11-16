package com.github.vladimirkroupa.paa.knapsack

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.time.Duration
import java.time.Instant

@RunWith(Parameterized::class)
class Measurement(val instanceSize: Int, val repeat: Int) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "N = {0}")
        fun data() : Collection<Array<Any>> {
            return listOf(
                    arrayOf(4, 100000),
                    arrayOf(10, 10000),
                    arrayOf(15, 1000),
                    arrayOf(20, 1000),
                    arrayOf(22, 1000),
                    arrayOf(25, 1000),
                    arrayOf(27, 1000),
                    arrayOf(30, 1000),
                    arrayOf(32, 1000),
                    arrayOf(35, 1000),
                    arrayOf(37, 1000),
                    arrayOf(40, 1000)
            ) as Collection<Array<Any>>
        }
    }

    @Test
    fun measureBranchAndBound() {
        measureTimes(SolverFactory(SolverType.BRANCHANDBOUND))
    }

    fun measureRelativeError(solverFactory : SolverFactory) {
        val data = prepareTestData(instanceSize)

        var totalRelErr = 0.0
        var maxRelErr = Double.MIN_VALUE

        data.forEach { pair ->
            val optimalValue = pair.second.value

            val problem = pair.first
            val solver = solverFactory.create(problem)
            val solution: Knapsack? = solver.solve()

            val absoluteError = optimalValue - (solution!!.totalValue)
            val relativeError = absoluteError.toDouble() / optimalValue

            if (relativeError > maxRelErr) {
                maxRelErr = relativeError
            }

            totalRelErr += relativeError
        }
        val avgRelErr = totalRelErr / data.size

        print("max rel. err")
        print("avg rel. err")
        print("max rel. err (%)")
        print("avg rel. err (%)")
        println()
        print("$maxRelErr")
        print("$avgRelErr")
        print("${maxRelErr * 100}")
        print("${avgRelErr * 100}")
    }

    fun measureTimes(solverFactory : SolverFactory) {
        val data = prepareTestData(instanceSize)

        var minInstMs = Double.MAX_VALUE
        var maxInstMs = Double.MIN_VALUE

        var totalInstMs = 0.0

        data.forEach { pair ->
            val startInst = Instant.now()
            for (i in (1..repeat)) {
                val problem = pair.first
                val solver = solverFactory.create(problem)
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
//        print("n\t")
//        print("min(ms)\t")
//        print("max(ms)\t")
//        print("avg(ms)\t")
//        print("repeated\t")
//        println()
        print("$instanceSize\t")
        print("$minInstMs\t")
        print("$maxInstMs\t")
        print("$avgInstMs\t")
        print("$repeat\t")
        println()
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