package DataStructureAndAlgorithms.Problems.Binary_Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;

@Problem(name = "Squares Of Sorted Array", category = "Binary Search")
public class Squares_Of_Sorted_Array extends BaseProblem<List<Integer>> {
    public final int[] nums = new int[] { -4, -1, 0, 3, 10 };

    @Override
    public List<Integer> solve() {
        int left = 0;
        int right = nums.length - 1;
        Stack<Integer> stack = new Stack<>();
        List<Integer> answer = new ArrayList<>();
        while (left <= right) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                stack.add(nums[left] * nums[left]);
                left++;
            } else {
                stack.add(nums[right] * nums[right]);
                right--;
            }
        }
        while (!stack.isEmpty()) {
            answer.add(stack.pop());
        }
        return answer;
    }

    // Better solution

    // private int[] solveBetter() {
    // int left = 0;
    // int right = nums.length - 1;
    // int[] answer = new int[nums.length];
    // int i = nums.length - 1;
    // while (left <= right) {
    // if (Math.abs(nums[left]) > Math.abs(nums[right])) {
    // answer[i] = nums[left] * nums[left];
    // left++;
    // i--;
    // } else {
    // answer[i] = nums[right] * nums[right];
    // right--;
    // i--;
    // }
    // }
    // return answer;
    // }
}