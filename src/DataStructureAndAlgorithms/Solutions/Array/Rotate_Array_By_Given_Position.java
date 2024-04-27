package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Solution;

public class Rotate_Array_By_Given_Position extends Base_Solution<List<Integer>> {

    public final int[] arr = { 3, 4, 5, 6, 7, 1, 2 };
    public final int position = 2;

    @Override
    public List<Integer> solve() {
        List<Integer> answer = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (i + position < arr.length) {
                answer.add(i, arr[position + i]);
            } else {
                answer.add(i, arr[position + i - arr.length]);
            }

        }
        return answer;
    }

}
