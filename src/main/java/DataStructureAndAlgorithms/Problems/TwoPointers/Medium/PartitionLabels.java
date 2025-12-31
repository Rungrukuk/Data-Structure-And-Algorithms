package DataStructureAndAlgorithms.Problems.TwoPointers.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayList;
import java.util.List;

@Problem(name = "Partition Labels", category = "Two Pointers", difficulty = "Medium")
public class PartitionLabels extends BaseProblem<List<Integer>> {
    public String s = "ababcbacadefegdehijhklij";

    @Override
    public List<Integer> solve() {
        List<Integer> answer = new ArrayList<>();
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);

            if (i == end) {
                answer.add(end - start + 1);
                start = i + 1;
            }
        }
        return answer;
    }
}
