package DataStructureAndAlgorithms.Problems.BinarySearch.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Search Insert Position", category = "Binary Search", difficulty = "Easy")
public class SearchInsertPosition extends BaseProblem<Integer> {

    public final int[] nums = new int[]{1, 3, 5, 6};
    public final int target = 0;

    @Override
    public Integer solve() {
        int right = nums.length - 1;
        int left = 0;
        while (left <= right) {
            final int middle = left + (right - left);
            if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }

}
