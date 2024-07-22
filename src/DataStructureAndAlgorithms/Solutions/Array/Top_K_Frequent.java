package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataStructureAndAlgorithms.Base_Solution;

public class Top_K_Frequent extends Base_Solution<List<Integer>> {
    public final int[] arr = { 1, 1, 1, 2, 2, 3 };
    public final int key = 2;

    @Override
    protected List<Integer> solve() {
        Map<Integer, Integer> freqMap = new HashMap<>();
        List<Integer> answer = new ArrayList<>();
        List<List<Integer>> bucketList = new ArrayList<>();

        for (int i = 0; i <= arr.length; i++) {
            bucketList.add(new ArrayList<>());
        }

        for (int num : arr) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();
            bucketList.get(freq).add(num);
        }

        for (int i = bucketList.size() - 1; i > 0 && answer.size() < key; i--) {
            if (!bucketList.get(i).isEmpty()) {
                answer.addAll(bucketList.get(i));
            }
        }

        if (answer.size() > key) {
            return answer.subList(0, key);
        }

        return answer;
    }
}
