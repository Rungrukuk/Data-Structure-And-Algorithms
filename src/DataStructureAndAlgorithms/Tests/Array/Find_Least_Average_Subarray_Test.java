package DataStructureAndAlgorithms.Tests.Array;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Find_Least_Average_Subarray;

public class Find_Least_Average_Subarray_Test extends Base_Test<Integer, Find_Least_Average_Subarray> {

    public Find_Least_Average_Subarray_Test(Find_Least_Average_Subarray solution) {
        super(solution);
    }

    @Override
    public Integer test() {
        int answer = 0;
        int sum = 0;
        for (int i = 0; i < solution.key; i++) {
            sum += solution.arr[i];
        }
        int minSum = sum;
        for (int i = solution.key; i < solution.arr.length; i++) {
            sum += solution.arr[i] - solution.arr[i - solution.key];
            if (minSum > sum) {
                minSum = sum;
                answer = i - solution.key + 2;
            }
        }
        return answer;
    }
}