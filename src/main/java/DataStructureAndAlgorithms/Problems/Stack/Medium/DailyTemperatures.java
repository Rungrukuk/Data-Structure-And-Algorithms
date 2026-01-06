package DataStructureAndAlgorithms.Problems.Stack.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayDeque;
import java.util.Deque;

@Problem(name = "Daily Temperatures", category = "Stack", difficulty = "Medium")
public class DailyTemperatures extends BaseProblem<int[]> {
    int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};

    @Override
    public int[] solve() {
        int n = temperatures.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                answer[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }

        return answer;
    }
}
