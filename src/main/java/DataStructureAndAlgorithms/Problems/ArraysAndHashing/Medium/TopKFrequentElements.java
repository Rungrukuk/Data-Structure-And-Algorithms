package DataStructureAndAlgorithms.Problems.ArraysAndHashing.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Problem(name = "Top K Frequent Elements", category = "Arrays And Hashing", difficulty = "Medium")
public class TopKFrequentElements extends BaseProblem<int[]> {
    public int[] nums = new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3};
    public int k = 2;

    @Override
    public int[] solve() {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        int n = nums.length;
        List<Integer>[] buckets = new List[n + 1];
        for (int key : freq.keySet()) {
            int f = freq.get(key);
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(key);
        }
        int[] answer = new int[k];
        int count = 0;

        for (int i = buckets.length - 1; i > 0 && count < k; i--) {
            if (buckets[i] == null) continue;

            for (int j = buckets[i].size() - 1; j >= 0 && count < k; j--) {
                answer[count] = buckets[i].get(j);
                count++;
            }
        }
        return answer;
    }
}
