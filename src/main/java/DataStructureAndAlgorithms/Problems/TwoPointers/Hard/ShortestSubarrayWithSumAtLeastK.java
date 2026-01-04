package DataStructureAndAlgorithms.Problems.TwoPointers.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayDeque;
import java.util.Deque;

@Problem(name = "Shortest Subarray With Sum At Least K", category = "Two Pointers", difficulty = "Hard")
public class ShortestSubarrayWithSumAtLeastK extends BaseProblem<Integer> {
    public int[] nums = new int[]{77, 19, 35, 10, -14};
    public int k = 19;

    @Override
    public Integer solve() {
        int n = nums.length;
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++)
            prefix[i + 1] = prefix[i] + nums[i];

        Deque<Integer> deque = new ArrayDeque<>();
        int ans = Integer.MAX_VALUE;

        for (int i = 0; i <= n; i++) {

            while (!deque.isEmpty() && prefix[i] - prefix[deque.peekFirst()] >= k) {
                ans = Math.min(ans, i - deque.pollFirst());
            }

            while (!deque.isEmpty() && prefix[i] <= prefix[deque.peekLast()]) {
                deque.pollLast();
            }

            deque.addLast(i);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
