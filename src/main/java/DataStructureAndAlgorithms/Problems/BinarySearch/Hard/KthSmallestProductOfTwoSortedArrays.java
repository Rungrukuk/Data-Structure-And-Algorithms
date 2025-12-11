package DataStructureAndAlgorithms.Problems.BinarySearch.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Kth Smallest Product Of Two Sorted Arrays", category = "Binary Search", difficulty = "Hard")
public class KthSmallestProductOfTwoSortedArrays extends BaseProblem<Long> {
    public int[] nums1 = new int[]{-2, -1, 0, 1, 2};
    public int[] nums2 = new int[]{-3, -1, 2, 4, 5};
    public int k = 3;

    @Override
    public Long solve() {
        int maxAbsNums1 = Math.max(Math.abs(nums1[0]), Math.abs(nums1[nums1.length - 1]));
        int maxAbsNums2 = Math.max(Math.abs(nums2[0]), Math.abs(nums2[nums2.length - 1]));

        long right = (long) maxAbsNums1 * maxAbsNums2;
        long left = -right;
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (countLessEqual(nums1, nums2, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private long countLessEqual(int[] nums1, int[] nums2, long x) {
        long count = 0;
        for (int a : nums1) {
            if (a > 0) {
                int l = 0, r = nums2.length;
                while (l < r) {
                    int m = l + (r - l) / 2;
                    if ((long) a * nums2[m] <= x)
                        l = m + 1;
                    else
                        r = m;
                }
                count += l;
            } else if (a < 0) {
                int l = 0, r = nums2.length;
                while (l < r) {
                    int m = l + (r - l) / 2;
                    if ((long) a * nums2[m] <= x)
                        r = m;
                    else
                        l = m + 1;
                }
                count += nums2.length - l;
            } else {
                if (x >= 0)
                    count += nums2.length;
            }
        }
        return count;
    }
}
