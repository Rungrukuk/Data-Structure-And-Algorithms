package DataStructureAndAlgorithms.Tests.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Convert_Sorted_Array_ZigZag;

public class Convert_Sorter_Array_ZigZag_Test extends Base_Test<List<Integer>, Convert_Sorted_Array_ZigZag> {

    public Convert_Sorter_Array_ZigZag_Test(Convert_Sorted_Array_ZigZag solution) {
        super(solution);
    }

    @Override
    protected List<Integer> test() {
        List<Integer> answer = new ArrayList<Integer>();
        int temp = 0;
        for (Integer integer : solution.arr) {
            answer.add(integer);

        }
        for (int i = 1; i <= solution.arr.length - 2; i += 2) {
            temp = answer.get(i);
            answer.set(i, answer.get(i + 1));
            answer.set(i + 1, temp);
        }
        return answer;
    }

}
