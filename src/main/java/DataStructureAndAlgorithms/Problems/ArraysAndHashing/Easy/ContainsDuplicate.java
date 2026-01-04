package DataStructureAndAlgorithms.Problems.ArraysAndHashing.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.HashSet;

@Problem(name = "Contains Duplicate", category = "Arrays And Hashing", difficulty = "Easy")
public class ContainsDuplicate extends BaseProblem<Boolean> {
    public int[] nums = new int[]{1, 2, 3, 1};

    @Override
    public Boolean solve() {
        HashSet<Integer> bucket = new HashSet<>();
        for (int i : nums) {
            if (bucket.contains(i))
                return true;
            bucket.add(i);
        }
        return false;
    }
}
