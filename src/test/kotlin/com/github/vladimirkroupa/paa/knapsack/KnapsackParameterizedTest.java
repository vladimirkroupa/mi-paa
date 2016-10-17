package com.github.vladimirkroupa.paa.knapsack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeThat;

@RunWith(Parameterized.class)
public class KnapsackParameterizedTest {

    private Problem instance;
    private Solution expectedSolution;

    public KnapsackParameterizedTest(Problem instance, Solution expectedSolution) {
        this.instance = instance;
        this.expectedSolution = expectedSolution;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        int problemSize = 4;
        List<Problem> instances = new InstanceFileParser().readInstanceFile(problemSize);
        List<Solution> solutions = new SolutionFileParser().readSolutionFile(problemSize);

        int dataCount = instances.size();
        List<Object[]> testData = new ArrayList<Object[]>();

        for (int i = 0; i < dataCount; i++) {
            Problem instance = instances.get(i);
            Solution solution = solutions.get(i);
            Object[] pair = { instance, solution };
            testData.add(pair);
        }
        return testData;
    }

    @Test
    public void solutionEqualToExpected() {
        assumeThat(instance.getItemCount(), equalTo(expectedSolution.getProblemSize()));

        KnapsackSolver solver = new BruteforceSolver();
        Knapsack solution = solver.solve(instance);
        assertNotNull(solution);

        assertThat("Test id " + expectedSolution.getId() + " failed.", solution.getTotalValue(), equalTo(expectedSolution.getValue()));
    }

}
