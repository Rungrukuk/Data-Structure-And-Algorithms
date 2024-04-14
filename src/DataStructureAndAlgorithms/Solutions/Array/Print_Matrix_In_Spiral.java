package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.ISolvable;

public class Print_Matrix_In_Spiral implements ISolvable {

    private final int[][] matrix;

    public Print_Matrix_In_Spiral(int[][] matrix) {
        this.matrix = matrix;
    }

    private List<Integer> printMatrixInSpiral() {
        List<Integer> answer = new ArrayList<Integer>();

        int m = matrix.length, n = matrix[0].length;
        boolean[][] seen = new boolean[m][n];
        int[] dr = { 0, 1, 0, -1 };
        int[] dc = { 1, 0, -1, 0 };
        int x = 0, y = 0, di = 0;

        for (int i = 0; i < m * n; i++) {
            answer.add(matrix[x][y]);
            seen[x][y] = true;
            int cr = x + dr[di];
            int cc = y + dc[di];

            if (0 <= cr && cr < m && 0 <= cc && cc < n
                    && !seen[cr][cc]) {
                x = cr;
                y = cc;
            } else {
                di = (di + 1) % 4;
                x += dr[di];
                y += dc[di];
            }
        }
        return answer;
    }

    @Override
    public void solve(SolutionKind solutionKind) {
        System.out.println(printMatrixInSpiral().toString());
    }

}
