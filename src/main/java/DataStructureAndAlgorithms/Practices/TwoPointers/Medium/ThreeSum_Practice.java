package DataStructureAndAlgorithms.Practices.TwoPointers.Medium;

import DataStructureAndAlgorithms.Problems.TwoPointers.Medium.ThreeSum;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Practice(problemName = "Three Sum", category = "Two Pointers", difficulty = "Medium")
public class ThreeSum_Practice extends BasePractice<List<List<Integer>>, ThreeSum> {

    public ThreeSum_Practice(ThreeSum problem) {
        super(problem);
    }

    @Override
    public List<List<Integer>> practice() {
        List<List<Integer>> answer = new ArrayList<>();
        Arrays.sort(problem.nums);
        for (int i = 0; i < problem.nums.length - 2; i++) {
            if (i > 0 && problem.nums[i] == problem.nums[i - 1])
                continue;
            int left = i + 1;
            int right = problem.nums.length - 1;
            while (left < right) {
                if (problem.nums[left] + problem.nums[right] + problem.nums[i] == 0) {
                    answer.add(List.of(problem.nums[left], problem.nums[right], problem.nums[i]));
                    while (left < right && problem.nums[left] == problem.nums[left + 1]) left++;
                    while (left < right && problem.nums[right] == problem.nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (problem.nums[left] + problem.nums[right] + problem.nums[i] > 0) {
                    right--;
                } else
                    left++;
            }
        }

        return answer;
    }
}
