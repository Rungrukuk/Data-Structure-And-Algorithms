package DataStructureAndAlgorithms.Solutions.Array;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Base_Solution;

public class Count_Pairs_With_Given_Sum extends Base_Solution<Integer> {
    public final int[] arr = { 10, 12, 10, 15, -1, 7, 6, 5, 4, 2, 1, 1, 1 };
    public final int key = 11;

    @Override
    protected Integer solve() {
        int answer = 0;
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i : arr) {
            if (frequencyMap.containsKey(key - i)) {
                answer += frequencyMap.get(key - i);
            }
            frequencyMap.put(
                    i, frequencyMap.getOrDefault(i, 0) + 1);
        }

        return answer;
    }

}
