package DataStructureAndAlgorithms.Practices.BinarySearch.Medium;

import DataStructureAndAlgorithms.Problems.BinarySearch.Medium.KokoEatingBananas;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "Koko Eating Bananas", category = "Binary Search", difficulty = "Medium")
public class KokoEatingBananas_Practice extends BasePractice<Integer, KokoEatingBananas> {

    public KokoEatingBananas_Practice(KokoEatingBananas problem) {
        super(problem);
    }

    @Override
    public Integer practice() {
        long sum = 0;
        int right = 0;
        for (int pile : problem.piles){
            if (pile>right)
                right = pile;
            sum+=pile;
        }
        int left = (int) (sum + problem.h-1)/ problem.h;

        while (left<right){
            int middle = left + (right-left)/2;
            long time = 0;
            for (int pile : problem.piles){
                time+=(pile+middle-1)/middle;
            }
            if (time>problem.h)
                left = middle + 1;
            else
                right = middle;
        }

        return left;
    }
}
