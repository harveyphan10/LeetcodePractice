package handbook.basic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dungptm2
 */
public class Daily {

    public int maxTwoEvents(int[][] events) {
        Arrays.sort(events, Comparator.comparingInt(o -> o[0]));
        int[] values = new int[events.length];
        Arrays.sort(values);
        int n = events.length;
        int[] L = new int[n + 1];
        L[n] = 0;
        L[n - 1] = events[n - 1][2];

        for (int i = n - 2; i >= 0; i--) {
            L[i] = Math.max(L[i + 1], events[i][2]);
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            int k = binarySearch(events, i + 1, events[i][1]);
            if (k <= n) {
                max = Math.max(max, events[i][2] + L[k]);
            }
        }
        return max;

    }

    private int binarySearch(int[][] a, int i, int end) {
        int l = i, r = a.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (a[mid][0] > end) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public static class SnapE {
        int v;
        int snapId;

        public SnapE(int v, int snapId) {
            this.v = v;
            this.snapId = snapId;
        }
    }

    public static class SnapshotArray {

        private int snapId;
        private final int[] array;
        private final Map<Integer, List<SnapE>> map;
        private Set<Integer> indexChange;
        private boolean changed;

        public SnapshotArray(int length) {
            snapId = 0;
            array = new int[length];
            map = new HashMap<>();
            indexChange = new HashSet<>();
            changed = false;
        }

        public void set(int index, int val) {
            array[index] = val;
            indexChange.add(index);
            changed = true;
        }

        public int snap() {
            if (changed) {
                for (int i : indexChange) {
                    List<SnapE> snapsE = map.computeIfAbsent(i, k -> new ArrayList<>());
                    if (snapsE.isEmpty() || snapsE.getLast().v != array[i]) {
                        snapsE.add(new SnapE(array[i], snapId));
                    }
                }
            }
            changed = false;
            return snapId++;
        }

        public int get(int index, int snap_id) {
            List<SnapE> snapES = map.get(index);
            if (snapES == null || snapES.isEmpty()) {
                return 0;
            }
            int findI = binarySearchSnap(snapES, snap_id);
            return findI < 0 ? 0 : snapES.get(findI).v;
        }

        private int binarySearchSnap(List<SnapE> snapES, int snap_id) {
            int l = 0, r = snapES.size() - 1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (snapES.get(mid).snapId == snap_id) {
                    return mid;
                } else if (snapES.get(mid).snapId > snap_id) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            return l - 1;
        }
    }

    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int n = nums.length;
        boolean[] res = new boolean[queries.length];
        int[] countSame = new int[n];
        countSame[0] = 0;
        for (int i = 1; i < n; i++) {
            countSame[i] = countSame[i - 1];
            if (nums[i] % 2 == nums[i - 1] % 2) {
                countSame[i]++;
            }
        }
        for (int i = 0; i < queries.length; i++) {
            int nSame = countSame[queries[i][1]] - countSame[queries[i][0]];
            res[i] = nSame == 0;
        }
        return res;
    }


