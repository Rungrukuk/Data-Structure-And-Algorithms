package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "Guess Number Higher Or Lower", category = "Binary Search")
public class Guess_Number_Higher_Or_Lower extends BaseProblem<Integer> {
    public final int n = 10;
    private final int pick = 6;

    @Override
    protected Integer solve() {
        int left = 0;
        int right = n;
        while (left <= right) {
            final int middle = left + (right - left) / 2;
            if (guess(middle) == -1) {
                right = middle - 1;
            } else if (guess(middle) == 1) {
                left = middle + 1;
            } else
                return middle;
        }
        return left;
    }

    public int guess(int num) {
        if (num > pick) {
            return -1;
        } else if (num < pick) {
            return 1;
        }
        return 0;
    }

}
