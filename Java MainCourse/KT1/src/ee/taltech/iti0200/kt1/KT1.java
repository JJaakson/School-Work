package ee.taltech.iti0200.kt1;

import java.util.HashMap;
import java.util.Map;

public class KT1 {
    /**
     * Given a non-empty array of integers,
     * every element appears twice except for one. Find that single one.
     * If there are not such (and in any other case) return 0.
     *
     * singleNumber([2,2,1]) => 1
     * singleNumber([4,1,2,1,2]) => 4
     */
    public static int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
            } else {
                map.replace(nums[i], map.get(nums[i]) + 1);
            }
        }
        for (Map.Entry<Integer, Integer> x : map.entrySet()) {
            if (x.getValue() == 1) {
                return x.getKey();
            }
        }
        return 0;
        }
    }
