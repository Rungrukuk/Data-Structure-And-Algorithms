package DataStructureAndAlgorithms.Solutions.Array;

import DataStructureAndAlgorithms.Base_Solution;

public class Find_Least_Average_Subarray extends Base_Solution<Integer> {

    public final int[] arr = { 3, 7, 90, 20, 10, 50, 40 };
    public final int key = 3;

    @Override
    protected Integer solve() {
        int answer = 0;
        int sum = 0;
        for (int i = 0; i < key; i++) {
            sum += arr[i];
        }
        int minSum = sum;
        for (int i = key; i < arr.length; i++) {
            sum += arr[i] - arr[i - key];
            if (sum < minSum) {
                minSum = sum;
                answer = i - key + 2;
            }
        }
        return answer;
    }

}
