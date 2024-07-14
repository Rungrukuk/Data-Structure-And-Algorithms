package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataStructureAndAlgorithms.Base_Solution;

public class Four_Sum extends Base_Solution<List<List<Integer>>> {

    public final int[] arr = { 1, 0, -1, 0, -2, 2 };
    public final int target = 0;

    @Override
    protected List<List<Integer>> solve() {
        List<List<Integer>> answer = new ArrayList<>();
        Arrays.sort(arr);
        for (int i = 0; i < arr.length - 3; i++) {
            for (int j = i + 1; j < arr.length - 2; j++) {
                int left = j + 1;
                int right = arr.length - 1;
                while (left < right) {
                    int sum = arr[i] + arr[j] + arr[left] + arr[right];
                    if (sum == target) {
                        answer.add(Arrays.asList(arr[i], arr[j], arr[left], arr[right]));
                        while (arr[left] == arr[left + 1] && right > left)
                            left++;
                        while (arr[right] == arr[right - 1] && right > left)
                            right--;

                        left++;
                        right--;
                    } else if (target > sum) {
                        left++;
                    } else
                        right--;
                }

            }
        }
        System.gc();
        return answer;
    }

}
