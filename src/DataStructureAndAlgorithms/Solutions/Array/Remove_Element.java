package DataStructureAndAlgorithms.Solutions.Array;

import java.util.Arrays;

import DataStructureAndAlgorithms.Base_Solution;

public class Remove_Elements extends Base_Solution<Integer> {

    public final int[] arr = { 0, 1, 2, 2, 3, 0, 4, 2 };
    public final int k = 2;

    @Override
    protected Integer solve() {
        int j = 0;
        int[] temp = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == k) {
                continue;
            }
            temp[j] = arr[i];
            j++;
        }
        System.out.println("Final array: " + Arrays.toString(temp));
        return j;
    }

}
