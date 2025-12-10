package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.FirstBadVersion;

@Practice(problemName = "First Bad Version", category = "Binary Search")
public class FirstBadVersion_Practice extends BasePractice<Integer, FirstBadVersion> {

    public FirstBadVersion_Practice(FirstBadVersion problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.n;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (problem.isBadVersion(middle)) {
                right = middle;
            } else
                left = middle + 1;
        }
        return left;
    }
}
