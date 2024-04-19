package DataStructureAndAlgorithms;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Debugs.Largest_Sum_Contiguous_Subarray_Debug;
import DataStructureAndAlgorithms.Solutions.Array.Largest_Sum_Contiguous_Subarray;
import DataStructureAndAlgorithms.Solutions.Array.Print_Matrix_In_Spiral;
import DataStructureAndAlgorithms.Solutions.Array.Rotate_Array_By_Given_Position;
import DataStructureAndAlgorithms.Solutions.Array.Trapping_Rain_Water;

@SuppressWarnings("rawtypes")
public class Solution_Manager {

    private final Map<Solution_Names, ISolvable> solutionsMap = new HashMap<Solution_Names, ISolvable>() {
        {
            put(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous,
                    new Largest_Sum_Contiguous_Subarray(Shared_Data.arr));
            put(Solution_Names.Print_Matrix_In_Spiral, new Print_Matrix_In_Spiral(Shared_Data.matrix));
            put(Solution_Names.Rotate_Array_By_Given_Position,
                    new Rotate_Array_By_Given_Position(Shared_Data.arr, Shared_Data.position));
            put(Solution_Names.Trapping_Rain_Water, new Trapping_Rain_Water(Shared_Data.arr_2));
        }
    };

    @SuppressWarnings("unchecked")
    private final Map<Solution_Names, ITestable> debugsMap = new HashMap<Solution_Names, ITestable>() {
        {
            put(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous,
                    new Largest_Sum_Contiguous_Subarray_Debug(
                            solutionsMap.get(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous)));
            put(Solution_Names.Print_Matrix_In_Spiral, null);
            put(Solution_Names.Rotate_Array_By_Given_Position, null);
            put(Solution_Names.Trapping_Rain_Water, null);
        }
    };

    private boolean debug;
    private ISolvable solution;
    private ITestable debugObject;

    public Solution_Manager(Solution_Names solutionName, boolean debug) {
        this.solution = solutionsMap.get(solutionName);
        this.debugObject = debugsMap.get(solutionName);
        this.debug = true;
    }

    public Solution_Manager(Solution_Names solutionName) {
        this.solution = solutionsMap.get(solutionName);
        this.debugObject = null;
        this.debug = false;
    }

    public void run() {
        if (debug) {
            System.out.println("Expected answer: " + solution.solve());
            System.out.println("Your answer: " + debugObject.test());
            System.out.println("Result: " + debugObject.compare());
        } else
            solution.solve();
    }

}
