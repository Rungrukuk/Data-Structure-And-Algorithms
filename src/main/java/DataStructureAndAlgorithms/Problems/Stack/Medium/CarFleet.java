package DataStructureAndAlgorithms.Problems.Stack.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

@Problem(name = "Car Fleet", category = "Two Pointers", difficulty = "Medium")
public class CarFleet extends BaseProblem<Integer> {
    public int target = 12;
    public int[] position = new int[]{10, 8, 0, 5, 3};
    public int[] speed = new int[]{2, 4, 1, 1, 3};

    @Override
    public Integer solve() {
        int n = position.length;
        int[][] cars = new int[n][2];

        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }

        Arrays.sort(cars, (a, b) -> Integer.compare(b[0], a[0]));

        Deque<Double> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            double time = (double) (target - cars[i][0]) / cars[i][1];

            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
        }

        return stack.size();
    }
}
