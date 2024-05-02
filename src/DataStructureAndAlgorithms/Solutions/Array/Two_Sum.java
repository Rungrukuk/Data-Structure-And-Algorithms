package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataStructureAndAlgorithms.Base_Solution;

public class Two_Sum extends Base_Solution<List<Integer>> {

    public final int[] arr = { 3, 2, 4 };
    public final int key = 7;

    @Override
    protected List<Integer> solve() {
        List<Integer> answer = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(key - arr[i])) {
                answer.add(i);
                answer.add(map.get(key - arr[i]));
            } else {
                map.put(arr[i], i);
            }
        }
        return answer;
    }

}
