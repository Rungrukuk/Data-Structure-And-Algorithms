package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "Median Of Two Sorted Arrays", category = "Binary Search")
public class Median_Of_Two_Sorted_Arrays extends BaseProblem<Double> {
    public int[] nums1 = new int[] { 1, 2, 3, 4, 5 };
    public int[] nums2 = new int[] { 6, 7, 8, 9, 10, 11, 12 };

    @Override
    protected Double solve() {
        if (nums1.length > nums2.length) {
            int[] temp;
            temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        int n = nums1.length, m = nums2.length;
        int left = 0, right = n;

        while (left <= right) {
            final int i = left + (right - left) / 2;
            final int j = (n + m + 1) / 2 - i;

            int nums1Left = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int nums1Right = (i == n) ? Integer.MAX_VALUE : nums1[i];
            int nums2Left = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2Right = (j == m) ? Integer.MAX_VALUE : nums2[j];

            if (nums1Left <= nums2Right && nums2Left <= nums1Right) {
                if ((n + m) % 2 == 0)
                    return (Math.max(nums1Left, nums2Left) + Math.min(nums1Right, nums2Right)) / 2.0;
                else
                    return ((double) Math.max(nums1Left, nums2Left));
            } else if (nums1Left > nums2Right)
                right = i - 1;
            else
                left = i + 1;
        }
        return 0.0;
    }
}
