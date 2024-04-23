package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataStructureAndAlgorithms.Base_Solution;

public class Search_Row_And_Column_Wise_In_Matrix extends Base_Solution<List<Integer>> {

    public final int[][] matrix;
    public final int key;

    public Search_Row_And_Column_Wise_In_Matrix(int[][] matrix, int key){
        this.matrix = matrix;
        this.key = key;
    }

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
