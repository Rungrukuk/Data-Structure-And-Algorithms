package DataStructureAndAlgorithms.Problems.Binary_Search;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.core.Base_Problem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "Find Closest Elements", category = "Binary Search")
public class Find_Closest_Elements extends Base_Problem<List<Integer>> {
    public final int[] arr = new int[] { 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    public final int k = 3;
    public final int x = 9;

    @Override
    protected List<Integer> solve() {
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
