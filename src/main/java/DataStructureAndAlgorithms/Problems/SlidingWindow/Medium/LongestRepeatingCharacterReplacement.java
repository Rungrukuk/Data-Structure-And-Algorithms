package DataStructureAndAlgorithms.Problems.SlidingWindow.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Longest Repeating Character Replacement", category = "Sliding Window", difficulty = "Medium")
public class LongestRepeatingCharacterReplacement extends BaseProblem<Integer> {
    public String s = "AABABBA";
    public int k = 1;

    @Override
    public Integer solve() {
        int[] freq = new int[26];
        int maxFreq = 0;
        int left = 0;
        int maxLen = 0;
        for (int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'A']++;
            maxFreq = Math.max(freq[s.charAt(right) - 'A'], maxFreq);
            while (right - left + 1 - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
