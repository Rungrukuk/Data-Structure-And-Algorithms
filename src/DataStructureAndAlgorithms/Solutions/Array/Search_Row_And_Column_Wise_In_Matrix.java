package DataStructureAndAlgorithms.Solutions.Array;

import java.util.Arrays;

import DataStructureAndAlgorithms.ISolvable;

public class Search_Row_And_Column_Wise_In_Matrix implements ISolvable {

    private final int[][] matrix = new int[][] { { 10, 20, 30, 40 },
            { 15, 25, 35, 45 },
            { 27, 29, 37, 48 },
            { 32, 33, 39, 50 } };
    private final int key = 29;

    public int[] findElementInSortedMatrix_DivideAndConquer() {
        int[] answer = new int[2];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                answer = DivideAndConquer(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1, key);
            }
        }
        return answer;
    }

    private int[] DivideAndConquer(int[][] arr, int fromRow, int toRow, int fromCol, int toCol, int key) {
        int i = fromRow + (toRow - fromRow) / 2;
        int j = fromCol + (toCol - fromCol) / 2;
        int[] answer = new int[2];

        if (arr[i][j] == key) {
            answer[0] = i;
            answer[1] = j;
            return answer;
        }

        if (i != toRow || j != fromCol) {
            answer = DivideAndConquer(arr, fromRow, i, j, toCol, key);
            if (answer[0] != -1 && answer[1] != -1) {
                return answer;
            }
        }

        if (arr[i][j] < key) {
            if (i + 1 <= toRow) {
                answer = DivideAndConquer(arr, i + 1, toRow, fromCol, toCol, key);
                if (answer[0] != -1 && answer[1] != -1) {
                    return answer;
                }
            }
        } else {
            if (j - 1 >= fromCol) {
                answer = DivideAndConquer(arr, fromRow, toRow, fromCol, j - 1, key);
                if (answer[0] != -1 && answer[1] != -1) {
                    return answer;
                }
            }
        }

        return new int[] { -1, -1 };
    }

    public int[] findElementInSortedMatrix_LinearTime() {
        int i = 0;
        int j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == key)
                return new int[] { i, j };

            if (matrix[i][j] > key)
                j--;
            else
                i++;
        }
        return new int[] { -1, -1 };
    }

    @Override
    public void solve(SolutionKind solutionKind) {
        if (solutionKind != null) {
            switch (solutionKind) {
                case LogarthmicTime:
                    printSolution(findElementInSortedMatrix_DivideAndConquer(),
                            String.valueOf(SolutionKind.LogarthmicTime));
                    break;
                case LinearTime:
                    printSolution(findElementInSortedMatrix_LinearTime(), String.valueOf(SolutionKind.LinearTime));
                    break;
                default:
                    printSolution(findElementInSortedMatrix_LinearTime(), String.valueOf(SolutionKind.LinearTime));
                    break;
            }
        } else {
            printSolution(findElementInSortedMatrix_LinearTime(), String.valueOf(SolutionKind.LinearTime));
        }
    }

    private void printSolution(int[] answer, String solutionKind) {
        System.out.println(solutionKind + ": " + Arrays.toString(answer));
    }

}
