package DataStructureAndAlgorithms.Practices.Binary_Search;

import DataStructureAndAlgorithms.Problems.Binary_Search.Binary_Search;
import DataStructureAndAlgorithms.core.BasePractice;
import DataStructureAndAlgorithms.core.Practice;

@Practice(problemName = "Binary Search", category = "Binary Search")
public class Binary_Search_Practice extends BasePractice<Integer, Binary_Search> {

    public Binary_Search_Practice(Binary_Search problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
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
