package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.FindKClosestElements;

import java.util.ArrayList;
import java.util.List;

@Practice(problemName = "Find K Closest Elements", category = "Binary Search")
public class FindKClosestElements_Practice extends BasePractice<List<Integer>, FindKClosestElements> {

    public FindKClosestElements_Practice(FindKClosestElements problem) {
        super(problem);
    }

    @Override
    public List<Integer> practice() {
        int left = 0;
        int right = problem.arr.length - problem.k;
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (problem.x - problem.arr[mid] > problem.arr[mid + problem.k] - problem.x) {
                left = mid + 1;
            } else
                right = mid;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + problem.k; i++) {
            result.add(problem.arr[i]);
        }

        return result;
    }
}
