package DataStructureAndAlgorithms.Problems.ArraysAndHashing.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.HashSet;
import java.util.Set;

@Problem(name = "Longest Consecutive Sequence", category = "Arrays And Hashing", difficulty = "Medium")
public class LongestConsecutiveSequence extends BaseProblem<Integer> {
    public int[] nums = new int[]{100, 4, 200, 1, 3, 2};

    @Override
    public Integer solve() {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int longest = 0;

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int current = num;
                int length = 1;

                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }

                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
