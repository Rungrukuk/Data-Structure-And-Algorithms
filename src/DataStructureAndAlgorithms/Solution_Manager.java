package DataStructureAndAlgorithms;

import java.util.HashMap;
import java.util.Map;

import DataStructureAndAlgorithms.Solutions.Array.*;
import DataStructureAndAlgorithms.Tests.Array.*;
import DataStructureAndAlgorithms.Solutions.String.*;

public class Solution_Manager {

        private final Map<Solution_Names, Base_Solution<?>> solutionsMap = new HashMap<>();
        private final Map<Solution_Names, Base_Test<?, ?>> testsMap = new HashMap<>();

        private final boolean isTest;
        private final Base_Solution<?> solution;
        private Base_Test<?, ?> testObject;

        public Solution_Manager(Solution_Names solutionName, boolean isTest) {
                this.isTest = isTest;
                initializeSolutionMap();
                this.solution = solutionsMap.get(solutionName);
                if (isTest) {
                        initializeTestMap();
                        this.testObject = testsMap.get(solutionName);
                }
        }

        public Solution_Manager(Solution_Names solutionName) {
                this(solutionName, false);
        }

        private void initializeSolutionMap() {
                solutionsMap.put(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous,
                                new Largest_Sum_Contiguous_Subarray());
                solutionsMap.put(Solution_Names.Print_Matrix_In_Spiral, new Print_Matrix_In_Spiral());
                solutionsMap.put(Solution_Names.Rotate_Array_By_Given_Position,
                                new Rotate_Array_By_Given_Position());
                solutionsMap.put(Solution_Names.Search_Row_And_Column_Wise_In_Matrix,
                                new Search_Row_And_Column_Wise_In_Matrix());
                solutionsMap.put(Solution_Names.Trapping_Rain_Water, new Trapping_Rain_Water());
                solutionsMap.put(Solution_Names.Count_Pairs_With_Given_Sum, new Count_Pairs_With_Given_Sum());
                solutionsMap.put(Solution_Names.Find_Least_Average_Subarray, new Find_Least_Average_Subarray());
                solutionsMap.put(Solution_Names.Convert_Sorted_Array_ZigZag, new Convert_Sorted_Array_ZigZag());
                solutionsMap.put(Solution_Names.Triplet_Sum_In_Array, new Triplet_Sum_In_Array());
                solutionsMap.put(Solution_Names.Is_Anagram, new Is_Anagram());
                solutionsMap.put(Solution_Names.Two_Sum, new Two_Sum());
                solutionsMap.put(Solution_Names.Group_Anagram, new Group_Anagram());
                solutionsMap.put(Solution_Names.Top_K_Frequent, new Top_K_Frequent());
                solutionsMap.put(Solution_Names.Triplet_Sum_Nearest, new Triplet_Sum_Nearest());
                solutionsMap.put(Solution_Names.Four_Sum, new Four_Sum());
                solutionsMap.put(Solution_Names.Remove_Duplicates, new Remove_Duplicates());
                solutionsMap.put(Solution_Names.Remove_Element, new Remove_Element());
                solutionsMap.put(Solution_Names.Next_Permutation, new Next_Permutation());
                solutionsMap.put(Solution_Names.Encode_Decode, new Encode_Decode());
        }

        private void initializeTestMap() {
                testsMap.put(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous,
                                new Largest_Sum_Contiguous_Subarray_Test((Largest_Sum_Contiguous_Subarray) solutionsMap
                                                .get(Solution_Names.Largest_Sum_Contiguous_Subarray_Contiguous)));
                testsMap.put(Solution_Names.Print_Matrix_In_Spiral, new Print_Matrix_In_Spiral_Test(
                                (Print_Matrix_In_Spiral) solutionsMap.get(Solution_Names.Print_Matrix_In_Spiral)));
                testsMap.put(Solution_Names.Rotate_Array_By_Given_Position, new Rotate_Array_By_Given_Position_Test(
                                (Rotate_Array_By_Given_Position) solutionsMap
                                                .get(Solution_Names.Rotate_Array_By_Given_Position)));
                testsMap.put(Solution_Names.Search_Row_And_Column_Wise_In_Matrix,
                                new Search_Row_And_Column_Wise_In_Matrix_Test(
                                                (Search_Row_And_Column_Wise_In_Matrix) solutionsMap
                                                                .get(Solution_Names.Search_Row_And_Column_Wise_In_Matrix)));
                testsMap.put(Solution_Names.Trapping_Rain_Water, new Trapping_Rain_Water_Test(
                                (Trapping_Rain_Water) solutionsMap.get(Solution_Names.Trapping_Rain_Water)));
                testsMap.put(Solution_Names.Find_Least_Average_Subarray, new Find_Least_Average_Subarray_Test(
                                (Find_Least_Average_Subarray) solutionsMap
                                                .get(Solution_Names.Find_Least_Average_Subarray)));
                testsMap.put(Solution_Names.Count_Pairs_With_Given_Sum, new Count_Pairs_With_Given_Sum_Test(
                                (Count_Pairs_With_Given_Sum) solutionsMap
                                                .get(Solution_Names.Count_Pairs_With_Given_Sum)));
                testsMap.put(Solution_Names.Convert_Sorted_Array_ZigZag,
                                new Convert_Sorter_Array_ZigZag_Test((Convert_Sorted_Array_ZigZag) solutionsMap
                                                .get(Solution_Names.Convert_Sorted_Array_ZigZag)));
                testsMap.put(Solution_Names.Top_K_Frequent,
                                new Top_K_Frequent_Test((Top_K_Frequent) solutionsMap
                                                .get(Solution_Names.Top_K_Frequent)));
                testsMap.put(Solution_Names.Triplet_Sum_In_Array,
                                new Triplet_Sum_In_Array_Test((Triplet_Sum_In_Array) solutionsMap
                                                .get(Solution_Names.Triplet_Sum_In_Array)));
                testsMap.put(Solution_Names.Next_Permutation, new Next_Permutation_Test(
                                (Next_Permutation) solutionsMap.get(Solution_Names.Next_Permutation)));

        }

        public void run() {
                if (isTest) {
                        System.out.println("Expected answer: " + solution.solve());
                        System.out.println("Your answer    : " + testObject.test());
                        System.out.println("Result: " + testObject.compare());
                } else {
                        System.out.println("Answer: " + solution.solve());
                }
        }
}
