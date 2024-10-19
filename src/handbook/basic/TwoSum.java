package handbook.basic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dungptm2
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        Map<Integer, Integer> A = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            if (A.containsKey(target - nums[i])) {
                return new int[]{A.get(target - nums[i]), i};
            }
            A.put(nums[i], i);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        TwoSum ts = new TwoSum();
        int[] res = ts.twoSum(nums, target);
        System.out.println(Arrays.toString(res));
        System.exit(0);
    }
}
