package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.Base_Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.Binary_Search;

public class Binary_Search_Practice extends Base_Practice<Integer, Binary_Search> {

    public Binary_Search_Practice(Binary_Search problem) {
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
        return -1;
    }

}
