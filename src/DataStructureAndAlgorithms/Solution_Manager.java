package DataStructureAndAlgorithms;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Solutions.Array.Count_Pairs_With_Given_Sum;
import DataStructureAndAlgorithms.Solutions.Array.Largest_Sum_Contiguous_Subarray;
import DataStructureAndAlgorithms.Solutions.Array.Print_Matrix_In_Spiral;
import DataStructureAndAlgorithms.Solutions.Array.Rotate_Array_By_Given_Position;
import DataStructureAndAlgorithms.Solutions.Array.Search_Row_And_Column_Wise_In_Matrix;
import DataStructureAndAlgorithms.Solutions.Array.Trapping_Rain_Water;
import DataStructureAndAlgorithms.Tests.Array.Largest_Sum_Contiguous_Subarray_Test;
import DataStructureAndAlgorithms.Tests.Array.Print_Matrix_In_Spiral_Test;
import DataStructureAndAlgorithms.Tests.Array.Rotate_Array_By_Given_Position_Test;
import DataStructureAndAlgorithms.Tests.Array.Search_Row_And_Column_Wise_In_Matrix_Test;
import DataStructureAndAlgorithms.Tests.Array.Trapping_Rain_Water_Test;

public class Solution_Manager {

    private final Map<Solution_Names, Base_Solution<?>> solutionsMap = new HashMap<>();
    private final Map<Solution_Names, Base_Test<?, ?>> testsMap = new HashMap<>();

    private boolean isTest;
    private Base_Solution<?> solution;
    private Base_Test<?, ?> testObject;

    public Solution_Manager(Solution_Names solutionName, boolean isTest) {
        this.isTest = isTest;
        initializeSolutionMap();
        this.solution = solutionsMap.get(solutionName);
        if (isTest) {
            initializeTestMap();
            this.testObject = testsMap.get(solutionName);
        }
    }

    public Solution_Manager(Solution_Names solutionName) {
        this(solutionName, false);
    }

    private void initializeSolutionMap() {
        solutionsMap.put(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous,
                new Largest_Sum_Contiguous_Subarray());
        solutionsMap.put(Solution_Names.Print_Matrix_In_Spiral, new Print_Matrix_In_Spiral());
        solutionsMap.put(Solution_Names.Rotate_Array_By_Given_Position,
                new Rotate_Array_By_Given_Position());
        solutionsMap.put(Solution_Names.Search_Row_And_Column_Wise_In_Matrix,
                new Search_Row_And_Column_Wise_In_Matrix());
        solutionsMap.put(Solution_Names.Trapping_Rain_Water, new Trapping_Rain_Water());
        solutionsMap.put(Solution_Names.Count_Pairs_With_Given_Sum, new Count_Pairs_With_Given_Sum());
    }

    private void initializeTestMap() {
        testsMap.put(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous,
                new Largest_Sum_Contiguous_Subarray_Test((Largest_Sum_Contiguous_Subarray) solutionsMap
                        .get(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous)));
        testsMap.put(Solution_Names.Print_Matrix_In_Spiral, new Print_Matrix_In_Spiral_Test(
                (Print_Matrix_In_Spiral) solutionsMap.get(Solution_Names.Print_Matrix_In_Spiral)));
        testsMap.put(Solution_Names.Rotate_Array_By_Given_Position, new Rotate_Array_By_Given_Position_Test(
                (Rotate_Array_By_Given_Position) solutionsMap.get(Solution_Names.Rotate_Array_By_Given_Position)));
        testsMap.put(Solution_Names.Search_Row_And_Column_Wise_In_Matrix, new Search_Row_And_Column_Wise_In_Matrix_Test(
                (Search_Row_And_Column_Wise_In_Matrix) solutionsMap
                        .get(Solution_Names.Search_Row_And_Column_Wise_In_Matrix)));
        testsMap.put(Solution_Names.Trapping_Rain_Water, new Trapping_Rain_Water_Test(
                (Trapping_Rain_Water) solutionsMap.get(Solution_Names.Trapping_Rain_Water)));
    }

    public void run() {
        if (isTest) {
            System.out.println("Expected answer: " + solution.solve());
            System.out.println("Your answer    : " + testObject.test());
            System.out.println("Result: " + testObject.compare());
        } else {
            System.out.println("Answer: " + solution.solve());
        }
    }
}
