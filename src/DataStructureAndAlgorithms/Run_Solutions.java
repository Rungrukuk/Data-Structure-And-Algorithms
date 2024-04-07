package DataStructureAndAlgorithms;

// import DataStructureAndAlgorithms.Base_Solution.SolutionKind;
import DataStructureAndAlgorithms.Solutions.Array.*;

public class Run_Solutions {
    public static void main(String[] args) {
        // int[] arr = { 1, -2, -3, 4, 5, -6, 7, -8 };
        // Largest_Sum_Contiguous_Subarray solution = new
        // Largest_Sum_Contiguous_Subarray(arr);
        // solution.solve();

        // int[][] arr = { { 10, 20, 30, 40 },
        // { 15, 25, 35, 45 },
        // { 27, 29, 37, 48 },
        // { 32, 33, 39, 50 } };

        // Search_Row_And_Column_Wise_In_Matrix solution = new
        // Search_Row_And_Column_Wise_In_Matrix(arr, 29,
        // SolutionKind.LinearTime);
        // solution.solve();

        // int[][] arr = { { 10, 20, 30, 40 },
        // { 15, 25, 35, 45 },
        // { 27, 29, 37, 48 },
        // { 32, 33, 39, 50 } };
        // Print_Matrix_In_Spiral solution = new Print_Matrix_In_Spiral(arr);
        // solution.solve();

        int[] arr = { 3, 4, 5, 6, 7, 1, 2 };
        Rotate_Array_By_Given_Position solution = new Rotate_Array_By_Given_Position(arr, 2);
        solution.solve();

    }
}
