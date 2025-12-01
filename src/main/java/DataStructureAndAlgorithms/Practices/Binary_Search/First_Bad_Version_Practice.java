package DataStructureAndAlgorithms.Practices.Binary_Search;

import DataStructureAndAlgorithms.Problems.Binary_Search.First_Bad_Version;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "First Bad Version", category = "Binary Search")
public class First_Bad_Version_Practice extends BasePractice<Integer, First_Bad_Version> {

    public First_Bad_Version_Practice(First_Bad_Version problem) {
        super(problem);
    }

    @Override
    public Integer practice() {

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
