package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "First Bad Version", category = "Binary Search")
public class First_Bad_Version extends BaseProblem<Integer> {
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
