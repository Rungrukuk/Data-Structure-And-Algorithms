package DataStructureAndAlgorithms.Problems.other;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "Binary Search", category = "other")
public class Binary_Search extends BaseProblem<Integer> {
    public final int[] nums = new int[] { 1, 3, 5, 6 };
    public final int target = 5;

    @Override
    protected Integer solve() {
        int right = nums.length - 1;
        int left = 0;
        int answer = -1;
        while (left <= right) {
            final int middle = left + (right - left);
            if (nums[middle] == target) {
                answer = middle;
                break;
            }
            if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return answer;
    }

}
