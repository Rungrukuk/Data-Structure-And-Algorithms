package DataStructureAndAlgorithms;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Solutions.Array.Largest_Sum_Contiguous_Subarray;
import DataStructureAndAlgorithms.Solutions.Array.Print_Matrix_In_Spiral;
import DataStructureAndAlgorithms.Solutions.Array.Rotate_Array_By_Given_Position;
import DataStructureAndAlgorithms.Solutions.Array.Trapping_Rain_Water;
import DataStructureAndAlgorithms.Tests.Array.Largest_Sum_Contiguous_Subarray_Test;

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
                new Largest_Sum_Contiguous_Subarray(Shared_Data.arr));
        solutionsMap.put(Solution_Names.Print_Matrix_In_Spiral, new Print_Matrix_In_Spiral(Shared_Data.matrix));
        solutionsMap.put(Solution_Names.Rotate_Array_By_Given_Position,
                new Rotate_Array_By_Given_Position(Shared_Data.arr, Shared_Data.position));
        solutionsMap.put(Solution_Names.Trapping_Rain_Water, new Trapping_Rain_Water(Shared_Data.arr_2));
    }

    private void initializeTestMap() {
        testsMap.put(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous,
                new Largest_Sum_Contiguous_Subarray_Test(
                        (Largest_Sum_Contiguous_Subarray) solutionsMap
                                .get(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous)));
        testsMap.put(Solution_Names.Print_Matrix_In_Spiral, null);
        testsMap.put(Solution_Names.Rotate_Array_By_Given_Position, null);
        testsMap.put(Solution_Names.Trapping_Rain_Water, null);
    }

    public void run() {
        if (isTest) {
            System.out.println("Expected answer: " + solution.solve());
            System.out.println("Your answer: " + testObject.test());
            System.out.println("Result: " + testObject.compare());
        } else {
            System.out.println("Answer: " + solution.solve());
        }
    }
}
