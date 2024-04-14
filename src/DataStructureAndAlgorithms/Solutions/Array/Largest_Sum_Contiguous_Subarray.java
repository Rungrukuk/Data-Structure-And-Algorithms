package DataStructureAndAlgorithms.Solutions.Array;

import DataStructureAndAlgorithms.ISolvable;

public class Largest_Sum_Contiguous_Subarray implements ISolvable {

    private final int[] arr;

    public Largest_Sum_Contiguous_Subarray(int[] arr) {
        this.arr = arr;
    }

    public Integer findLargestSumContiguousSubarray() {
        int maxEndingHere = 0;
        int maxSoFar = Integer.MIN_VALUE;

        for (int i = 1; i < arr.length; i++) {
            maxEndingHere += arr[i];
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
    public void solve(SolutionKind solutionKind) {
        System.out.println(findLargestSumContiguousSubarray());
    }
}
