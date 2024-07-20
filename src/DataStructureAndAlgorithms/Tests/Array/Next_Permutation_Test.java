package DataStructureAndAlgorithms.Tests.Array;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Next_Permutation;

public class Next_Permutation_Test extends Base_Test<List<Integer>, Next_Permutation> {

    public Next_Permutation_Test(Next_Permutation solution) {
        super(solution);
    }

    @Override
    protected List<Integer> test() {
        int n = solution.arr.length;
        int[] temp = Arrays.copyOf(solution.arr, n);
        int k = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (temp[i] < temp[i + 1]) {
                k = i;
                break;
            }
        }

        if (k == -1) {
            reverse(temp, 0, n - 1);
        } else {
            int l = -1;
            for (int i = n - 1; i > k; i--) {
                if (temp[i] > temp[k]) {
                    l = i;
                    break;
                }
            }
            swap(temp, k, l);
            reverse(temp, k + 1, n - 1);
        }
        List<Integer> answer = Arrays.stream(temp).boxed().collect(Collectors.toList());
        return answer;
    }

    private void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private void reverse(int[] arr, int left, int right) {
        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
    }

}
