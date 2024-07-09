package DataStructureAndAlgorithms.Tests.Array;

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
        List<List<Integer>> bucketList = new ArrayList<>(solution.arr.length);
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < solution.arr.length; i++) {
            bucketList.add(new ArrayList<>());
        }
        for (int i = 0; i < solution.arr.length; i++) {
            freqMap.put(solution.arr[i], freqMap.getOrDefault(solution.arr[i], 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            bucketList.get(entry.getValue()).add(entry.getKey());
        }
        for (int i = bucketList.size() - 1; i > 0 && answer.size() < solution.key; i--) {
            if (!bucketList.get(i).isEmpty()) {
                answer.addAll(bucketList.get(i));
            }
        }
        if (answer.size() > solution.key) {
            return answer.subList(0, solution.key);
        }
        return answer;
    }

}
