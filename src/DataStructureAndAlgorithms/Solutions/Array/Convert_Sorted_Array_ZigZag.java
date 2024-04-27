package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Solution;

public class Convert_Sorted_Array_ZigZag extends Base_Solution<List<Integer>> {

    public final int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

    @Override
    protected List<Integer> solve() {
        List<Integer> answer = new ArrayList<Integer>();
        int temp = 0;
        for (int i = 1; i <= arr.length - 2; i += 2) {
            temp = arr[i];
            arr[i] = arr[i + 1];
            arr[i + 1] = temp;
        }
        for (int i : arr) {
            answer.add(i);
        }
        return answer;
    }

}
