package DataStructureAndAlgorithms.Solutions.String;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Base_Solution;

public class Is_Anagram extends Base_Solution<Boolean> {

    public final String s = "anagram";
    public final String t = "nagaram";

    @Override
    protected Boolean solve() {
        Map<Character, Integer> freqMap = new HashMap<>();
        if (s.length() != t.length())
            return false;
        for (Character i : s.toCharArray()) {
            if (freqMap.containsKey(i)) {
                freqMap.put(i, freqMap.get(i) + 1);
            } else {
                freqMap.put(i, 1);
            }
        }

        for (Character i : t.toCharArray()) {
            if (freqMap.containsKey(i)) {
                if (freqMap.get(i) > 0)
                    freqMap.put(i, freqMap.get(i) - 1);
                else
                    return false;
            } else {
                return false;
            }
        }

        return true;
    }

}
