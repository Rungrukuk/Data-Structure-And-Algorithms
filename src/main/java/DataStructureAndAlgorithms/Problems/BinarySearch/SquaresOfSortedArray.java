package DataStructureAndAlgorithms.Problems.BinarySearch;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Squares Of Sorted Array", category = "Binary Search")
public class SquaresOfSortedArray extends BaseProblem<int[]> {
    public final int[] nums = new int[] { -7, -3, 2, 3, 11 };

    @Override
    public int[] solve() {
        int left = 0;
        int right = nums.length - 1;
        int[] answer = new int[nums.length];
        int i = nums.length - 1;
        while (left <= right) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                answer[i] = nums[left] * nums[left];
                left++;
                i--;
            } else {
                answer[i] = nums[right] * nums[right];
                right--;
                i--;
            }
        }
        return answer;
    }
}