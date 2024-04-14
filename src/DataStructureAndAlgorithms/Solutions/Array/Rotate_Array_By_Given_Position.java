package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;

import DataStructureAndAlgorithms.ISolvable;

public class Rotate_Array_By_Given_Position implements ISolvable {

    private int[] arr;
    private int position;

    public Rotate_Array_By_Given_Position(int[] arr, int position) {
        this.arr = arr;
        this.position = position;
    }

    private ArrayList<Integer> rotateArray() {
        ArrayList<Integer> answer = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (i + position < arr.length) {
                answer.add(i, arr[position + i]);
            } else {
                answer.add(i, arr[position + i - arr.length]);
            }

        }
        return answer;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void solve(SolutionKind solutionKind) {
        System.out.println(rotateArray().toString());
    }

}
