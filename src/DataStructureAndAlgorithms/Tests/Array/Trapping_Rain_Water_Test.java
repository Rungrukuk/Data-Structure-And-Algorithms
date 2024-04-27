package DataStructureAndAlgorithms.Tests.Array;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Trapping_Rain_Water;

public class Trapping_Rain_Water_Test extends Base_Test<Integer, Trapping_Rain_Water> {

    public Trapping_Rain_Water_Test(Trapping_Rain_Water solution) {
        super(solution);
    }

    @Override
    protected Integer test() {
        int answer = 0;
        int rightMax = 0, leftMax = 0, right = solution.arr.length - 1, left = 0;
        while (left < right) {
            if (solution.arr[left] < solution.arr[right]) {
                if (leftMax < solution.arr[left]) {
                    leftMax = solution.arr[left];
                } else {
                    answer += leftMax - solution.arr[left];
                }
                left++;
            } else {
                if (rightMax < solution.arr[right]) {
                    rightMax = solution.arr[right];
                } else {
                    answer += rightMax - solution.arr[right];
                }
                right--;
            }
        }
        return answer;
    }

}
