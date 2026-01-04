package DataStructureAndAlgorithms.Problems.ArraysAndHashing.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.HashMap;

@Problem(name = "Valid Anagram", category = "Arrays And Hashing", difficulty = "Easy")
public class ValidAnagram extends BaseProblem<Boolean> {
    public String s = "anagram";
    public String t = "nagaram";

    @Override
    public Boolean solve() {
        HashMap<Character, Integer> bucket = new HashMap<>();
        for (char c : s.toCharArray()) {
            bucket.put(c, bucket.getOrDefault(c, 0) + 1);
        }
        for (char c : t.toCharArray()) {
            if (bucket.containsKey(c) && bucket.get(c) > 0) {
                bucket.put(c, bucket.get(c) - 1);
            } else
                return false;
        }
        return s.length() == t.length();
    }
}
