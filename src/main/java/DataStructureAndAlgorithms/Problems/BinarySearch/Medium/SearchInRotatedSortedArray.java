package DataStructureAndAlgorithms.Problems.BinarySearch.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Search In Rotated Sorted Array", category = "Binary Search", difficulty = "Medium")
public class SearchInRotatedSortedArray extends BaseProblem<Integer> {
    public final int[] nums = new int[]{3, 5, 1};
    public final int target = 3;

    @Override
    public Integer solve() {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target)
                return mid;

            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            }
            else {
                if (nums[mid] < target && target <= nums[right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }
        return -1;
    }

}
