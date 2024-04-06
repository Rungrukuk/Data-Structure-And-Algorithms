package DataStructureAndAlgorithms;

import DataStructureAndAlgorithms.Solutions.*;

public class Run_Solutions {
    public static void main(String[] args) {
        Base_Solution solution = new Largest_Sum_Contiguous_Subarray();

        int[] arr = { -2, -3, 4, -1, -2, 1, 5, -3 };

        int result = solution.solution(arr);

        System.out.println("Largest sum contiguous subarray: " + result);
    }
}
