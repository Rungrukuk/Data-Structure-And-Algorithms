package DataStructureAndAlgorithms.Practices.BinarySearch.Hard;

import DataStructureAndAlgorithms.Problems.BinarySearch.Hard.FindKthSmallestPairDistance;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

import java.util.Arrays;

@Practice(problemName = "Find Kth Smallest Pair Distance", category = "Binary Search", difficulty = "Hard")
public class FindKthSmallestPairDistance_Practice extends BasePractice<Integer, FindKthSmallestPairDistance> {

    public FindKthSmallestPairDistance_Practice(FindKthSmallestPairDistance problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        Arrays.sort(problem.nums);
        int left = 0;
        int right = problem.nums[problem.nums.length-1] - problem.nums[0];
        while (left<right){
            int middle = left + (right-left)/2;
            if (countLessAndEqual(problem.nums, middle)<problem.k)
                left = middle + 1;
            else
                right = middle;
        }
        return left;
    }

    int countLessAndEqual(int[] nums, int m){
        int count = 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            while(j<nums.length && nums[j]-nums[i]<=m){
                j++;
            }
            count+= j - i -1;
        }

        return count;
    }
}
