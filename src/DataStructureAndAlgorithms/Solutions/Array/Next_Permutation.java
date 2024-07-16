package DataStructureAndAlgorithms.Solutions.Array;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import DataStructureAndAlgorithms.Base_Solution;

public class Next_Permutation extends Base_Solution<List<Integer>> {

    public final int[] arr = { 1, 2, 3, 3, 3, 4, 5, 2, 1, 1, 2, 4, 5, 6, 3, 2, 1 };
    // Expected 1,2,3,3,3,4,5,2,1,1,2,4,6,1,2,3,5

    @Override
    protected List<Integer> solve() {
        int n = arr.length;
        int k = -1;
        int[] temp = Arrays.copyOf(arr, n);
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] < arr[i + 1]) {
                k = i;
                break;
            }
        }

        if (k == -1) {
            reverse(temp, 0, n - 1);
        } else {
            int l = -1;
            for (int i = n - 1; i > k; i--) {
                if (arr[i] > arr[k]) {
                    l = i;
                    break;
                }
            }

            swap(temp, k, l);

            reverse(temp, k + 1, n - 1);
        }
        List<Integer> answer = Arrays.stream(temp)
                .boxed()
                .collect(Collectors.toList());

        return answer;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

}
