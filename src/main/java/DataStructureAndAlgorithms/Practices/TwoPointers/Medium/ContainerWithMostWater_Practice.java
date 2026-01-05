package DataStructureAndAlgorithms.Practices.TwoPointers.Medium;

import DataStructureAndAlgorithms.Problems.TwoPointers.Medium.ContainerWithMostWater;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "Container With Most Water", category = "Two Pointers", difficulty = "Medium")
public class ContainerWithMostWater_Practice extends BasePractice<Integer, ContainerWithMostWater> {

    public ContainerWithMostWater_Practice(ContainerWithMostWater problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.height.length - 1;
        int maxWater = 0;
        while (left < right) {
            maxWater = Math.max(maxWater, Math.min(problem.height[left], problem.height[right]) * (right - left));
            if (problem.height[left] < problem.height[right])
                left++;
            else
                right--;
        }
        return maxWater;
    }
}
