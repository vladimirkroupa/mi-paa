package com.github.vladimirkroupa.paa.knapsack

import com.github.vladimirkroupa.paa.knapsack.Problem
import com.github.vladimirkroupa.util.Resources
import com.github.vladimirkroupa.util.pairwise

class InstanceFileParser {

    fun readInstanceFile(instanceSize: Int): List<Problem> {
        val lines = Resources.readLines("/inst/knap_$instanceSize.inst.dat")
        val instances = lines.map { line -> parseInstanceLine(line) }
        return instances
    }

    fun parseInstanceLine(instanceLine: String): Problem {
        val cols = instanceLine.split(delimiters = " ")
        val id = cols[0]
        val size = cols[1].toInt()
        val capacity = cols[2].toInt()
        val items = cols.subList(3, cols.size).map { str -> str.toInt() }.pairwise()
        val weights = items.first
        val values = items.second
        if (weights.size != size || values.size != size) {
            throw IllegalArgumentException("$size = ${weights.size} = ${values.size} does not hold.")
        }
        return Problem(capacity, weights, values)
    }

}