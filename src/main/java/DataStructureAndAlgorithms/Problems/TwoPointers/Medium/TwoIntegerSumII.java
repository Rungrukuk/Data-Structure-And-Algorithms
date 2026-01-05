package DataStructureAndAlgorithms.Problems.TwoPointers.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Two Integer Sum II", category = "Two Pointers", difficulty = "Medium")
public class TwoIntegerSumII extends BaseProblem<int[]> {
    public int[] numbers = new int[]{2, 7, 11, 15};
    public int target = 9;

    @Override
    public int[] solve() {
        int[] answer = new int[2];
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                answer[0] = left + 1;
                answer[1] = right + 1;
                break;
            } else if (numbers[left] + numbers[right] > target) {
                right--;
            } else
                left++;
        }
        return answer;
    }
}
