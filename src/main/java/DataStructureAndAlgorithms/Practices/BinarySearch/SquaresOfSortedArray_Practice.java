package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.SquaresOfSortedArray;

@Practice(problemName = "Squares Of Sorted Array", category = "Binary Search")
public class SquaresOfSortedArray_Practice extends BasePractice<int[], SquaresOfSortedArray> {

    public SquaresOfSortedArray_Practice(SquaresOfSortedArray problem) {
        super(problem);
    }

    @Override
    public int[] practice() {
        int left = 0;
        int right = problem.nums.length - 1;
        int[] result = new int[problem.nums.length];
        int i = result.length - 1;
        while (left <= right) {
            if (Math.abs(problem.nums[left]) < Math.abs(problem.nums[right])) {
                result[i] = problem.nums[right] * problem.nums[right];
                right--;
            } else {
                result[i] = problem.nums[left] * problem.nums[left];
                left++;
            }
            i--;
        }
        return result;
    }
}