    public int maximumLength(String s) {
        int n = s.length();
        Map<String, Integer> map = new HashMap<>();
        int l = 0, r = 0;
        Map<Character, Integer> countChar = new HashMap<>(2);
        while (l < n) {
            if (r >= n) {
                l++;
                r = l;
                countChar.clear();
                if (l >= n) {
                    break;
                }
            }
            countChar.put(s.charAt(l), countChar.getOrDefault(s.charAt(l), 0) + 1);
            countChar.put(s.charAt(r), countChar.getOrDefault(s.charAt(r), 0) + 1);
            if (countChar.size() == 1) {
                String subStr = s.substring(l, r + 1);
                map.put(subStr, map.getOrDefault(subStr, 0) + 1);
                r++;
            }
            if (countChar.size() == 2) {
                countChar.remove(s.charAt(l));
                countChar.remove(s.charAt(r));
                l++;
                r = l;
            }
        }
        int maxLen = -1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 3 && entry.getKey().length() > maxLen) {
                maxLen = entry.getKey().length();
            }
        }
        return maxLen;
    }

    public static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int maximumBeauty(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int maxLen = 0;
        int iMinElem = 0;
        for (int iMaxElem = 0; iMaxElem < n; iMaxElem++) {
            while (nums[iMaxElem] - nums[iMinElem] > 2 * k) {
                iMinElem++;
            }
            maxLen = Math.max(maxLen, iMaxElem - iMinElem + 1);
        }

        return maxLen;
    }

    private boolean hasUnion(Pair a, Pair b) {
        if (b.x <= a.y && a.y <= b.y) {
            return true;
        }
        if (b.x <= a.x && a.x <= b.y) {
            return true;
        }
        return false;
    }

    public int longestSemiRepetitiveSubstring(String s) {
        if (s.length() == 1) {
            return 1;
        }
        int l = 0, r = 1;
        int iRepeated = -1;
        int maxLen = 0;
        while (l <= r && r < s.length()) {
            if (s.charAt(r) == s.charAt(r - 1)) {
                if (iRepeated < 0) {
                    iRepeated = r - 1;
                } else {
                    maxLen = Math.max(maxLen, r - l);
                    l = iRepeated + 1;
                    iRepeated = r - 1;
                }
            }
            r++;
        }

        return Math.max(maxLen, r - l);
    }

    public int maximizeWin2(int[] prizePositions, int k) {
        int n = prizePositions.length;
        List<Pair> pairs = new ArrayList<>();

        int countMaxPrice = 0;
        for (int iS1 = 0; iS1 < n; iS1++) {
            int s1 = prizePositions[iS1];
            int e1 = s1 + k;
            int iE1 = findMaxIndexLTEValue(prizePositions, iS1, n - 1, e1);
            pairs.add(new Pair(iS1, iE1));
            iS1 = iE1;
        }

        for (int i = 0; i < pairs.size(); i++) {
            int current = pairs.get(i).y - pairs.get(i).x + 1;
            countMaxPrice = Math.max(countMaxPrice, current);
            for (int j = i + 1; j < pairs.size(); j++) {
                countMaxPrice = Math.max(countMaxPrice, current + pairs.get(j).y - pairs.get(j).x + 1);
            }
        }

        return countMaxPrice;
    }

    private int findMaxIndexLTEValue(int[] a, int start, int end, int value) {
        int l = start, r = end;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (a[mid] <= value) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l - 1;
    }

    public int maximizeWin(int[] A, int k) {
        int maxCountElem = 1;
        int n = A.length;
        int[] L = new int[n + 1];
        L[0] = 1;

        int j = 0;
        for (int i = 1; i < n; i++) {
            while (A[j] < A[i] - k) {
                j++;
            }
            L[i] = Math.max(L[i - 1], i - j + 1);
            if (j > 0) {
                maxCountElem = Math.max(maxCountElem, i - j + 1 + L[j - 1]);
            } else {
                maxCountElem = Math.max(maxCountElem, i + 1);
            }
        }

        return maxCountElem;
    }

    class Pair3 {
        int v;
        int index;

        public Pair3(int v, int index) {
            this.v = v;
            this.index = index;
        }
    }

    public long findScore(int[] nums) {
        PriorityQueue<Pair3> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1.v != o2.v) {
                return o1.v - o2.v;
            } else {
                return o1.index - o2.index;
            }
        });
        for (int i = 0; i < nums.length; i++) {
            pq.offer(new Pair3(nums[i], i));
        }
        long sum = 0;
        while (!pq.isEmpty()) {
            Pair3 poll = pq.poll();
            if (nums[poll.index] >= 0) {
                sum += poll.v;
                if (poll.index - 1 >= 0 && nums[poll.index - 1] >= 0) {
                    nums[poll.index - 1] *= -1;
                }
                if (poll.index + 1 < nums.length && nums[poll.index + 1] >= 0) {
                    nums[poll.index + 1] *= -1;
                }
            }
        }
        return sum;

    }

    public long countGood(int[] nums, int k) {
        if (nums.length == 1) {
            return 0;
        }
        int n = nums.length;
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int l = 0, r = 1;
        map.put(nums[l], map.getOrDefault(nums[l], 0) + 1);
        int count = 0;
        while (r < n) {
            map.put(nums[r], map.getOrDefault(nums[r], 0) + 1);
            if (map.get(nums[r]) >= 2) {
                count += map.get(nums[r]) - 1;
                if (count >= k) {
                    result++;
                }
            }
            r++;
        }
        while (l < n && count >= k) {
            map.put(nums[l], map.getOrDefault(nums[l], 0) - 1);
            if (map.get(nums[l]) >= 1) {
                count -= map.get(nums[l]);
                if (count >= k) {
                    result++;
                }
            } else {
                result++;
            }
            l++;
        }

        return result;
    }

    public long continuousSubarrays(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        int n = nums.length;
        long result = 0;
        int l = 0, r = 0;
        int curMax = nums[0], curMin = nums[0];
        while (r < n) {
            curMax = Math.max(curMax, nums[r]);
            curMin = Math.min(curMin, nums[r]);
            if (curMax - curMin > 2) {
                long windowSize = r - l;
                result += windowSize * (windowSize + 1) / 2;
                l = r;
                curMax = nums[l];
                curMin = nums[l];
                while (l > 0 && Math.abs(nums[r] - nums[l - 1]) <= 2) {
                    l--;
                    curMax = Math.max(curMax, nums[l]);
                    curMin = Math.min(curMin, nums[l]);
                }
                long minusWindowSize = r - l;
                result -= minusWindowSize * (minusWindowSize + 1) / 2;
            }
            r++;
        }
        long windowSize = r - l;
        result += windowSize * (windowSize + 1) / 2;
        return result;
    }

    public double maxAverageRatio(int[][] a, int extraStudents) {
        int n = a.length;
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> {
            double diff1 = ((double) o1[0] + 1) / ((double) o1[1] + 1) - ((double) o1[0] / (double) o1[1]);
            double diff2 = ((double) o2[0] + 1) / ((double) o2[1] + 1) - ((double) o2[0] / (double) o2[1]);

            return diff1 - diff2 == 0 ? 0 : diff1 - diff2 > 0 ? -1 : 1;
        });
        for (int i = 0; i < n; i++) {
            q.offer(a[i]);
        }
        while (!q.isEmpty() && extraStudents > 0) {
            int[] poll = q.poll();
            poll[0] = poll[0] + 1;
            poll[1] = poll[1] + 1;
            q.offer(poll);
            extraStudents--;
        }
        double result = 0;
        while (!q.isEmpty()) {
            int[] poll = q.poll();
            result += (double) poll[0] / (double) poll[1];
        }
        return BigDecimal.valueOf(result / n).setScale(5, RoundingMode.HALF_UP).doubleValue();
    }

    public int[] getFinalState(int[] nums, int k, int multiplier) {
        PriorityQueue<int[]> q = new PriorityQueue<>(nums.length, (int[] o1, int[] o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        });
        for (int i = 0; i < nums.length; i++) {
            q.offer(new int[]{nums[i], i});
        }
        while (!q.isEmpty() && k > 0) {
            int[] poll = q.poll();
            poll[0] *= multiplier;
            q.offer(poll);
            k--;
        }
        while (!q.isEmpty()) {
            int[] poll = q.poll();
            nums[poll[1]] = poll[0];
        }

        return nums;
    }

    public String repeatLimitedString(String s, int repeatLimit) {
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        PriorityQueue<Character> q = new PriorityQueue<>(Comparator.reverseOrder());

        map.forEach((key, value) -> q.offer(key));

        int k = 0;
        Character remindC = null;
        Character lastC = null;
        while (!q.isEmpty()) {
            Character c = q.peek();
            if (c != lastC) {
                k = 0;
                lastC = c;
            }
            k++;
            sb.append(c);
            map.put(c, map.get(c) - 1);
            if (map.get(c) == 0) {
                map.remove(c);
                q.poll();
            }
            if (remindC != null) {
                q.offer(remindC);
                remindC = null;
            } else if (k >= repeatLimit && map.containsKey(c)) {
                remindC = q.poll();
            }
        }
        return sb.toString();
    }

    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && prices[stack.peek()] >= prices[i]) {
                prices[stack.pop()] -= prices[i];
            }
            stack.push(i);
        }
        return prices;
    }

    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (stack.isEmpty() || stack.peek() < arr[i]) {
                stack.push(arr[i]);
                continue;
            }
            int maxCur = arr[i];
            while (!stack.isEmpty() && stack.peek() > arr[i]) {
                maxCur = Math.max(maxCur, stack.pop());
            }
            stack.push(maxCur);
        }
        return stack.size();
    }

    public int[] mostCompetitive(int[] nums, int k) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && stack.peek() > nums[i] && stack.size() + (n - i - 1) >= k) {
                stack.pop();
            }
            if (stack.size() < k) {
                stack.push(nums[i]);
            }
        }
        int[] result = new int[k];
        int i = k - 1;
        while (!stack.isEmpty()) {
            result[i--] = stack.pop();
        }
        return result;
    }

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] left = new int[n + 1];
        int[] right = new int[n + 1];
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] > nums[j]) {
                    left[i] = Math.max(left[i], left[j] + 1);
                }
            }
        }
        right[n] = 0;
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (nums[i] > nums[j]) {
                    right[i] = Math.max(right[i], right[j] + 1);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (left[i] == 1 || right[i] == 1) {
                continue;
            }
            max = Math.max(max, left[i] + right[i]);
        }
        return n - max + 1;
    }

    public static class Pair4 {
        int sum;
        int index;

        public Pair4(int sum, int index) {
            this.sum = sum;
            this.index = index;
        }
    }

    public int constrainedSubsetSum(int[] nums, int k) {
        int n = nums.length;
        int[] L = new int[n + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        L[n] = 0;
        int max = nums[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            L[i] = nums[i] + (deque.isEmpty() ? 0 : deque.peekFirst());
            max = Math.max(max, L[i]);
            while (!deque.isEmpty() && deque.peekLast() < L[i]) {
                deque.pollLast();
            }
            if (L[i] > 0) {
                deque.addLast(L[i]);
            }
            if (i < n - k && !deque.isEmpty() && deque.peekFirst() == L[i + k]) {
                deque.pollFirst();
            }
        }
        return max;
    }

    static class Pair5 {
        int v;
        int i;

        public Pair5(int v, int i) {
            this.v = v;
            this.i = i;
        }
    }

    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        int n = heights.length;
        int m = queries.length;
        int[] result = new int[m];
        Arrays.fill(result, -1);
        List<int[]>[] que = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            que[i] = new ArrayList<>();
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        for (int i = 0; i < m; i++) {
            int i1 = queries[i][0];
            int i2 = queries[i][1];
            int h1 = heights[i1];
            int h2 = heights[i2];
            if (i1 == i2) {
                result[i] = i1;
                continue;
            }
            if (i1 > i2 && h1 > h2) {
                result[i] = i1;
                continue;
            }
            if (i2 > i1 && h2 > h1) {
                result[i] = i2;
                continue;
            }
            int fromIndex = Math.max(i1, i2);
            int fromValue = Math.max(h1, h2);

            que[fromIndex].add(new int[]{fromValue, i});
        }

        for (int i = 0; i < n; i++) {
            for (int[] q : que[i]) {
                pq.offer(q);
            }
            while (!pq.isEmpty() && pq.peek()[0] < heights[i]) {
                result[pq.poll()[1]] = i;
            }
        }

        return result;
    }

    public String MinWindownSubString(String[] strArr) {
        String N = strArr[0];
        String K = strArr[1];
        int n = N.length();
        int k = K.length();
        Map<Character, Integer> mapK = new HashMap<>();
        for (int i = 0; i < k; i++) {
            mapK.put(K.charAt(i), mapK.getOrDefault(K.charAt(i), 0) + 1);
        }
        int iStart = 0;
        int min = n;
        int l = 0, r = 0;
        Map<Character, Integer> mapSub = new HashMap<>();
        mapSub.put(N.charAt(r), mapSub.getOrDefault(N.charAt(r), 0) + 1);
        while (l <= r && r < n) {
            boolean contain = contain(mapK, mapSub);
            if (contain) {
                if (r - l + 1 < min) {
                    iStart = l;
                    min = r - l + 1;
                }
                mapSub.put(N.charAt(l), mapSub.getOrDefault(N.charAt(l), 0) - 1);
                l++;
            } else {
                r++;
                if (r < n) {
                    mapSub.put(N.charAt(r), mapSub.getOrDefault(N.charAt(r), 0) + 1);
                }
            }
        }
        return N.substring(iStart, iStart + min);
    }

    private static boolean contain(Map<Character, Integer> a, Map<Character, Integer> b) {
        for (Map.Entry<Character, Integer> entry : a.entrySet()) {
            Integer count = b.getOrDefault(entry.getKey(), 0);
            if (count < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public String BracketMatcher(String str) {
        int n = str.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) == '(') {
                stack.push(str.charAt(i));
            } else if (str.charAt(i) == ')') {
                if (stack.isEmpty()) {
                    return "0";
                }
                stack.pop();
            }
        }
        return stack.isEmpty() ? "1" : "0";
    }

    public String TreeConstructor(String[] strArr) {
        Map<Integer, Integer> nodeNchild = new HashMap<>();
        Map<Integer, Boolean> nodeHasParent = new HashMap<>();
        int n = strArr.length;
        for (int i = 0; i < n; i++) {
            String[] split = strArr[i].split("[(),]");
            Integer child = Integer.parseInt(split[1]);
            Integer parent = Integer.parseInt(split[2]);
            nodeNchild.put(parent, nodeNchild.getOrDefault(parent, 0) + 1);
            if (nodeNchild.get(parent) > 2) {
                return "false";
            }
            nodeHasParent.put(child, true);
        }
        boolean metRoot = false;
        for (Map.Entry<Integer, Integer> entry : nodeNchild.entrySet()) {
            if (entry.getValue() == 1 && !nodeHasParent.getOrDefault(entry.getKey(), false) && metRoot) {
                return "false";
            }
            if (entry.getValue() == 1 && !nodeHasParent.getOrDefault(entry.getKey(), false)) {
                metRoot = true;
            }
        }
        return "true";
    }

    private static int count = 0;

    public int BracketCombination(int num) {
        recurseCombination("", 0, 0, num);
        return count;
    }

    private void recurseCombination(String s, int l, int r, int n) {
        if (s.length() == 2 * n) {
            count++;
            return;
        }
        if (l < n) {
            recurseCombination(s + "(", l + 1, r, n);
        }
        if (r < l) {
            recurseCombination(s + ")", l, r + 1, n);
        }
    }

    public static String QuestionsMarks(String str) {
        // code goes here
        Map<Integer, List<Integer>> numberIndexes = new HashMap<>();
        List<int[]> pair10s = new ArrayList<>();
        int n = str.length();
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                Integer number = str.charAt(i) - '0';
                if (numberIndexes.containsKey(10 - number)) {
                    for (int index : numberIndexes.get(10 - number)) {
                        pair10s.add(new int[]{i, index});
                    }
                }
                numberIndexes.computeIfAbsent(number, k -> new ArrayList<>()).add(i);
            }
        }
        for (int[] pair : pair10s) {
            int count = 0;
            int fromIndex = Math.min(pair[0], pair[1]);
            int toIndex = Math.max(pair[0], pair[1]);
            for (int i = fromIndex + 1; i < toIndex; i++) {
                if (str.charAt(i) == '?') {
                    count++;
                }
            }
            if (count == 3) {
                return "true";
            }
        }
        return "false";
    }

    public int[] vowelStrings(String[] words, int[][] queries) {
        int n = words.length;
        int[] L = new int[n + 1];
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        for (int i = n - 1; i >= 0; i--) {
            if (vowels.contains(words[i].charAt(0)) && vowels.contains(words[i].charAt(words[i].length() - 1))) {
                L[i] = 1 + L[i + 1];
            } else {
                L[i] = L[i + 1];
            }
        }
        int m = words.length;
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            int total1 = L[l] - L[r + 1];
            res[i] = total1;
        }
        return res;
    }

    public int waysToSplitArray(int[] nums) {
        int n = nums.length;
        long total = 0;
        for (int e : nums) {
            total += e;
        }
        long sumLeft = 0;
        int count = 0;
        for (int i = 0; i <= n - 2; i++) {
            sumLeft += nums[i];
            if (sumLeft >= total - sumLeft) {
                count++;
            }
        }
        return count;
    }

    public int countPalindromicSubsequence(String s) {
        int total = 0;
        Map<Character, Integer> lastI = new HashMap<>();
        Map<Character, Integer> firstI = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            firstI.putIfAbsent(c, i);
            lastI.put(c, i);
        }
        for (Map.Entry<Character, Integer> entry : lastI.entrySet()) {
            if (firstI.get(entry.getKey()) <= entry.getValue() - 2) {
                String substring = s.substring(firstI.get(entry.getKey()) + 1, lastI.get(entry.getKey()));
                long distinct = substring.chars().distinct().count();
                total += distinct;
            }
        }
        return total;
    }

    public String shiftingLetters(String s, int[][] shifts) {
        int n = s.length();
        int[] L = new int[n + 1];
        for (int[] shift : shifts) {
            if (shift[2] == 1) {
                L[shift[0]]++;
                L[shift[1] + 1]--;
            } else {
                L[shift[0]]--;
                L[shift[1] + 1]++;
            }
        }
        for (int i = 1; i < n; i++) {
            L[i] += L[i - 1];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            int div = (c + L[i]) / 26;
            int mod = ((c + L[i]) - div * 26);

            char newC = '1';
            if (mod >= 0) {
                newC = (char) (mod + 'a');
            } else {
                newC = (char) (26 + mod + 'a');
            }
            sb.append(newC);
        }
        return sb.toString();
    }

    public int[] minOperations(String boxes) {
        int n = boxes.length();
        int[] L = new int[n + 1];
        int[] R = new int[n + 1];
        int[] ball = new int[n + 1];
        L[0] = 0;
        ball[0] = boxes.charAt(0) == '1' ? 1 : 0;
        for (int i = 1; i < n; i++) {
            L[i] = L[i - 1] + ball[i - 1];
            ball[i] = ball[i - 1] + (boxes.charAt(i) == '1' ? 1 : 0);
        }
        for (int i = n - 1; i >= 0; i--) {
            R[i] = R[i + 1] + ball[i + 1];
            ball[i] = ball[i + 1] + (boxes.charAt(i) == '1' ? 1 : 0);
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = L[i] + R[i];
        }
        return res;
    }

    public List<String> stringMatching(String[] words) {
        Set<String> res = new HashSet<>();
        Arrays.sort(words, (o1, o2) -> o2.length() - o1.length());
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].contains(words[j])) {
                    res.add(words[j]);
                }
            }
        }
        return new ArrayList<>(res);
    }

    public int countPrefixSuffixPairs(String[] words) {
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (isPrefixAndSuffix(words[i], words[j])) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isPrefixAndSuffix(String s1, String s2) {
        return s2.startsWith(s1) && s2.endsWith(s1);
    }

    public List<String> wordSubsets(String[] words1, String[] words2) {
        int[] maxMultiC = new int[26];
        for (String s : words2) {
            int[] multiC = new int[26];
            for (char c : s.toCharArray()) {
                multiC[c - 'a']++;
            }
            for (int i = 0; i < multiC.length; i++) {
                maxMultiC[i] = Math.max(maxMultiC[i], multiC[i]);
            }
        }

        List<String> res = new ArrayList<>();
        for (String s : words1) {
            int[] multiC = new int[26];
            for (char c : s.toCharArray()) {
                multiC[c - 'a']++;
            }
            int i = 0;
            for (i = 0; i < multiC.length; i++) {
                if (multiC[i] < maxMultiC[i]) {
                    break;
                }
            }
            if (i == 26) {
                res.add(s);
            }
        }
        return res;
    }

    public boolean canConstruct(String s, int k) {
        int n = s.length();
        if (k > n) return false;

        int[] countC = new int[26];
        for (char c : s.toCharArray()) {
            countC[c - 'a']++;
        }
        int nOddC = 0;
        for (int i = 0; i < 26; i++) {
            if (countC[i] % 2 != 0) {
                nOddC++;
            }
        }
        return nOddC <= k;
    }

    public boolean canBeValid(String s, String locked) {
        int n = s.length();
        if (n % 2 != 0)
            return false;
        Stack<Integer> star = new Stack<>();
        Stack<Integer> open = new Stack<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            char l = locked.charAt(i);
            if (l == '0') {
                star.push(i);
            } else if (c == '(') {
                open.push(i);
            } else if (c == ')') {
                if (!open.isEmpty()) {
                    open.pop();
                } else if (!star.isEmpty()) {
                    star.pop();
                } else {
                    return false;
                }
            }
        }
        while (!star.isEmpty() && !open.isEmpty() && open.peek() < star.peek()) {
            open.pop();
            star.pop();
        }
        if (star.size() % 2 != 0) return false;
        return open.isEmpty();
    }

    public int minimumLength(String s) {
        int[] map = new int[26];
        for (char c : s.toCharArray()) {
            map[c - 'a']++;
        }
        int res = 0;
        for (int i = 0; i < 26; i++) {
            if (map[i] <= 2) {
                res += map[i];
            } else {
                res += map[i] % 2 == 0 ? 2 : 1;
            }
        }
        return res;
    }

    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int n = A.length;
        int[] res = new int[n];
        Set<Integer> set = new HashSet<>();
        res[0] = A[0] == B[0] ? 1 : 0;
        set.add(A[0]);
        set.add(B[0]);
        for (int i = 1; i < n; i++) {
            if (A[i] == B[i]) {
                res[i] = 1 + res[i - 1];
                set.add(A[i]);
            } else {
                int same = 0;
                if (!set.add(A[i])) {
                    same++;
                }
                if (!set.add(B[i])) {
                    same++;
                }
                res[i] = same + res[i - 1];
            }
        }
        return res;
    }

    public int minimizeXor(int num1, int num2) {
        int n1 = Integer.bitCount(num1);
        int n2 = Integer.bitCount(num2);
        if (n1 == n2) {
            return num1;
        }
        int res = num1;
        for (int i = 0; i < 32; i++) {
            int x = (1 << i) & num1;
            if (n1 > n2 && x > 0) {
                res ^= 1 << i;
                n1--;
            }
            if (n1 < n2 && x == 0) {
                res ^= 1 << i;
                n2--;
            }
        }
        return res;
    }

    public int xorAllNums(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        int res = 0;
        if (n1 % 2 == 0 && n2 % 2 == 0) {
            return res;
        }
        if (n2 % 2 != 0) {
            for (int e : nums1) {
                res ^= e;
            }
        }
        if (n1 % 2 != 0) {
            for (int e : nums2) {
                res ^= e;
            }
        }
        return res;
    }

    public boolean doesValidArrayExist(int[] derived) {
        int xorSum = 0;
        for (int e : derived) {
            xorSum ^= e;
        }
        return xorSum == 0;
    }

    public int minCost(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] aCost = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(aCost[i], Integer.MAX_VALUE);
        }
        aCost[0][0] = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        int[][] directions = new int[][]{{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        queue.offer(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int cost = poll[0];
            int r = poll[1], c = poll[2];
            for (int i = 1; i <= 4; i++) {
                int nR = r + directions[i][0];
                int nC = c + directions[i][1];
                if (nR >= 0 && nR < n && nC >= 0 && nC < m) {
                    int nCost = cost;
                    if (i != grid[r][c]) {
                        nCost++; // have to change direction
                    }
                    if (nCost < aCost[nR][nC]) {
                        aCost[nR][nC] = nCost;
                        queue.offer(new int[]{nCost, nR, nC});
                    }
                }
            }
        }
        return aCost[n - 1][m - 1];
    }

    public long gridGame(int[][] grid) {
        int n = grid[0].length;
        long sum1 = 0;
        for (int i = 0; i < n; i++) {
            sum1 += grid[0][i];
        }
        long curSum1 = sum1, curSum2 = 0;
        long res = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            curSum1 -= grid[0][i];
            if (i > 0) {
                curSum2 += grid[1][i - 1];
            }
            res = Math.min(res, Math.max(curSum1, curSum2));
        }
        return res;
    }

    public int[][] highestPeak(int[][] isWater) {
        int n = isWater.length;
        int m = isWater[0].length;
        int[][] res = new int[n][m];
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (isWater[i][j] == 1) {
                    q.offer(new int[]{i, j, 0});
                    isWater[i][j] = 2;
                }
            }
        }
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] poll = q.poll();
                int h = poll[2];
                for (int k = 0; k < 4; k++) {
                    int nR = poll[0] + directions[k][0];
                    int nC = poll[1] + directions[k][1];
                    if (nR >= 0 && nR < n && nC >= 0 && nC < m && isWater[nR][nC] != 2) {
                        res[nR][nC] = h + 1;
                        isWater[nR][nC] = 2;
                        q.offer(new int[]{nR, nC, h + 1});
                    }
                }
            }
        }
        return res;
    }

    public int firstCompleteIndex(int[] arr, int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] row = new int[m];
        int[] col = new int[n];

        List<int[]> mark = new ArrayList<>(m * n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mark.add(new int[]{0, 0});
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mark.get(mat[i][j] - 1)[0] = i;
                mark.get(mat[i][j] - 1)[1] = j;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            int val = arr[i] - 1;
            row[mark.get(val)[0]]++;
            col[mark.get(val)[1]]++;
            if (row[mark.get(val)[0]] == n || col[mark.get(val)[1]] == m) {
                return i;
            }
        }
        return -1;
    }

    public int countServers(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0;
        int[] row = new int[m];
        int[] col = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    row[i]++;
                    col[j]++;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && (row[i] + col[j]) > 2) {
                    res++;
                }
            }
        }
        return res;
    }

    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        Map<Integer, List<Integer>> in = new HashMap<>(n);
        List<Set<Integer>> out = new ArrayList<>(n);
        Queue<Integer> terminalNodes = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (graph[i].length == 0) {
                terminalNodes.offer(i);
                continue;
            }
            out.add(new HashSet<>());
            for (int j = 0; j < graph[i].length; j++) {
                in.computeIfAbsent(graph[i][j], k -> new ArrayList<>()).add(i);
                out.get(i).add(graph[i][j]);
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!terminalNodes.isEmpty()) {
            Integer poll = terminalNodes.poll();
            res.add(poll);
            List<Integer> inNodes = in.get(poll);
            if (inNodes == null) continue;
            for (int inNode : inNodes) {
                out.get(inNode).remove(poll);
                if (out.get(inNode).isEmpty()) {
                    terminalNodes.offer(inNode);
                }
            }
        }
        return res.stream().sorted().collect(Collectors.toList());
    }

    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        List<int[]> valueIndexes = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            valueIndexes.add(new int[]{nums[i], i});
        }
        valueIndexes.sort(Comparator.comparingInt(a -> a[0]));
        List<List<int[]>> subCons = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i == 0 || valueIndexes.get(i)[0] - valueIndexes.get(i - 1)[0] > limit) {
                subCons.add(new ArrayList<>());
            }
            subCons.getLast().add(valueIndexes.get(i));
        }
        for (int i = 0; i < subCons.size(); i++) {
            List<int[]> sortedSub = new ArrayList<>(subCons.get(i));
            sortedSub.sort(Comparator.comparingInt(a -> a[1]));
            for (int j = 0; j < sortedSub.size(); j++) {
                nums[sortedSub.get(j)[1]] = subCons.get(i).get(j)[0];
            }
        }
        return nums;
    }

    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] in = new int[n];
        for (int i = 0; i < n; i++) {
            in[favorite[i]]++;
        }
        int[] maxDepth = new int[n];
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (in[i] == 0) {
                q.add(i);
            }
        }
        while (!q.isEmpty()) {
            int i = q.poll();
            int like = favorite[i];
            in[like]--;
            maxDepth[like] = Math.max(maxDepth[like], 1 + maxDepth[i]);
            if (in[like] == 0) {
                q.add(like);
            }
        }

        int maxC3 = 0;
        int totalC2 = 0;
        int[] visited = new int[n];
        for (int i = 0; i < n; i++) {
            if (in[i] != 0 && visited[i] == 0) {
                in[i]--;
                int like = i;
                int len = 0;
                while (visited[like] == 0) {
                    visited[like] = 1;
                    like = favorite[like];
                    len++;
                }
                if (len == 2) {
                    totalC2 += 2 + maxDepth[like] + maxDepth[favorite[like]];
                } else if (len >= 3) {
                    maxC3 = Math.max(maxC3, len);
                }
            }
        }
        return Math.max(totalC2, maxC3);
    }

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        int[] in = new int[numCourses];
        Map<Integer, List<Integer>> preAfters = new HashMap<>();
        for (int[] pre : prerequisites) {
            in[pre[1]]++;
            preAfters.computeIfAbsent(pre[0], k -> new ArrayList<>()).add(pre[1]);
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (in[i] == 0) {
                q.add(i);
            }
        }
        Map<Integer, Set<Integer>> afterPres = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            afterPres.put(i, new HashSet<>());
        }
        while (!q.isEmpty()) {
            int pre = q.poll();
            List<Integer> afters = preAfters.get(pre);
            if (afters == null) {
                continue;
            }
            Set<Integer> pathPre = afterPres.get(pre);
            for (int after : afters) {
                in[after]--;
                afterPres.get(after).addAll(pathPre);
                afterPres.get(after).add(pre);
                if (in[after] == 0) {
                    q.offer(after);
                }
            }
        }
        List<Boolean> res = new ArrayList<>(queries.length);
        for (int i = 0; i < queries.length; i++) {
            Set<Integer> pres = afterPres.getOrDefault(queries[i][1], new HashSet<>());
            res.add(pres.contains(queries[i][0]));
        }
        return res;
    }

    int[][] dirFish = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int findMaxFish(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int maxMasterFish = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                Queue<int[]> q = new LinkedList<>();
                q.offer(new int[]{i, j});
                int curFish = grid[i][j];
                while (!q.isEmpty()) {
                    int size = q.size();
                    for (int k = 0; k < size; k++) {
                        int[] poll = q.poll();
                        grid[poll[0]][poll[1]] = 0;
                        for (int[] dir : dirFish) {
                            int di = poll[0] + dir[0];
                            int dj = poll[1] + dir[1];
                            if (di >= 0 && di < m && dj >= 0 && dj < n && grid[di][dj] != 0) {
                                q.offer(new int[]{di, dj});
                                curFish += grid[di][dj];
                                grid[di][dj] = 0;
                            }
                        }

                    }
                }
                maxMasterFish = Math.max(maxMasterFish, curFish);
            }
        }
        return maxMasterFish;
    }

    private int dfsFish(int[][] grid, int i, int j, int m, int n) {
        int curFish = grid[i][j];
        grid[i][j] = 0;
        for (int[] dir : dirFish) {
            int dI = i + dir[0];
            int dJ = j + dir[1];
            if (dI < 0 || dI >= m || dJ < 0 || dJ >= n || grid[dI][dJ] == 0) continue;
            curFish += dfsFish(grid, dI, dJ, m, n);
        }
        return curFish;
    }

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int[] e : edges) {
            int aRoot = find(e[0], parent);
            int bRoot = find(e[1], parent);
            if (aRoot == bRoot) {
                return new int[]{e[0], e[1]};
            }
            if (rank[aRoot] == rank[bRoot]) {
                rank[aRoot]++;
                parent[bRoot] = aRoot;
            } else if (rank[aRoot] > rank[bRoot]) {
                parent[bRoot] = aRoot;
            } else {
                parent[aRoot] = bRoot;
            }
        }
        return null;
    }

    private int find(int a, int[] parent) {
        while (a != parent[a]) {
            a = parent[a];
        }
        return a;
    }

    static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }
        }

        public int find(int a) {
            while (a != parent[a]) {
                a = parent[a];
            }
            return a;
        }

        public boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) {
                return false;
            }
            if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }
            return true;
        }
    }

    public int magnificentSets(int n, int[][] edges) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        UnionFind uf = new UnionFind(n);

        for (int[] e : edges) {
            adj.computeIfAbsent(e[0], k -> new ArrayList<>()).add(e[1]);
            adj.computeIfAbsent(e[1], k -> new ArrayList<>()).add(e[0]);
            uf.union(e[0], e[1]);
        }

        int[] maxGroup = new int[n + 1];
        int[] visited = new int[n + 1];
        Queue<int[]> q = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            q.offer(new int[]{i, 1});
            Arrays.fill(visited, -1);
            visited[i] = 1;
            int group = 1;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int j = 0; j < size; j++) {
                    int[] cur = q.poll();
                    int nextLevel = cur[1] + 1;
                    for (int next : adj.getOrDefault(cur[0], new ArrayList<>())) {
                        if (visited[next] == -1) {
                            visited[next] = nextLevel;
                            group = nextLevel;
                            q.offer(new int[]{next, nextLevel});
                        } else if (visited[next] == cur[1]) {
                            return -1;
                        }
                    }
                }
            }
            int parentI = uf.find(i);
            maxGroup[parentI] = Math.max(maxGroup[parentI], group);
        }
        return Arrays.stream(maxGroup).sum();
    }

    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int maxLen = 0;
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        Map<Integer, Integer> indexIslandSize = new HashMap<>();

        int indexIsland = 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    Queue<int[]> q = new LinkedList<>();
                    q.offer(new int[]{i, j});
                    grid[i][j] = indexIsland;
                    int curLen = 1;
                    while (!q.isEmpty()) {
                        int[] poll = q.poll();
                        for (int[] dir : directions) {
                            int ni = poll[0] + dir[0];
                            int nj = poll[1] + dir[1];
                            if (ni >= 0 && ni < n && nj >= 0 && nj < n && grid[ni][nj] == 1) {
                                q.offer(new int[]{ni, nj});
                                curLen++;
                                grid[ni][nj] = indexIsland;
                            }
                        }
                    }
                    indexIslandSize.put(indexIsland, curLen);
                    maxLen = Math.max(maxLen, curLen);
                    indexIsland++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Set<Integer> indexIslandJoin = new HashSet<>();
                if (grid[i][j] == 0) {
                    int curLen = 1;
                    for (int[] dir : directions) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];
                        if (ni >= 0 && ni < n && nj >= 0 && nj < n && grid[ni][nj] !=0) {
                            if (indexIslandJoin.add(grid[ni][nj])) {
                                curLen += indexIslandSize.get(grid[ni][nj]);
                            }
                        }
                    }
                    maxLen = Math.max(maxLen, curLen);
                }
            }
        }
        return maxLen;
    }


    public static void main(String[] args) {
        Daily daily = new Daily();
        System.out.println(daily.largestIsland(new int[][] {{0,0,0,0,0,0,0},{0,1,1,1,1,0,0},{0,1,0,0,1,0,0},{1,0,1,0,1,0,0},{0,1,0,0,1,0,0},{0,1,0,0,1,0,0},{0,1,1,1,1,0,0}}));
        System.exit(0);
    }
}
