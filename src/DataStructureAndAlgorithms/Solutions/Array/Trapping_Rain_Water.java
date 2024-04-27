package DataStructureAndAlgorithms.Solutions.Array;

import DataStructureAndAlgorithms.Base_Solution;

public class Trapping_Rain_Water extends Base_Solution<Integer> {

    public final int[] arr;

    public Trapping_Rain_Water(int[] arr) {
        this.arr = arr;
    }

    @Override
    public Integer solve() {
        int answer = 0;
        int left = 0, right = arr.length - 1, leftMax = 0, rightMax = 0;
        while (left < right) {
            if (arr[left] < arr[right]) {
                if (arr[left] > leftMax) {
                    leftMax = arr[left];
                } else {
                    answer += leftMax - arr[left];
                }
                left++;
            } else {
                if (arr[right] > rightMax) {
                    rightMax = arr[right];
                } else {
                    answer += rightMax - arr[right];
                }
                right--;
            }
        }
        return answer;
    }
}
