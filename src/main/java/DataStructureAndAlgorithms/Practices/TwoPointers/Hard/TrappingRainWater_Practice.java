package DataStructureAndAlgorithms.Practices.TwoPointers.Hard;

import DataStructureAndAlgorithms.Problems.TwoPointers.Hard.TrappingRainWater;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "Trapping Rain Water", category = "Two Pointers", difficulty = "Hard")
public class TrappingRainWater_Practice extends BasePractice<Integer, TrappingRainWater> {

    public TrappingRainWater_Practice(TrappingRainWater problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.height.length - 1;
        int maxLeft = 0;
        int maxRight = 0;
        int water = 0;
        while (left < right) {
            if (problem.height[left] < problem.height[right]) {
                if (problem.height[left] >= maxLeft) {
                    maxLeft = problem.height[left];
                } else
                    water += maxLeft - problem.height[left];
                left++;
            } else {
                if (problem.height[right] >= maxRight) {
                    maxRight = problem.height[right];
                } else
                    water += maxRight - problem.height[right];
                right--;
            }
        }

        return water;
    }
}
