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
    fun measure() {
        val factory = SolverFactory(SolverType.FPTAS, 0.1)
        measureTimes(factory)
        //measureRelativeError(factory)
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

        if (instanceSize == 4) {
            print("max rel. err\t")
            print("avg rel. err\t")
            print("max rel. err (%)\t")
            print("avg rel. err (%)\t")
            println()
        }
        print("${maxRelErr.format(3)}\t")
        print("${avgRelErr.format(3)}\t")
        print("${(maxRelErr * 100).format(3)}\t")
        print("${(avgRelErr * 100).format(3)}\t")
        println()
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

        if (instanceSize == 4) {
            print("n\t")
            print("min(ms)\t")
            print("max(ms)\t")
            print("avg(ms)\t")
            print("repeated\t")
            println()
        }
        print("$instanceSize\t")
        print("${minInstMs.format(4)}\t")
        print("${maxInstMs.format(4)}\t")
        print("${avgInstMs.format(4)}\t")
        print("$repeat\t")
        println()
    }

    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

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