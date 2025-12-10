package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.SearchInsertPosition;

@Practice(problemName = "Search Insert Position", category = "Binary Search")
public class SearchInsertPosition_Practice extends BasePractice<Integer, SearchInsertPosition> {

    public SearchInsertPosition_Practice(SearchInsertPosition problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.nums.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (problem.nums[middle] < problem.target) {
                left = middle + 1;
            } else
                right = middle - 1;
        }

        return left;
    }
}
