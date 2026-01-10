package DataStructureAndAlgorithms.Problems.SlidingWindow.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.HashSet;
import java.util.Set;

@Problem(name = "Longest Substring Without Repeating Characters", category = "Sliding Window", difficulty = "Medium")
public class LongestSubstringWithoutRepeatingCharacters extends BaseProblem<Integer> {
    public String s = "abcabcbb";

    @Override
    public Integer solve() {
        Set<Character> set = new HashSet<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(s.charAt(right));
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
