package DataStructureAndAlgorithms.Problems.ArraysAndHashing.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.HashMap;

@Problem(name = "Two Sum", category = "Arrays And Hashing", difficulty = "Easy")
public class TwoSum extends BaseProblem<int[]> {
    public int[] nums = new int[]{2, 7, 11, 15};
    public int target = 9;

    @Override
    public int[] solve() {
        int[] answer = new int[2];
        HashMap<Integer, Integer> bucket = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (bucket.containsKey(target - nums[i])) {
                answer[0] = i;
                answer[1] = bucket.get(target - nums[i]);
                break;
            }
            bucket.put(nums[i], i);
        }

        return answer;
    }
}
