package DataStructureAndAlgorithms.Problems.TwoPointers.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Sort Colors", category = "Two Pointers", difficulty = "Medium")
public class SortColors extends BaseProblem<int[]> {
    public int[] nums = new int[]{2, 0, 2, 1, 0, 1};

    @Override
    public int[] solve() {
        int[] answer = nums.clone();
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (answer[mid] == 0) {
                swap(answer, mid, low);
                mid++;
                low++;
            } else if (answer[mid] == 1) {
                mid++;
            } else {
                swap(answer, mid, high);
                high--;
            }
        }
        return answer;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
