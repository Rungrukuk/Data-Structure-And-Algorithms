package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.Base_Practice;
import DataStructureAndAlgorithms.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.First_Bad_Version;

@Practice(problemName = "First Bad Version", category = "Binary Search")
public class First_Bad_Version_Practice extends Base_Practice<Integer, First_Bad_Version> {

    public First_Bad_Version_Practice(First_Bad_Version problem) {
        super(problem);
    }

    @Override
    protected Integer practice() {

        int left = 0;
        int right = problem.n;

        while (left <= right) {
            final int middle = left + (right - left) / 2;
            if (problem.isBadVersion(middle))
                right = middle - 1;
            else
                left = middle + 1;
        }

        return left;
    }

}
