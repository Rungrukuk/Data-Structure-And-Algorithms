package DataStructureAndAlgorithms.Tests.Array;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Count_Pairs_With_Given_Sum;

public class Count_Pairs_With_Given_Sum_Test extends Base_Test<Integer, Count_Pairs_With_Given_Sum> {

    public Count_Pairs_With_Given_Sum_Test(Count_Pairs_With_Given_Sum solution) {
        super(solution);
    }

    @Override
    protected Integer test() {
        int answer = 0;
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < solution.arr.length; i++) {
            if (frequencyMap.containsKey(solution.key - solution.arr[i])) {
                answer += frequencyMap.get(solution.key - solution.arr[i]);
            }
            frequencyMap.put(solution.arr[i], frequencyMap.getOrDefault(solution.arr[i], 0) + 1);
        }
        return answer;
    }

}
