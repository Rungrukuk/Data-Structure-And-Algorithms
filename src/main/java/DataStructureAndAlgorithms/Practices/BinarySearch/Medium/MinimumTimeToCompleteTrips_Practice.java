package DataStructureAndAlgorithms.Practices.BinarySearch.Medium;

import DataStructureAndAlgorithms.Problems.BinarySearch.Medium.MinimumTimeToCompleteTrips;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.core.base.BasePractice;

@Practice(problemName = "Minimum Time To Complete Trips", category = "Binary Search", difficulty = "Medium")
public class MinimumTimeToCompleteTrips_Practice extends BasePractice<Long, MinimumTimeToCompleteTrips> {

    public MinimumTimeToCompleteTrips_Practice(MinimumTimeToCompleteTrips problem) {
        super(problem);
    }

    @Override
    public Long practice() {
        long right = 0;
        for (int t : problem.time){
            if (t>right)
                right = t;
        }
        long left = 1;
        right *= problem.totalTrips;
        while (left<right){
            long middle = left+(right-left)/2;
            long count = 0;
            for (int t : problem.time){
                count+=middle/t;
            }
            if (count< problem.totalTrips)
                left = middle+1;
            else
                right = middle;
        }
        return left;
    }
}
