package DataStructureAndAlgorithms.Tests.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Triplet_Sum_In_Array;

public class Triplet_Sum_In_Array_Test extends Base_Test<List<Integer>, Triplet_Sum_In_Array> {

    public Triplet_Sum_In_Array_Test(Triplet_Sum_In_Array solution) {
        super(solution);
    }

    @Override
    protected List<Integer> test() {
        List<Integer> answer = new ArrayList<>();
        for (int i = 0; i < solution.arr.length - 2; i++) {
            int left = i + 1;
            int right = solution.arr.length - 1;
            while (left < right) {
                if (solution.arr[i] + solution.arr[left] + solution.arr[right] == solution.key) {
                    answer.add(solution.arr[i]);
                    answer.add(solution.arr[left]);
                    answer.add(solution.arr[right]);
                    return answer;
                } else if (solution.arr[left] + solution.arr[right] > solution.key) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return answer;
    }

}
