package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.ISolvable;

public class Rotate_Array_By_Given_Position implements ISolvable<List<Integer>> {

    private int[] arr;
    private int position;

    public Rotate_Array_By_Given_Position(int[] arr, int position) {
        this.arr = arr;
        this.position = position;
    }

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

    @Override
    public void printAnswer() {
        System.out.println(solve().toString());
    }

}
