package DataStructureAndAlgorithms.Solutions.String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataStructureAndAlgorithms.Base_Solution;

public class Group_Anagram extends Base_Solution<List<List<String>>> {

    public final String[] strs = new String[] { "eat", "tea", "tan", "ate", "nat", "bat" };

    @Override
    protected List<List<String>> solve() {
        Map<String, List<String>> answer = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a'] += 1;
            }
            String key = Arrays.toString(count);
            if (!answer.containsKey(key)) {
                answer.put(key, new ArrayList<>());
            }
            answer.get(key).add(s);
        }
        return new ArrayList<>(answer.values());
    }

}
