package DataStructureAndAlgorithms.Problems.TwoPointers.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Minimum Window Substring", category = "Two Pointers", difficulty = "Hard")
public class MinimumWindowSubstring extends BaseProblem<String> {
    public String s = "ADOBECODEBANC";
    public String t = "ABC";

    @Override
    public String solve() {
        if (s.length() < t.length()) return "";

        int[] need = new int[128];
        for (char c : t.toCharArray())
            need[c]++;
        int left = 0, right = 0;
        int missing = t.length();
        int start = 0, minLen = Integer.MAX_VALUE;

        while (right < s.length()) {
            char c = s.charAt(right);
            if (need[c] > 0)
                missing--;
            need[c]--;
            right++;

            while (missing == 0) {
                if (right - left < minLen) {
                    minLen = right - left;
                    start = left;
                }

                char lc = s.charAt(left);
                need[lc]++;
                if (need[lc] > 0)
                    missing++;
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
