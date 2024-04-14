package DataStructureAndAlgorithms;

public class Run_Solutions {
    public static void main(String[] args) {
        // int[] arr = { 1, -2, -3, 4, 5, -6, 7, -8 };
        // Largest_Sum_Contiguous_Subarray solution = new
        // Largest_Sum_Contiguous_Subarray(arr);
        // solution.solve();

        // Search_Row_And_Column_Wise_In_Matrix solution = new
        // Search_Row_And_Column_Wise_In_Matrix(arr, ,
        // SolutionKind.LinearTime);
        // solution.solve();

        // Print_Matrix_In_Spiral solution = new Print_Matrix_In_Spiral();
        // solution.printResult();

        // int[] arr = { 3, 4, 5, 6, 7, 1, 2 };
        // Rotate_Array_By_Given_Position solution = new
        // Rotate_Array_By_Given_Position(arr, 2);
        // solution.solve();

        // int[] arr = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
        // Trapping_Rain_Water solution = new Trapping_Rain_Water(arr,
        // SolutionKind.ParabolicTime);
        // solution.solve();
        Solution_Manager solution_Manager = new Solution_Manager(
                Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous, false);
        solution_Manager.run();

    }
}
