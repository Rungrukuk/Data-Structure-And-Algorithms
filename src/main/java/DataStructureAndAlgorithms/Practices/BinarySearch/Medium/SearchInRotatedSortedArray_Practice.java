package DataStructureAndAlgorithms.Practices.BinarySearch.Medium;

import DataStructureAndAlgorithms.Problems.BinarySearch.Medium.SearchInRotatedSortedArray;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "Search In Rotated Sorted Array", category = "Binary Search", difficulty = "Medium")
public class SearchInRotatedSortedArray_Practice extends BasePractice<Integer, SearchInRotatedSortedArray> {

    public SearchInRotatedSortedArray_Practice(SearchInRotatedSortedArray problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int left = 0;
        int right = problem.nums.length-1;
        while (left<=right){
            int middle = left + (right-left)/2;
            if (problem.nums[middle]== problem.target)
                return middle;
            if (problem.nums[middle]>=problem.nums[left]){
                if (problem.nums[left]<= problem.target&& problem.target< problem.nums[middle])
                   right = middle-1;
                else
                    left = middle+1;
            }
            else{
                if (problem.nums[middle]< problem.target&&problem.target<=problem.nums[right])
                    left = middle+1;
                else
                    right = middle-1;
            }

        }
        return -1;
    }
}
