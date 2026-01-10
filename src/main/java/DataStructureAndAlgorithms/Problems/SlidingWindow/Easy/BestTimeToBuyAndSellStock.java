package DataStructureAndAlgorithms.Problems.SlidingWindow.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

@Problem(name = "Best Time To Buy And Sell Stock", category = "Sliding Window", difficulty = "Easy")
public class BestTimeToBuyAndSellStock extends BaseProblem<Integer> {
    public int[] prices = new int[]{7, 1, 5, 3, 6, 4};

    @Override
    public Integer solve() {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }
        return maxProfit;

    }
}
