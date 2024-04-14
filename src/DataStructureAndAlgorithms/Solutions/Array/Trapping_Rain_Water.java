package DataStructureAndAlgorithms.Solutions.Array;

import DataStructureAndAlgorithms.ISolvable;

public class Trapping_Rain_Water implements ISolvable {

    private final int[] arr;

    public Trapping_Rain_Water(int[] arr) {
        this.arr = arr;
    }

    private Integer findTrappingWater_parabolic() {
        int answwer = 0;

        for (int i = 1; i < arr.length - 1; i++) {

            int left = arr[i];
            for (int j = 0; j < i; j++) {
                left = Math.max(left, arr[j]);
            }

            int right = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                right = Math.max(right, arr[j]);
            }

            answwer += Math.min(left, right) - arr[i];
        }
        return answwer;
    }

    @Override
    public void solve(SolutionKind solutionKind) {
        if (solutionKind != null) {
            switch (solutionKind) {
                case ParabolicTime:
                    printSolution(findTrappingWater_parabolic(), SolutionKind.ParabolicTime.toString());
                    break;
                case LinearTime:
                    // printSolution(findElementInSortedMatrix_LinearTime(),
                    // String.valueOf(SolutionKind.LinearTime));
                    break;
                default:
                    // printSolution(findElementInSortedMatrix_LinearTime(),
                    // String.valueOf(SolutionKind.LinearTime));
                    break;
            }
        }
        // else {
        // printSolution(findElementInSortedMatrix_LinearTime(),
        // String.valueOf(SolutionKind.LinearTime));
        // }
    }

    private void printSolution(int answer, String solutionKind) {
        System.out.println(solutionKind + ": " + answer);
    }
}
