package DataStructureAndAlgorithms.Tests.Array;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Top_K_Frequent;

public class Top_K_Frequent_Test extends Base_Test<List<Integer>, Top_K_Frequent> {

    public Top_K_Frequent_Test(Top_K_Frequent solution) {
        super(solution);
    }

    @Override
    protected List<Integer> test() {
        List<Integer> answer = new ArrayList<>();
        int[] temp = new int[solution.arr.length];
        Map<Integer, Integer> freqMap = new HashMap<>();

        for (Integer integer : solution.arr) {
            freqMap.put(integer, freqMap.getOrDefault(integer, 0) + 1);
        }

        for (Entry<Integer, Integer> entry : freqMap.entrySet()) {
            temp[entry.getValue()] = entry.getKey();
        }

        for (int i = temp.length - 1; i >= 0 && answer.size() != solution.key; i--) {
            if (temp[i] != 0) {
                answer.add(temp[i]);
            }
        }
        return answer;
    }

}
