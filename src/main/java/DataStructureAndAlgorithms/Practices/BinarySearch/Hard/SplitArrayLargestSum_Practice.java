package DataStructureAndAlgorithms.Practices.BinarySearch.Hard;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.Hard.SplitArrayLargestSum;

@Practice(problemName = "Split Array Largest Sum", category = "Binary Search", difficulty = "Hard")
public class SplitArrayLargestSum_Practice extends BasePractice<Integer, SplitArrayLargestSum> {

    public SplitArrayLargestSum_Practice(SplitArrayLargestSum problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right =0;
        for (int i : problem.nums){
            if (left < i) {
                left = i;
            }
            right+=i;
        }
        while(left<right){
            int middle = left + (right-left)/2;
            int count  = 1;
            int sum = 0;
            for (int i = 0; i < problem.nums.length; i++) {
                sum += problem.nums[i];
                if (sum>middle){
                    sum = problem.nums[i];
                    count++;
                }
            }
            if (count>problem.k){
                left = middle+1;
            }
            else
                right = middle;

        }
        return left;
    }
}
