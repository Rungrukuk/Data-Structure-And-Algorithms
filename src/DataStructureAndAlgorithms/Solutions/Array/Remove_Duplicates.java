package DataStructureAndAlgorithms.Solutions.Array;

import java.util.Arrays;

import DataStructureAndAlgorithms.Base_Solution;

public class Remove_Duplicates extends Base_Solution<Integer> {

    public int[] arr = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };

    @Override
    protected Integer solve() {
        int j = 1;
        int[] temp = Arrays.copyOf(arr, arr.length);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                continue;
            }
            temp[j] = arr[i];
            j++;
        }
        System.out.println("Original arr: " + Arrays.toString(temp));
        return j;
    }

}
