package DataStructureAndAlgorithms.Tests;

import DataStructureAndAlgorithms.ISolvable;
import DataStructureAndAlgorithms.ITestable;
import DataStructureAndAlgorithms.Solutions.Array.Largest_Sum_Contiguous_Subarray;

public class Largest_Sum_Contiguous_Subarray_Debug implements ITestable<Integer> {

    private Largest_Sum_Contiguous_Subarray solution;

    public Largest_Sum_Contiguous_Subarray_Debug(ISolvable<Integer> solution) {
        this.solution = (Largest_Sum_Contiguous_Subarray) solution;
    }

    @Override
    public Integer test() {
        int maxEndingHere = 0;
        int maxSoFar = Integer.MIN_VALUE;
        int i = 0;
        while (i < solution.arr.length) {
            maxEndingHere += solution.arr[i];
            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
            }
            if (maxEndingHere < 0) {
                maxEndingHere = 0;
            }
            i++;
        }

        return maxSoFar;
    }

    @Override
    public boolean compare() {
        return solution.solve() == this.test();
    }
}