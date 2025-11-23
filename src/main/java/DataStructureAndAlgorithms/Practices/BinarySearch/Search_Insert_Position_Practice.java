package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.Base_Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.Search_Insert_Position;

public class Search_Insert_Position_Practice extends Base_Practice<Integer, Search_Insert_Position> {

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
