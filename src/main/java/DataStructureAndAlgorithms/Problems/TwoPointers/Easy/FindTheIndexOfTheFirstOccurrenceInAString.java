package DataStructureAndAlgorithms.Problems.TwoPointers.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Find The Index Of The First Occurrence In A String", category = "Two Pointers", difficulty = "Easy")
public class FindTheIndexOfTheFirstOccurrenceInAString extends BaseProblem<Integer> {
    public String haystack = "sadbutsad";
    public String needle = "sad";

    @Override
    public Integer solve() {
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            int j = 0;
            while (j < needle.length() && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i;
            }
        }
        return -1;

//        return haystack.indexOf(needle);
    }
}
