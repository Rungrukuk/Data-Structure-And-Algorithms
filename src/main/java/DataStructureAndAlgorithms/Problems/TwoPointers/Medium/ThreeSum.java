package DataStructureAndAlgorithms.Problems.TwoPointers.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Problem(name = "Three Sum", category = "Two Pointers", difficulty = "Medium")
public class ThreeSum extends BaseProblem<List<List<Integer>>> {
    public int[] nums = new int[]{-1, 0, 1, 2, -1, -4};

    @Override
    public List<List<Integer>> solve() {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else
                    right--;
            }
        }

        return result;
    }
}
