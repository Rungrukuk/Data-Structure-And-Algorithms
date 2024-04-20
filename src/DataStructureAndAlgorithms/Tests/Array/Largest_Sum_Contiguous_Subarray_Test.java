package DataStructureAndAlgorithms.Tests.Array;

import DataStructureAndAlgorithms.Base_Test;
import DataStructureAndAlgorithms.Solutions.Array.Largest_Sum_Contiguous_Subarray;

public class Largest_Sum_Contiguous_Subarray_Test extends Base_Test<Integer, Largest_Sum_Contiguous_Subarray> {

    public Largest_Sum_Contiguous_Subarray_Test(Largest_Sum_Contiguous_Subarray solution) {
        super(solution);
    }

    @Override
    public Integer test() {
        int maxEndingHere = 0;
        int maxSoFar = Integer.MIN_VALUE;
        int i = 0;
        while (i < this.solution.arr.length) {
            maxEndingHere += this.solution.arr[i];
            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
            }
            if (maxEndingHere < 0) {
                maxEndingHere = 0;
            }
            i++;
        }

        return maxSoFar;
    }
}