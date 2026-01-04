package DataStructureAndAlgorithms.Problems.ArraysAndHashing.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Problem(name = "Group Anagrams", category = "Arrays And Hashing", difficulty = "Medium")
public class GroupAnagrams extends BaseProblem<List<List<String>>> {
    public List<String> strs = new ArrayList<>(List.of("eat", "tea", "tan", "ate", "nat", "bat"));

    @Override
    public List<List<String>> solve() {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            int[] freq = new int[26];
            for (char c : s.toCharArray()) freq[c - 'a']++;

            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                keyBuilder.append('#').append(freq[i]);
            }
            String key = keyBuilder.toString();

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }
}
