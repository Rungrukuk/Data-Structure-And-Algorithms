package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Solution;

public class Triplet_Sum_In_Array extends Base_Solution<List<Integer>> {

    public final int[] arr = { 1, 2, 3, 4, 5 };
    public final int key = 10;

    @Override
    protected List<Integer> solve() {
        List<Integer> answer = new ArrayList<>();
        for (int i = 0; i < arr.length - 2; i++) {
            int left = i + 1;
            int right = arr.length - 1;
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                if (sum == key) {
                    answer.add(arr[i]);
                    answer.add(arr[left]);
                    answer.add(arr[right]);
                    return answer;
                } else if (sum < key) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return answer;
    }
}