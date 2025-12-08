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
        int right = problem.nums.length - 1;
        int left = 0;
        while (left <= right) {
            final int middle = left + (right - left);
            if (problem.nums[middle] == problem.target) {
                left = middle;
                break;
            }
            if (problem.nums[middle] > problem.target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }
}
