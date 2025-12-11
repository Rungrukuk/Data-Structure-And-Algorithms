package DataStructureAndAlgorithms.Problems.BinarySearch.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "First Bad Version", category = "Binary Search", difficulty = "Easy")
public class FirstBadVersion extends BaseProblem<Integer> {
    public final int n = 5;
    public final int badVersion = 4;

    @Override
    public Integer solve() {
        int left = 0;
        int right = n;
        while (left <= right) {
            final int middle = left + (right - left) / 2;
            if (isBadVersion(middle)) {
                right = middle - 1;
            } else
                left = middle + 1;
        }
        return left;
    }

    public Boolean isBadVersion(int version) {
        return version == badVersion;
    }

}
