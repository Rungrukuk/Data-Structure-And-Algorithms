package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataStructureAndAlgorithms.ISolvable;

public class Search_Row_And_Column_Wise_In_Matrix implements ISolvable<List<Integer>> {

    private final int[][] matrix = new int[][] { { 10, 20, 30, 40 },
            { 15, 25, 35, 45 },
            { 27, 29, 37, 48 },
            { 32, 33, 39, 50 } };
    private final int key = 29;

    @Override
    public List<Integer> solve() {
        int i = 0;
        int j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == key)
                return new ArrayList<Integer>(Arrays.asList(i, j));
            if (matrix[i][j] > key)
                j--;
            else
                i++;
        }
        return new ArrayList<Integer>(Arrays.asList(-1, -1));
    }
}
