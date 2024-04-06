package DataStructureAndAlgorithms.Solutions;

import DataStructureAndAlgorithms.Base_Solution;

public class Largest_Sum_Contiguous_Subarray extends Base_Solution {
    @SuppressWarnings("unchecked")
    @Override
    public <T, R> R solution(T... args) {
        R answer = null;
        if (args[0] instanceof int[]) {
            int[] arr = (int[]) args[0];
            answer = (R) this.findLargestSumContiguousSubarray(arr);
        } else {
            throw new IllegalArgumentException("args[0] must be an array of integers");
        }
        return answer;
    }

    public Integer findLargestSumContiguousSubarray(int arr[]) {
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
