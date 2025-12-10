package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.BinarySearch;

@Practice(problemName = "Binary Search", category = "Binary Search")
public class BinarySearch_Practice extends BasePractice<Integer, BinarySearch> {

    public BinarySearch_Practice(BinarySearch problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.nums.length - 1;

        while (left < right) {
            int middle = left + (right - left) / 2;
            if (problem.nums[middle] == problem.target) {
                return middle;
            }
            if (problem.nums[middle] < problem.target) {
                left = middle + 1;
            } else
                right = middle;
        }

        return -1;
    }
}
