package DataStructureAndAlgorithms.Solutions.Array;

import DataStructureAndAlgorithms.Base_Solution;

public class Largest_Sum_Contiguous_Subarray extends Base_Solution<Integer> {

    public final int[] arr = { -2, -3, 4, -1, 2, 1, -5, 3 };

    @Override
    public Integer solve() {
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
}
