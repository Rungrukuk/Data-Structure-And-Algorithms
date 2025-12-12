package DataStructureAndAlgorithms.Practices.BinarySearch.Medium;

import DataStructureAndAlgorithms.Problems.BinarySearch.Medium.FindKClosestElements;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

import java.util.ArrayList;
import java.util.List;

@Practice(problemName = "Find K Closest Elements", category = "Binary Search", difficulty = "Medium")
public class FindKClosestElements_Practice extends BasePractice<List<Integer>, FindKClosestElements> {

    public FindKClosestElements_Practice(FindKClosestElements problem) {
        super(problem);
    }

    @Override
    public List<Integer> practice() {

        int left = 0;
        int right = problem.arr.length - problem.k;

        while (left<right){
            int middle = left + (right-left)/2;

            if (problem.x-problem.arr[middle]>problem.arr[middle+problem.k]-problem.x)
                left = middle+1;
            else
                right = middle;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < problem.k; i++) {
            result.add(problem.arr[i+left]);
        }
        return result;
    }
}
