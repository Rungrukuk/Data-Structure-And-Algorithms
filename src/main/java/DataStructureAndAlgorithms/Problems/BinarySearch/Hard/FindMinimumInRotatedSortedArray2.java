package DataStructureAndAlgorithms.Problems.BinarySearch.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Find Minimum In Rotated Sorted Array 2", category = "Binary Search", difficulty = "Hard")
public class FindMinimumInRotatedSortedArray2 extends BaseProblem<Integer> {
    public int[] nums = new int[]{4, 5, 6, 7, 0, 1, 4};

    @Override
    public Integer solve() {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                // min must be in right half
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                // min could be at mid or in left half
                right = mid;
            } else {
                // nums[mid] == nums[right], we can't be sure, just shrink right
                right--;
            }
        }
        return nums[left];
    }
}
