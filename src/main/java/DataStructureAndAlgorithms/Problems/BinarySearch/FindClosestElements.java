package DataStructureAndAlgorithms.Problems.BinarySearch;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Find Closest Elements", category = "Binary Search")
public class FindClosestElements extends BaseProblem<List<Integer>> {
    public final int[] arr = new int[] { 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    public final int k = 3;
    public final int x = 9;

    @Override
    public List<Integer> solve() {
        int left = 0;
        int right = arr.length - k;

        while (left < right) {
            int middle = left + (right - left) / 2;

            if (x - arr[middle] > arr[middle + k] - x) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }

        return result;
    }

}
