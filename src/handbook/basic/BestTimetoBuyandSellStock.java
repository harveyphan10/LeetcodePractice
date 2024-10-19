package handbook.basic;

/**
 * @author dungptm2
 */
public class BestTimetoBuyandSellStock {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int maxSell = prices[n-1];
        int maxProfit = 0;
        for (int i = n-2; i >= 0; i--) {
            maxProfit = Math.max(maxProfit, maxSell - prices[i]);
            if (prices[i] > maxSell) {
                maxSell = prices[i];
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] prices = new int[] {1};
        BestTimetoBuyandSellStock bt = new BestTimetoBuyandSellStock();
        System.out.println(bt.maxProfit(prices));
        System.exit(0);
    }
}
