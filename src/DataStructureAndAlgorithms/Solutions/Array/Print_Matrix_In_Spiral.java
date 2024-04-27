package DataStructureAndAlgorithms.Solutions.Array;

import java.util.ArrayList;
import java.util.List;

import DataStructureAndAlgorithms.Base_Solution;

public class Print_Matrix_In_Spiral extends Base_Solution<List<Integer>> {

    public final int[][] matrix = {
            { 1, 2, 3, 4 },
            { 5, 6, 7, 8 },
            { 9, 10, 11, 12 },
            { 13, 14, 15, 16 }
    };

    @Override
    public List<Integer> solve() {
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
}
