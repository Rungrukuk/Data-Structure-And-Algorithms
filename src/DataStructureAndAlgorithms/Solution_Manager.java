package DataStructureAndAlgorithms;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Solutions.Array.Largest_Sum_Contiguous_Subarray;
import DataStructureAndAlgorithms.Solutions.Array.Print_Matrix_In_Spiral;
import DataStructureAndAlgorithms.Solutions.Array.Rotate_Array_By_Given_Position;
import DataStructureAndAlgorithms.Solutions.Array.Trapping_Rain_Water;

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
    private boolean debug;
    private ISolvable solution;

    public Solution_Manager(Solution_Names solutionName, boolean debug) {
        this.debug = debug;
        this.solution = solutionsMap.get(solutionName);
    }

    public void run() {
        if (debug) {

        } else
            solution.solve(null);
    }

}
