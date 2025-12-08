package DataStructureAndAlgorithms.Problems.BinarySearch;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Koko Eating Bananas", category = "Binary Search")
public class KokoEatingBananas extends BaseProblem<Integer> {
    public final int[] piles = new int[] { 3, 6, 7, 11 };
    public final int h = 8;

    @Override
    public Integer solve() {
        long total = 0;
        int right = 0;
        for (int p : piles) {
            total += p;
            if (p > right) {
                right = p;
            }
        }

        int left = (int) ((total - 1) / h) + 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int time = 0;
            for (int p : piles) {
                time += (p - 1) / mid + 1;
            }
            if (time > h) {
                left = mid + 1;
            } else
                right = mid;
        }
        return left;
    }

}
