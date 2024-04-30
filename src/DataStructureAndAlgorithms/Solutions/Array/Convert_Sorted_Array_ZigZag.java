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
        for (Integer integer : arr) {
            answer.add(integer);

        }
        for (int i = 1; i <= arr.length - 2; i += 2) {
            temp = answer.get(i);
            answer.set(i, answer.get(i + 1));
            answer.set(i + 1, temp);
        }

        return answer;
    }

}
