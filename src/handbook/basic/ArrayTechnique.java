package handbook.basic;

import java.util.*;

/**
 * @author dungptm2
 */
public class ArrayTechnique {

    /**
     * To find the maximum sum of all subarrays of size K
     * Given an array of integers of size ‘n’, Our aim is to calculate the maximum sum of ‘k’ consecutive elements in the array.
     */
    public int maxSumK(int[] a, int k) {
        int n = a.length;
        int maxSum = 0;

        for (int i = 0; i < k; i++) {
            maxSum += a[i];
        }

        for (int i = 1; i + k < n; i++) {
            int nextSum = maxSum + a[i + k] - a[i];
            maxSum = Math.max(nextSum, maxSum);
        }
        return maxSum;
    }

    /**
     * Smallest subarray with sum greater than a given value
     * Given an array arr[] of integers and a number X,
     * the task is to find the smallest subarray with a sum greater than the given value.
     */
    public int smallestSubarrayX(int[] a, int X) {
        int n = a.length;

        int start = 0;
        int end = 0;
        int sum = a[end];
        int minLen = Integer.MAX_VALUE;
        while (end < n && start <= end) {
            if (sum > X) {
                if (minLen > (end - start + 1)) {
                    minLen = end - start + 1;
                }
                sum -= a[start];
                start++;
            } else {
                end++;
                if (end < n) {
                    sum += a[end];
                }
            }
        }
        return minLen;
    }

    /**
     * Find subarray with given sum in an array of non-negative integers
     * Given an array arr[] of non-negative integers and an integer sum, find a subarray that adds to a given sum.
     */
    public int[] subArraySumK(int[] a, int K) {
        int n = a.length;
        int start = 0, end = 0, sum = a[end];

        while (end < n && start <= end) {
            if (sum < K) {
                end++;
                if (end < n) {
                    sum += a[end];
                }
            } else if (sum > K) {
                start++;
                sum -= a[start];
            } else {
                return new int[]{start, end};
            }
        }
        return null;
    }

    /**
     * Smallest window in a string containing all the characters of another string
     * Given two strings s and p.
     * Find the smallest window in the string s consisting of all the characters(including duplicates) of the string p.
     */
    public int[] smallestContainOther(String s, String p) {
        int n = s.length();
        int m = p.length();
        Map<Character, Integer> hp = new HashMap<>(m);
        Map<Character, Integer> hc = new HashMap<>(m);
        for (int i = 0; i < m; i++) {
            hp.put(p.charAt(i), hp.getOrDefault(p.charAt(i), 0) + 1);
        }

        int start = 0, end = 0;
        int minStart = 0, minEnd = n;
        boolean sameSlide = true;
        for (int i = 0; i < n; i++) {
            if (hp.containsKey(s.charAt(i))) {
                start = end = i;
                hc.put(s.charAt(i), hc.getOrDefault(s.charAt(i), 0) + 1);
                while (end < n) {
                    if (sameSlide) {
                        end++;
                        if (end >= n) {
                            break;
                        }
                        hc.put(s.charAt(end), hc.getOrDefault(s.charAt(end), 0) + 1);
                    }
                    if (hp.containsKey(s.charAt(end)) && end - start + 1 >= m) {
                        boolean equalP = true;
                        for (Map.Entry<Character, Integer> entry : hp.entrySet()) {
                            if (hc.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                                equalP = false;
                                break;
                            }
                        }
                        if (equalP) {
                            if (end - start < minEnd - minStart) {
                                minEnd = end;
                                minStart = start;
                            }
                            hc.put(s.charAt(start), hc.getOrDefault(s.charAt(start), 0) - 1);
                            start++;
                            sameSlide = false;
                        } else {
                            sameSlide = true;
                        }
                    } else {
                        sameSlide = true;
                    }
                }
                break;
            }
        }
        return new int[]{minStart, minEnd};
    }

    /**
     * Longest Substring Without Repeating Characters
     */
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int start = 0, end = 0, maxLen = 0;
        Map<Character, Integer> map = new HashMap<>();
        boolean sameSlide = true;

