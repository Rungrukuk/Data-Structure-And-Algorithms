package DataStructureAndAlgorithms.Practices.BinarySearch;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.annotations.Practice;
import DataStructureAndAlgorithms.Problems.BinarySearch.MinimumTimeToCompleteTrips;

@Practice(problemName = "Minimum Time To Complete Trips", category = "Binary Search")
public class MinimumTimeToCompleteTrips_Practice extends BasePractice<Long, MinimumTimeToCompleteTrips> {

    public MinimumTimeToCompleteTrips_Practice(MinimumTimeToCompleteTrips problem) {
        super(problem);
    }

    @Override
    public Long practice() {
        long right = 0;
        long total = 0;
        for (int t : problem.time) {
            total += t;
            if (t > right) {
                right = t;
            }
        }
        right *= problem.totalTrips;
        long left = (total - 1) / problem.totalTrips + 1;
        while (left < right) {
            long mid = left + (right - left) / 2;
            long trips = 0;
            for (int t : problem.time) {
                trips += mid / t;
                if (trips >= problem.totalTrips)
                    break;
            }
            if (trips < problem.totalTrips) {
                left = mid + 1;
            } else
                right = mid;
        }
        return left;
    }
}
