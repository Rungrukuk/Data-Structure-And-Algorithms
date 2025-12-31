package DataStructureAndAlgorithms.Problems.TwoPointers.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Problem(name = "Four Sum", category = "Two Pointers", difficulty = "Medium")
public class FourSum extends BaseProblem<List<List<Integer>>> {
    public int[] nums = new int[]{1, 0, -1, 0, -2, 2};
    public int target = 0;


    @Override
    public List<List<Integer>> solve() {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;
                int left = j + 1;
                int right = nums.length - 1;
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else
                        right--;
                }
            }
        }
        return result;
    }
}
