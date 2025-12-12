package DataStructureAndAlgorithms.Practices.BinarySearch.Medium;

import DataStructureAndAlgorithms.Problems.BinarySearch.Medium.FindPeakElement;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "Find Peak Element", category = "Binary Search", difficulty = "Medium")
public class FindPeakElement_Practice extends BasePractice<Integer, FindPeakElement> {

    public FindPeakElement_Practice(FindPeakElement problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.nums.length-1;
        while (left<right){
            int middle = left + (right-left)/2;
            if (problem.nums[middle]<problem.nums[middle+1])
                left = middle+1;
            else
                right = middle;
        }
        return problem.nums[left];
    }
}
