package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.FindPeakElement;

@Practice(problemName = "Find Peak Element", category = "Binary Search")
public class FindPeakElement_Practice extends BasePractice<Integer, FindPeakElement> {

    public FindPeakElement_Practice(FindPeakElement problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.nums.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;

            if (problem.nums[middle] > problem.nums[middle + 1]) {
                right = middle - 1;
            } else
                left = middle + 1;
        }

        return left;
    }
}
