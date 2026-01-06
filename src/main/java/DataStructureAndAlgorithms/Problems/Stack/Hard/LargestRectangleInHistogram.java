package DataStructureAndAlgorithms.Problems.Stack.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.Stack;

@Problem(name = "Largest Rectangle In Histogram", category = "Stack", difficulty = "Hard")
public class LargestRectangleInHistogram extends BaseProblem<Integer> {
    public int[] heights = new int[]{2, 1, 5, 6, 2, 3};

    @Override
    public Integer solve() {
        int n = heights.length;
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                int width = i - left - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }
}
