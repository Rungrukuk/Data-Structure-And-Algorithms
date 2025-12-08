package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.GuessNumberHigherOrLower;

@Practice(problemName = "Guess Number Higher Or Lower", category = "Binary Search")
public class GuessNumberHigherOrLower_Practice extends BasePractice<Integer, GuessNumberHigherOrLower> {

    public GuessNumberHigherOrLower_Practice(GuessNumberHigherOrLower problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.n;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (problem.guess(middle) == 0) {
                return middle;
            } else if (problem.guess(middle) == -1) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }

        }
        return left;
    }
}
