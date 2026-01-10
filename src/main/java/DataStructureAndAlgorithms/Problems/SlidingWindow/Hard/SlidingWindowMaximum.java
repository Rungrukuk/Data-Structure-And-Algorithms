package DataStructureAndAlgorithms.Problems.SlidingWindow.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayDeque;
import java.util.Deque;

@Problem(name = "Sliding Window Maximum", category = "Sliding Window", difficulty = "Hard")
public class SlidingWindowMaximum extends BaseProblem<int[]> {
    public int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
    public int k = 3;

    @Override
    public int[] solve() {
        if (nums.length == 0 || k == 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {

            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }

            dq.offerLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[dq.peekFirst()];
            }
        }

        return result;
    }
}
