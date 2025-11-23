package DataStructureAndAlgorithms.Problems.BinarySearch;

import DataStructureAndAlgorithms.Base_Problem;
import DataStructureAndAlgorithms.Problem;

@Problem(name = "First Bad Version", category = "Binary Search")
public class First_Bad_Version extends Base_Problem<Integer> {
    public final int n = 5;
    public final int badVersion = 4;

    @Override
    protected Integer solve() {
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
