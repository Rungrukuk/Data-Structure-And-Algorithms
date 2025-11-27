package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "Search Insert Position", category = "Binary Search")
public class Search_Insert_Position extends BaseProblem<Integer> {

    public final int[] nums = new int[] { 1, 3, 5, 6 };
    public final int target = 0;

    @Override
    protected Integer solve() {
        int right = nums.length - 1;
        int left = 0;
        while (left <= right) {
            final int middle = left + (right - left);
            if (nums[middle] == target) {
                left = middle;
                break;
            }
            if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }

}
