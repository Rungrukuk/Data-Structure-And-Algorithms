package DataStructureAndAlgorithms.Problems.Binary_Search;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "Koko Eating Bananas", category = "Binary Search")
public class Koko_Eating_Bananas extends BaseProblem<Integer> {
    public final int[] piles = new int[] { 3, 6, 7, 11 };
    public final int h = 8;

    @Override
    protected Integer solve() {
        int n = piles.length;
        long total = 0;
        for (int p : piles)
            total += p;

        int left = (int) ((total - 1) / h) + 1;
        int right = (int) ((total - n) / (h - n + 1)) + 1;

        while (left < right) {
            int middle = left + (right - left) / 2;
            int time = 0;
            for (int p : piles) {
                time += (p - 1) / middle + 1;
            }
            if (time > h) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

}
