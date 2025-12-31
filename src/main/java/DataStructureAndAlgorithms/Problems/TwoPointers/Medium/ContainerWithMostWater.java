package DataStructureAndAlgorithms.Problems.TwoPointers.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Container With Most Water", category = "Two Pointers", difficulty = "Medium")
public class ContainerWithMostWater extends BaseProblem<Integer> {
    public int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};

    @Override
    public Integer solve() {
        int maxSum = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int tempSum = Math.min(height[left], height[right]) * (right - left);
            if (tempSum > maxSum)
                maxSum = tempSum;
            if (height[left] > height[right])
                right--;
            else
                left++;
        }
        return maxSum;
    }
}
