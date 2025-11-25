package DataStructureAndAlgorithms.Problems.BinarySearch;

import DataStructureAndAlgorithms.Base_Problem;
import DataStructureAndAlgorithms.Problem;

@Problem(name = "Median Of Two Sorted Arrays", category = "Binary Search")
public class Median_Of_Two_Sorted_Arrays extends Base_Problem<Double> {
    public int[] nums = new int[]{1, 2, 3, 4, 5};
    public int[] nums2 = new int[]{6, 7, 8, 9, 10, 11, 12};

    @Override
    protected Double solve() {
        if (nums.length > nums2.length) {
            int[] temp;
            temp = nums;
            nums = nums2;
            nums2 = temp;
        }
        int n = nums.length, m = nums2.length;
        int left = 0, right = n;

        while (left <= right) {
            final int i = left + (right - left) / 2;
            final int j = (n + m + 1) / 2 - i;

            int numsLeft = (i == 0) ? Integer.MIN_VALUE : nums[i - 1];
            int numsRight = (i == n) ? Integer.MAX_VALUE : nums[i];
            int nums2Left = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2Right = (j == m) ? Integer.MAX_VALUE : nums2[j];

            if (numsLeft <= nums2Right && nums2Left <= numsRight) {
                if ((n + m) % 2 == 0)
                    return (Math.max(numsLeft, nums2Left) + Math.min(numsRight, nums2Right)) / 2.0;
                else
                    return ((double) Math.max(numsLeft, nums2Left));
            } else if (numsLeft > nums2Right)
                right = i - 1;
            else
                left = i + 1;
        }
        return 0.0;
    }
}
