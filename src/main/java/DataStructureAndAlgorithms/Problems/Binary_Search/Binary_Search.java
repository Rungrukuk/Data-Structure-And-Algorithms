package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Binary Search", category = "Binary Search")
public class Binary_Search extends BaseProblem<Integer> {
    public final int[] nums = new int[] { 1, 3, 5, 6 };
    public final int target = 5;

    @Override
    public Integer solve() {
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
