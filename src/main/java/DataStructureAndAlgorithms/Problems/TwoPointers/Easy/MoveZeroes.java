package DataStructureAndAlgorithms.Problems.TwoPointers.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Move Zeroes", category = "Two Pointers", difficulty = "Easy")
public class MoveZeroes extends BaseProblem<int[]> {
    int[] nums = new int[]{0, 1, 0, 3, 12};

    @Override
    public int[] solve() {
        int[] answer = new int[nums.length];
        int left = 0;

        for (int number : nums) {
            if (number != 0) {
                answer[left] = number;
                left++;
            }
        }
        for (int i = left; i < answer.length; i++) {
            answer[i] = 0;
        }
        return answer;
    }
}
