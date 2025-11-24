package DataStructureAndAlgorithms.Problems.BinarySearch;

import DataStructureAndAlgorithms.Base_Problem;
import DataStructureAndAlgorithms.Problem;

@Problem(name = "Search In Rotated Sorted Array", category = "Binary Search")
public class Search_In_Rotated_Sorted_Array extends Base_Problem<Integer> {
    public final int[] nums = new int[] { 3, 5, 1 };
    public final int target = 3;

    @Override
    protected Integer solve() {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;

            if (nums[middle] == target)
                return middle;

            if (nums[left] <= nums[middle]) {
                if (nums[left] <= target && target < nums[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                if (nums[middle] < target && target <= nums[right]) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }

        return -1;
    }

}
