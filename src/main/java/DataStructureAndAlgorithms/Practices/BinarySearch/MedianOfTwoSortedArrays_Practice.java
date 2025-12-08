package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.MedianOfTwoSortedArrays;

@Practice(problemName = "Median Of Two Sorted Arrays", category = "Binary Search")
public class MedianOfTwoSortedArrays_Practice extends BasePractice<Double, MedianOfTwoSortedArrays> {

    public MedianOfTwoSortedArrays_Practice(MedianOfTwoSortedArrays problem) {
        super(problem);
    }

    @Override
    public Double practice() {
        if (problem.nums1.length > problem.nums2.length) {
            int[] temp = problem.nums1;
            problem.nums1 = problem.nums2;
            problem.nums2 = temp;
        }
        int n = problem.nums1.length, m = problem.nums2.length;
        int left = 0, right = n;
        while (left <= right) {
            final int i = left + (right - left) / 2;
            final int j = (n + m + 1) / 2 - i;
            int nums1Left = (i == 0) ? Integer.MIN_VALUE : problem.nums1[i - 1];
            int nums1Right = (i == n) ? Integer.MAX_VALUE : problem.nums1[i];
            int nums2Left = (j == 0) ? Integer.MIN_VALUE : problem.nums2[j - 1];
            int nums2Right = (j == m) ? Integer.MAX_VALUE : problem.nums2[j];
            if (nums1Left <= nums2Right && nums2Left <= nums1Right) {
                if ((n + m) % 2 == 0) 
                    return (Math.max(nums2Left, nums1Left) + Math.min(nums1Right, nums2Right)) / 2.0;
                return (double) Math.max(nums2Left, nums1Left);
            } else if (nums1Left > nums2Right) {
                right = i - 1;
            } else
                left = i + 1;
        }
        return 0.0;
    }
}
