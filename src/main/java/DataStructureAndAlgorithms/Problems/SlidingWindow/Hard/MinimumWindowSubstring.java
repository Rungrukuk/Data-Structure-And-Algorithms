package DataStructureAndAlgorithms.Problems.SlidingWindow.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;


@Problem(name = "Minimum Window Substring", category = "Sliding Window", difficulty = "Hard")
public class MinimumWindowSubstring extends BaseProblem<String> {
    public String s = "aabbcdea";
    public String t = "abcde";

    @Override
    public String solve() {
        if (s.length() < t.length()) return "";

        int[] need = new int[58];

        for (char c : t.toCharArray()) {
            need[c - 'A']++;
        }

        int left = 0;
        int missing = t.length();
        int minLen = Integer.MAX_VALUE;
        int start = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (need[c - 'A'] > 0) {
                missing--;
            }
            need[c - 'A']--;

            while (missing == 0) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }

                char l = s.charAt(left);
                need[l - 'A']++;
                if (need[l - 'A'] > 0) {
                    missing++;
                }
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
