package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.KokoEatingBananas;

@Practice(problemName = "Koko Eating Bananas", category = "Binary Search")
public class KokoEatingBananas_Practice extends BasePractice<Integer, KokoEatingBananas> {

    public KokoEatingBananas_Practice(KokoEatingBananas problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        int right = 0;
        long total = 0;
        for (int p : problem.piles) {
            total += p;
            if (right < p) {
                right = p;
            }
        }
        int left = (int) ((total - 1) / problem.h) + 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int time = 0;
            for (int p : problem.piles) {
                time += (p - 1) / mid + 1;
            }
            if (time > problem.h) {
                left = mid + 1;
            } else
                right = mid;
        }
        return left;
    }
}
