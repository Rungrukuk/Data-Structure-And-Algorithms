package DataStructureAndAlgorithms.Debugs;

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

        for (int i = 1; i < solution.arr.length; i++) {
            maxEndingHere += solution.arr[i];
            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
            }
            if (maxEndingHere < 0) {
                maxEndingHere = 0;
            }
        }
        return maxSoFar;
    }

    @Override
    public boolean compare() {
        return solution.solve() == this.test();
    }
}