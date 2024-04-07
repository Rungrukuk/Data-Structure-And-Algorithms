package DataStructureAndAlgorithms.Solutions.Array;

import java.util.Arrays;

import DataStructureAndAlgorithms.Base_Solution;

public class Search_Row_And_Column_Wise_In_Matrix implements Base_Solution {

    private final int[][] martix;
    private final int length;
    private final int key;
    private final SolutionKind solutionKind;

    public Search_Row_And_Column_Wise_In_Matrix(int[][] martix, int length, int key, SolutionKind solutionKind) {
        this.martix = martix;
        this.length = length;
        this.key = key;
        this.solutionKind = solutionKind;
    }

    public Search_Row_And_Column_Wise_In_Matrix(int[][] martix, int length, int key) {
        this.martix = martix;
        this.length = length;
        this.key = key;
        this.solutionKind = null;
    }

    public int[] findElementInSortedMatrix_DivideAndConquer() {
        int[] answer = new int[2];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                answer = DivideAndConquer(martix, 0, length - 1, 0, length - 1, key);
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
        int j = length - 1;
        while (i < length && j >= 0) {
            if (martix[i][j] == key)
                return new int[] { i, j };

            if (martix[i][j] > key)
                j--;
            else
                i++;
        }
        return new int[] { -1, -1 };
    }

    @Override
    public void solve() {
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
    }

    private void printSolution(int[] answer, String solutionKind) {
        System.out.println(solutionKind + ": " + Arrays.toString(answer));
    }

}
