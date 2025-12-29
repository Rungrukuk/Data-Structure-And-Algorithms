package DataStructureAndAlgorithms.Problems.TwoPointers.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Valid Palindrome", category = "Two Pointers", difficulty = "Easy")
public class ValidPalindrome extends BaseProblem<Boolean> {
    public String s = "A man, a plan, a canal: Panama";

    @Override
    public Boolean solve() {
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int left = 0, right = s.length() - 1;

        while (left <= right) {
            if (s.charAt(left) != s.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }
}
