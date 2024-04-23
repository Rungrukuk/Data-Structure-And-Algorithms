package DataStructureAndAlgorithms.Tests.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Search_Row_And_Column_Wise_In_Matrix;

public class Search_Row_And_Column_Wise_In_Matrix_Test extends Base_Test<List<Integer>, Search_Row_And_Column_Wise_In_Matrix> {

    public Search_Row_And_Column_Wise_In_Matrix_Test(Search_Row_And_Column_Wise_In_Matrix solution) {
        super(solution);
    }

    @Override
    protected List<Integer> test() {
        List<Integer> answer = new ArrayList<Integer>();
        int m=solution.matrix.length-1,n=0;
        while (solution.matrix[m][n]!=solution.key) {
            if (solution.key<solution.matrix[m][n]) {
                m--;
            }
            else if (solution.key>solution.matrix[m][n]) {
                n++;
            }
            if (solution.matrix[m][n]==solution.key) {
                answer.add(m);
                answer.add(n);
                break;
            }
        }
        return answer;
    }
    
}
