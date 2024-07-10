package DataStructureAndAlgorithms.Solutions.Array;

import java.util.Arrays;

import DataStructureAndAlgorithms.Base_Solution;

public class Triplet_Sum_Nearest extends Base_Solution<Integer> {

    public final int[] arr = { -1, 2, 1, -4 };
    public final int key = 1;

    @Override
    protected Integer solve() {
        int answer = 0;
        int min = Integer.MAX_VALUE;
        Arrays.sort(arr);

        for (int i = 0; i < arr.length - 2; i++) {
            int left = i + 1;
            int right = arr.length - 1;
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                if (sum == key)
                    return sum;

                if (min > Math.abs(key - sum)) {
                    min = Math.abs(key - sum);
                    answer = sum;
                }
                if (sum < key)
                    left++;
                else
                    right--;
            }
        }

        return answer;
    }

}