        while (end < n) {
            if (sameSlide) {
                map.put(s.charAt(end), map.getOrDefault(s.charAt(end), 0) + 1);
            }
            if (map.get(s.charAt(end)) == 1) {
                maxLen = Math.max(maxLen, end - start + 1);
                end++;
                sameSlide = true;
            } else {
                map.put(s.charAt(start), map.getOrDefault(s.charAt(start), 0) - 1);
                start++;
                sameSlide = false;
            }
            if (start == end) {
                end++;
                sameSlide = true;
            }
        }
        return maxLen;
    }

    /**
     * Minimum Size Subarray Sum
     */
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int start = 0, end = 0, minLen = n + 1, sum = 0;
        boolean sameSlide = true;

        while (end < n) {
            if (sameSlide) {
                sum += nums[end];
            }
            if (sum < target) {
                end++;
                sameSlide = true;
            } else {
                minLen = Math.min(end - start + 1, minLen);
                sum -= nums[start];
                start++;
                sameSlide = false;
            }
            if (start > end) {
                end++;
                sameSlide = false;
            }
        }
        return minLen > n ? 0 : minLen;
    }

    /**
     * Minimum Window Substring
     */
    public String minWindow(String s, String t) {
        int n = s.length();
        int m = t.length();
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        int minLen = n + 1, start = 0, end = 0;
        int rStart = 0, rEnd = 0;
        boolean sameSlide = true;
        int[] ht = new int[128];
        int[] hc = new int[128];

        if (n < m) {
            return "";
        }

        for (char ct : tArr) {
            ht[ct]++;
        }

        while (end < n) {
            if (sameSlide) {
                hc[sArr[end]]++;
            }

            if (end - start + 1 >= m) {
                boolean equalT = true;
                for (int i = 0; i < 128; i++) {
                    if (hc[i] < ht[i]) {
                        equalT = false;
                        break;
                    }
                }
                if (equalT) {
                    if (end - start + 1 < minLen) {
                        minLen = end - start + 1;
                        rStart = start;
                        rEnd = end;
                        if (minLen == m) {
                            break;
                        }
                    }
                    hc[sArr[start]]--;
                    start++;
                    sameSlide = false;
                } else {
                    sameSlide = true;
                    end++;
                }
            } else {
                sameSlide = true;
                end++;
            }
            if (start > end) {
                end++;
                sameSlide = false;
            }
        }
        return minLen > n ? "" : s.substring(rStart, rEnd + 1);
    }


    public int[] closestPair2Array(int[] a, int[] b, int x) {
        int diff = Integer.MAX_VALUE;
        int n = a.length;
        int m = b.length;
        int l = 0, r = m - 1, rL = 0, rR = 0;

        while (l < n && r >= 0) {
            if (Math.abs(a[l] + a[r] - x) < diff) {
                diff = Math.abs(a[l] + a[r] - x);
                rL = l;
                rR = r;
            }
            if (a[l] + a[r] > x) {
                r--;
            } else {
                l++;
            }
        }
        return new int[]{rL, rR};
    }

    public int[] closestPair1Array(int[] a, int x) {
        int n = a.length;
        int diff = Integer.MAX_VALUE;
        int l = 0, r = n - 1, rL = 0, rR = 0;

        while (l < n && r >= 0) {
            if (a[l] + a[r] == x) {
                rL = l;
                rR = r;
                break;
            } else if (Math.abs(a[l] + a[r] - x) < diff) {
                diff = Math.abs(a[l] + a[r] - x);
                rL = l;
                rR = r;
            }
            if (a[l] + a[r] > x) {
                r = (r + l) / 2 - 1;
            } else {
                l = (l + r) / 2 + 1;
            }
        }
        return new int[]{rL, rR};
    }

    /**
     * Sort Colors
     */
    public void sortColors(int[] nums) {
        int n = nums.length;
        int[] index = new int[2];

        for (int i = 0; i < n; i++) {
            while (i - 1 >= 0 && nums[i] < nums[i - 1]) {
                int cursor = nums[i];
                swap(nums, i, index[cursor]);
                index[cursor]++;
                if (cursor + 1 < index.length && index[cursor + 1] < index[cursor]) {
                    index[cursor + 1]++;
                }
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public int countSubstrings(String s) {
        int n = s.length();
        int[][] B = new int[n + 1][n + 1];

        for (int i = 0; i < n; i++) {
            B[i][i] = 1;
            if (i + 1 < n && s.charAt(i + 1) == s.charAt(i)) {
                B[i][i + 1] = 1;
            }
        }

        int total = 0;
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                if (len >= 3 && s.charAt(i) == s.charAt(i + len - 1)) {
                    B[i][i + len - 1] = B[i + 1][i + len - 2];
                }
                total += B[i][i + len - 1];
            }
        }
        return total;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i1 = 0, i2 = 0;
        if (n == 0) {
            return;
        }
        if (m == 0) {
            System.arraycopy(nums2, 0, nums1, 0, n);
            return;
        }
        int curLen1 = m;
        while (i1 < curLen1 && i2 < n) {
            while (i1 < curLen1 && nums1[i1] <= nums2[i2]) {
                i1++;
            }
            int fromI2 = i2;
            while (i2 < n && nums2[i2] <= nums1[i1]) {
                i2++;
            }

            if (i2 - fromI2 > 0) {
                for (int i = m - 1 + fromI2; i >= i1; i--) {
                    nums1[i + (i2 - fromI2)] = nums1[i];
                }
                System.arraycopy(nums2, fromI2, nums1, i1, i2 - fromI2);
                i1 += i2 - fromI2;
                curLen1 += i2 - fromI2;
            }
        }
        if (i2 < n) {
            System.arraycopy(nums2, i2, nums1, nums1.length - (n - i2), n - i2);
        }
    }

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        for (int k = nums1.length - 1; k >= 0; k--) {
            if (j < 0) {
                break;
            }
            if (i >= 0 && nums1[i] >= nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }

        }
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] A = new int[n];
        A[n - 1] = 0;

        for (int i = n - 2; i >= 0; i--) {
            if (temperatures[i + 1] > temperatures[i]) {
                A[i] = 1;
            } else if (temperatures[i] == temperatures[i + 1]) {
                if (A[i + 1] == 0) {
                    A[i] = 0;
                } else {
                    A[i] = A[i + 1] + 1;
                }
            } else {
                int total = 1 + A[i + 1];
                int j = i + 1 + A[i + 1];
                while (A[j] != 0 && temperatures[i] >= temperatures[j]) {
                    total += A[j];
                    j += A[j];
                }
                if (temperatures[j] > temperatures[i]) {
                    A[i] = total;
                } else if (temperatures[j] == temperatures[i] && A[j] != 0) {
                    A[i] = total;
                }
            }
        }
        return A;
    }

    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] A = new int[n];
        int[] B = new int[n];

        if (n == 1) {
            return A;
        }

        A[n - 1] = 0;
        A[n - 2] = 1;
        B[n - 1] = -1;
        B[n - 2] = n - 1;

        for (int i = n - 3; i >= 0; i--) {
            if (heights[i + 1] > heights[i]) {
                A[i] = 1;
                B[i] = i + 1;
            } else {
                int j = i + 1;
                int prev = 0;
                while (j > 0 && j < n && heights[j] > prev && heights[j] < heights[i]) {
                    A[i]++;
                    prev = heights[j];
                    j = B[j];
                }
                if (j >= 0 && j < n && heights[j] > heights[i]) {
                    A[i]++;
                }
                B[i] = j;
            }
        }
        return A;
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });
        Stack<int[]> s = new Stack<>();
        for (int i = 0; i < intervals.length; i++) {
            if (s.isEmpty()) {
                s.add(intervals[i]);
                continue;
            }
            int[] tail = s.peek();
            if (tail[1] >= intervals[i][0]) {
                int[] merged = new int[]{tail[0], Math.max(tail[1], intervals[i][1])};
                s.pop();
                s.add(merged);
            } else {
                s.add(intervals[i]);
            }
        }
        return s.toArray(new int[s.size()][]);
    }

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n];

        pre[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            pre[i] = pre[i + 1] * nums[i];
        }
        for (int i = 1; i < n; i++) {
            nums[i] = nums[i - 1] * nums[i];
        }
        pre[0] = pre[1];
        for (int i = 1; i < n - 1; i++) {
            pre[i] = pre[i + 1] * nums[i - 1];
        }
        pre[n - 1] = nums[n - 2];
        return pre;
    }

    public int maxSubArray(int[] A) {
        int n = A.length;
        int i = 0, j = 0;
        int maxSum = Integer.MIN_VALUE, sum = 0;
        while (i < n && j < n) {
            sum += A[j];
            if (A[j] > sum) {
                i = j;
                sum = A[i];
            }
            if (sum > maxSum) {
                maxSum = sum;
            }
            j++;
        }
        return maxSum;
    }

    public boolean containsDuplicate(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

    public int maxProduct(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        int maxL = 1, maxR = 1, max = nums[0];

        while (l < n && r >= 0) {
            if (maxL == 0) {
                maxL = 1;
            }
            if (maxR == 0) {
                maxR = 1;
            }
            maxL = maxL * nums[l];
            maxR = maxR * nums[r];
            max = Math.max(max, Math.max(maxL, maxR));
            l++;
            r--;
        }
        return max;
    }


    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[l] <= nums[mid]) {
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else if (nums[mid] < nums[r]) {
                if (nums[mid] < target && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    public int maxArea(int[] h) {
        int l = 0, r = h.length - 1;
        int max = 0;

        while (l < r) {
            max = Math.max(max, Math.min(h[l], h[r]) * (r - l));
            if (h[l] < h[r]) {
                l++;
            } else {
                r--;
            }
        }
        return max;
    }


    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(n);
        Arrays.sort(nums);

        for (int i = 0; i < n; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                continue;
            }
            int target = -nums[i];
            for (int j = i + 1; j < n; j++) {
                int rest = target - nums[j];
                if (map.getOrDefault(rest,0) > j) {
                    result.add(Arrays.asList(nums[i], nums[j], rest));
                    j = map.get(nums[j]);
                }
            }
            i = map.get(nums[i]);
        }
        return result;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        ArrayDeque<Integer> deque = new ArrayDeque<>(n);


        PriorityQueue<Integer> heap = new PriorityQueue<>(k, Comparator.reverseOrder());

        for (int i = 0; i < k; i++) {
            heap.add(nums[i]);
        }
        res[0] = heap.peek();
        for (int i = 1; i + k <= n; i++) {
            int remove = nums[i - 1];
            int add = nums[i + k - 1];
            heap.remove(remove);
            heap.add(add);
            res[i] = heap.peek();
        }
        return res;
    }


    public static void main(String[] args) {
        ArrayTechnique ss = new ArrayTechnique();
        List<List<Integer>> res = ss.threeSum(new int[]{-1,0,1,2,-1,-4});
        System.out.println(res);
        System.exit(0);
    }


}
