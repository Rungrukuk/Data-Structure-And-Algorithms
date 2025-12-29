package DataStructureAndAlgorithms.Problems.TwoPointers.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Reverse String", category = "Two Pointers", difficulty = "Easy")
public class ReverseString extends BaseProblem<char[]> {
    char[] s = new char[]{'h', 'e', 'l', 'l', 'o'};

    @Override
    public char[] solve() {
        char[] answer = new char[s.length];
        int left = 0;
        int right = s.length - 1;
        while (left <= right) {
            answer[left] = s[right];
            answer[right] = s[left];
            left++;
            right--;
        }
        return answer;
    }
}
