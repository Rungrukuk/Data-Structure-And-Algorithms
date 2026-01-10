package DataStructureAndAlgorithms.Problems.SlidingWindow.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.Arrays;

@Problem(name = "Permutation In String", category = "Sliding Window", difficulty = "Medium")
public class PermutationInString extends BaseProblem<Boolean> {
    public String s1 = "ab";
    public String s2 = "eidbaooo";

    @Override
    public Boolean solve() {
        if (s1.length() > s2.length()) return false;

        int[] need = new int[26];
        int[] window = new int[26];

        for (char c : s1.toCharArray()) {
            need[c - 'a']++;
        }

        int left = 0;

        for (int right = 0; right < s2.length(); right++) {
            window[s2.charAt(right) - 'a']++;

            if (right - left + 1 > s1.length()) {
                window[s2.charAt(left) - 'a']--;
                left++;
            }

            if (Arrays.equals(need, window)) {
                return true;
            }
        }
        return false;
    }
}
