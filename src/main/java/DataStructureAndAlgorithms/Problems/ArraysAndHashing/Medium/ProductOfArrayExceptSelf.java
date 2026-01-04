package DataStructureAndAlgorithms.Problems.ArraysAndHashing.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Product Of Array Except Self", category = "Arrays And Hashing", difficulty = "Medium")
public class ProductOfArrayExceptSelf extends BaseProblem<int[]> {
    public int[] nums = new int[]{1, 2, 3, 4};

    @Override
    public int[] solve() {
        int n = nums.length;
        int[] answer = new int[n];

        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= rightProduct;
            rightProduct *= nums[i];
        }

        return answer;
    }
}
