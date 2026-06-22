class Solution {
    public int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);

        int count = 0;
        int spent = 0;

        for (int cost : costs) {
            if (spent + cost > coins) {
                break;
            }

            spent += cost;
            count++;
        }

        return count;
    }
}