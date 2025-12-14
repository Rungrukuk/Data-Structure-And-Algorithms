package DataStructureAndAlgorithms.Practices.BinarySearch.Hard;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.Hard.KthSmallestProductOfTwoSortedArrays;

@Practice(problemName = "Kth Smallest Product Of Two Sorted Arrays", category = "Binary Search", difficulty = "Hard")
public class KthSmallestProductOfTwoSortedArrays_Practice extends BasePractice<Long, KthSmallestProductOfTwoSortedArrays> {

    public KthSmallestProductOfTwoSortedArrays_Practice(KthSmallestProductOfTwoSortedArrays problem) {
        super(problem);
    }

    @Override
    public Long practice() {
        int maxAbs1 = Math.max(Math.abs(problem.nums1[0]), Math.abs(problem.nums1[problem.nums1.length-1]));
        int maxAbs2 = Math.max(Math.abs(problem.nums2[0]), Math.abs(problem.nums2[problem.nums2.length-1]));
        long right = (long)maxAbs1 * maxAbs2;
        long left = -right;
        while (left<right){
            long middle = left + (right-left)/2;
            if (countProducts(problem.nums1, problem.nums2, middle)<problem.k)
                left = middle + 1;
            else
                right = middle;
        }
        return left;
    }

    long countProducts(int[] nums1, int[] nums2, long v){
        long count = 0;
        for (int i: nums1) {
            if (i>0){
                int left = 0;
                int right = nums2.length-1;
                while (left<right){
                    int mid = left + (right-left)/2;
                    if ((long) nums2[mid]*i<=v){
                        left = mid+1;
                    }
                    else
                        right = mid;
                }
                count+=left;
            }
            else if (i<0){
                int left = 0;
                int right = nums2.length-1;
                while (left<right){
                    int mid = left + (right-left)/2;
                    if ((long)nums2[mid]*i>v){
                        left = mid+1;
                    }
                    else
                        right = mid;
                }
                count+=nums2.length - left;
            }
            else {
                if (v>=0)
                    count = nums2.length;

            }
        }
        return count;
    }
}
