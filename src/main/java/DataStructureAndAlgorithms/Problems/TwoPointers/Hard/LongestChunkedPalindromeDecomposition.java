package DataStructureAndAlgorithms.Problems.TwoPointers.Hard;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Longest Chunked Palindrome Decomposition", category = "Two Pointers", difficulty = "Hard")
public class LongestChunkedPalindromeDecomposition extends BaseProblem<Integer> {
    public String text = "ghiabcdefhelloadamhelloabcdefghi";

    @Override
    public Integer solve() {
        int left = 0;
        int right = text.length() - 1;
        int result = 0;
        while (left <= right) {
            boolean found = false;
            for (int len = 1; left + len - 1 < right; len++) {
                if (text.substring(left, left + len).equals(
                        text.substring(right - len + 1, right + 1))) {
                    result += 2;
                    left += len;
                    right -= len;
                    found = true;
                    break;
                }
            }
            if (!found) {
                result += 1;
                break;
            }
        }
        return result;
    }
}
