package DataStructureAndAlgorithms.Problems.TwoPointers.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Remove Duplicates From Sorted Array", category = "Two Pointers", difficulty = "Easy")
public class RemoveDuplicatesFromSortedArray extends BaseProblem<Integer> {
    int[] nums = new int[]{1, 1, 2};

    @Override
    public Integer solve() {
        int write = 1;

        for (int read = 1; read < nums.length; read++) {
            if (nums[read] != nums[read - 1]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;
    }
}
