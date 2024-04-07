package DataStructureAndAlgorithms.Solutions.Array;

import java.util.Arrays;

import DataStructureAndAlgorithms.Base_Solution;

public class Search_Row_And_Column_Wise_In_Matrix implements Base_Solution {

    private int[][] matrix;
    private int key;
    private SolutionKind solutionKind;

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public SolutionKind getSolutionKind() {
        return solutionKind;
    }

    public void setSolutionKind(SolutionKind solutionKind) {
        this.solutionKind = solutionKind;
    }

    public Search_Row_And_Column_Wise_In_Matrix(int[][] martix, int key, SolutionKind solutionKind) {
        this.matrix = martix;
        this.key = key;
        this.solutionKind = solutionKind;
    }

    public Search_Row_And_Column_Wise_In_Matrix(int[][] martix, int key) {
        this.matrix = martix;
        this.key = key;
        this.solutionKind = null;
    }

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
    public void solve() {
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
