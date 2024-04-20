package DataStructureAndAlgorithms.Tests.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Print_Matrix_In_Spiral;

public class Print_Matrix_In_Spiral_Test extends Base_Test<List<Integer>, Print_Matrix_In_Spiral> {

    public Print_Matrix_In_Spiral_Test(Print_Matrix_In_Spiral solution) {
        super(solution);
    }

    @Override
    public List<Integer> test() {
        List<Integer> answer = new ArrayList<Integer>();
        int sizeOfRows = solution.matrix.length;
        int sizeOfColumns = solution.matrix[0].length;
        boolean seen[][] = new boolean[sizeOfRows][sizeOfColumns];
        int dr[] = { 1, 0, -1, 0 };
        int dd[] = { 0, 1, 0, -1 };
        int x = 0, y = 0, di = 0;
        for (int i = 0; i < sizeOfRows * sizeOfColumns; i++) {
            answer.add(solution.matrix[y][x]);
            seen[y][x] = true;
            int nr = x + dr[di];
            int nd = y + dd[di];
            if (nr >= 0 && nr < sizeOfColumns && nd >= 0 && nd < sizeOfRows && !seen[nd][nr]) {
                y = nd;
                x = nr;
            } else {
                di = (di + 1) % 4;
                x += dr[di];
                y += dd[di];
            }
        }
        return answer;
    }

}
