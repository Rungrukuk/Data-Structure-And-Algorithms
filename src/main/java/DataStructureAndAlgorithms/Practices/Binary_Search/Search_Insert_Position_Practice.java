package DataStructureAndAlgorithms.Practices.Binary_Search;

import DataStructureAndAlgorithms.Problems.Binary_Search.Search_Insert_Position;
import DataStructureAndAlgorithms.core.BasePractice;
import DataStructureAndAlgorithms.core.Practice;

@Practice(problemName = "Search Insert Position", category = "Binary Search")
public class Search_Insert_Position_Practice extends BasePractice<Integer, Search_Insert_Position> {

    public Search_Insert_Position_Practice(Search_Insert_Position problem) {
        super(problem);
    }

    @Override
    protected Integer practice() {
        int right = problem.nums.length - 1;
        int left = 0;
        while (left <= right) {
            final int middle = left + (right - left) / 2;
            if (problem.nums[middle] == problem.target) {
                return middle;
            }
            if (problem.nums[middle] > problem.target)
                right = middle - 1;
            else
                left = middle + 1;
        }
        return left;
    }

}
