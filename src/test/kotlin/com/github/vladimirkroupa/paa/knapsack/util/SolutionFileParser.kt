package com.github.vladimirkroupa.paa.knapsack.util

class SolutionFileParser {

    fun readSolutionFile(instanceSize: Int): List<Solution> {
        val lines = Resources.readLines("/sol/knap_$instanceSize.sol.dat")
        val solutions = lines.map { line -> parseSolutionLine(line) }
        return solutions
    }

    fun parseSolutionLine(solutionLine: String): Solution {
        val cols = solutionLine.split(delimiters = " ")
        val id = cols[0]
        val problemSize = cols[1].toInt()
        val value = cols[2].toInt()
        return Solution(id, problemSize, value)
    }

}