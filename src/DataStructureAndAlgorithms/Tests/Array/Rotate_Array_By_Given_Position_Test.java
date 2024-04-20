package DataStructureAndAlgorithms.Tests.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Rotate_Array_By_Given_Position;

public class Rotate_Array_By_Given_Position_Test extends Base_Test<List<Integer>, Rotate_Array_By_Given_Position> {

    public Rotate_Array_By_Given_Position_Test(Rotate_Array_By_Given_Position solution) {
        super(solution);
    }

    @Override
    protected List<Integer> test() {
        List<Integer> answer = new ArrayList<Integer>();
        int j = 0;
        for (int i = solution.arr.length - 1; i >= 0; i--) {
            answer.add(j, solution.arr[(solution.arr.length - i + solution.position - 1) % solution.arr.length]);
            j++;
        }
        return answer;
    }

}
