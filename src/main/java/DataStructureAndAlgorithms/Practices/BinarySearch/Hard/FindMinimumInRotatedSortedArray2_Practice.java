package DataStructureAndAlgorithms.Practices.BinarySearch.Hard;

import DataStructureAndAlgorithms.Problems.BinarySearch.Hard.FindMinimumInRotatedSortedArray2;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "Find Minimum In Rotated Sorted Array 2", category = "Binary Search", difficulty = "Hard")
public class FindMinimumInRotatedSortedArray2_Practice extends BasePractice<Integer, FindMinimumInRotatedSortedArray2> {

    public FindMinimumInRotatedSortedArray2_Practice(FindMinimumInRotatedSortedArray2 problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.nums.length-1;
        while (left<right)
        {
            int middle = left+(right-left)/2;
            if (problem.nums[middle]>problem.nums[right]){
                left = middle+1;
            }
            else if (problem.nums[middle]<problem.nums[right]){
                right = middle;
            }
            else
                right--;
        }
        return problem.nums[left];
    }
}
