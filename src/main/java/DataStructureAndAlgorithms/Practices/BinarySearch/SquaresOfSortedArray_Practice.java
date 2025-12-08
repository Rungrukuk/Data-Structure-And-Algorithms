package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;

import DataStructureAndAlgorithms.Problems.BinarySearch.SquaresOfSortedArray;

@Practice(problemName = "Squares Of Sorted Array", category = "Binary Search")
public class SquaresOfSortedArray_Practice extends BasePractice<Integer[], SquaresOfSortedArray> {

    public SquaresOfSortedArray_Practice(SquaresOfSortedArray problem) {
        super(problem);
    }

    @Override
    public Integer[] practice() {
        int left = 0;
        int right = problem.nums.length - 1;
        Integer[] answer = new Integer[problem.nums.length];
        int i = problem.nums.length - 1;
        while (left <= right) {
            if (Math.abs(problem.nums[left]) > Math.abs(problem.nums[right])) {
                answer[i] = problem.nums[left] * problem.nums[left];
                left++;
            } else {
                answer[i] = problem.nums[right] * problem.nums[right];
                right--;
            }
            i--;
        }
        return answer;
    }
}
