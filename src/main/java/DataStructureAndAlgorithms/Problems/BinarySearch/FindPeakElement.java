package DataStructureAndAlgorithms.Problems.BinarySearch;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Find Peak Element", category = "Binary Search")
public class FindPeakElement extends BaseProblem<Integer> {

    public final int[] nums = new int[] { 1, 2, 1, 3, 5, 6, 4 };

    @Override
    public Integer solve() {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            final int middle = left + (right - left) / 2;
            if (nums[middle] < nums[middle + 1]) {
                left = middle + 1;
            } else
                right = middle;
        }
        return left;
    }

}
