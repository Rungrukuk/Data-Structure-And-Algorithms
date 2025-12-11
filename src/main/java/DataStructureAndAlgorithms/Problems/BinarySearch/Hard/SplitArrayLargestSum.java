package DataStructureAndAlgorithms.Problems.BinarySearch.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Split Array Largest Sum", category = "Binary Search", difficulty = "Hard")
public class SplitArrayLargestSum extends BaseProblem<Integer> {
    int[] nums = new int[]{7, 2, 5, 10, 8};
    int k = 2;

    @Override
    public Integer solve() {
        int left = 0;
        int right = 0;
        for (int i : nums) {
            if (left < i) {
                left = i;
            }
            right += i;
        }

        while (left < right) {
            int middle = left + (right - left) / 2;
            int sum = 0;
            int count = 1;
            for (int i : nums) {
                if (i + sum > middle) {
                    count++;
                    sum = i;
                } else
                    sum += i;
            }
            if (count <= k) {
                right = middle;
            } else
                left = middle + 1;
        }
        return left;
    }
}
