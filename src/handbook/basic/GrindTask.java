package handbook.basic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dungptm2
 */
public class GrindTask {
    private boolean isBadVersion(int n) {
        return n == 1702766719;
    }

    public int firstBadVersion(int n) {
        int l = 1, r = n;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            boolean badVersion = isBadVersion(mid);
            if (badVersion) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public int longestPalindrome(String s) {
        int count = 0;
        boolean takeOdd = false;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        for (Map.Entry<Character, Integer> e : map.entrySet()) {
            Integer v = e.getValue();
            if (v % 2 == 0) {
                count += v;
            } else {
                count += v - 1;
                takeOdd = true;
            }
        }
        return takeOdd ? count + 1 : count;
    }

    public int majorityElement(int[] nums) {
        int count = 0;
        int major = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == major) {
                count++;
            } else {
                count--;
            }
            if (count < 0) {
                major = nums[i];
                count = 0;
            }
        }
        return major;
    }

    public String addBinary(String a, String b) {
        char[] result = new char[Math.max(a.length(), b.length()) + 1];
        char[] aArr = a.toCharArray();
        char[] bArr = b.toCharArray();
        int plus = 0;
        int rA = aArr.length - 1;
        int rB = bArr.length - 1;
        int i = result.length - 1;
        while (rA >= 0 || rB >= 0 || plus > 0) {
            int sum = (rA >= 0 && rB >= 0) ? aArr[rA] - '0' + (bArr[rB] - '0') + plus :
                    rA >= 0 ? aArr[rA] - '0' + plus :
                            rB >= 0 ? bArr[rB] - '0' + plus : plus;
            result[i--] = Character.forDigit(sum % 2, 10);
            plus = sum / 2;
            rA--;
            rB--;
        }

        return new String(result).substring(i + 1);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int n = candidates.length;
        int[][] A = new int[n + 1][target + 1];
        List<List<Integer>>[][] pp = new List[n + 1][target + 1];
        for (int i = 0; i <= n; i++) {
            for (int t = 0; t <= target; t++) {
                pp[i][t] = new ArrayList<>();
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int t = 1; t <= target; t++) {
                if (t % candidates[i] == 0) {
                    A[i][t] = 1;
                    List<Integer> combine = new ArrayList<>(t / candidates[i]);
                    for (int k = 1; k <= t / candidates[i]; k++) {
                        combine.add(candidates[i]);
                    }
                    pp[i][t].add(combine);
                }
                for (int k = 0; k <= t / candidates[i]; k++) {
                    List<Integer> combine = new ArrayList<>();
                    if (A[i + 1][t - k * candidates[i]] != 0) {
                        for (int j = 1; j <= k; j++) {
                            combine.add(candidates[i]);
                        }
                        List<List<Integer>> subCombine = pp[i + 1][t - k * candidates[i]];
                        for (List<Integer> comb : subCombine) {
                            List<Integer> cloneParent = new ArrayList<>(comb);
                            cloneParent.addAll(combine);
                            pp[i][t].add(cloneParent);
                        }
                        A[i][t] += A[i + 1][t - k * candidates[i]];
                    }
                }
            }
        }
        return pp[0][target];
    }

    public static class Pair2 {
        int i;
        int j;

        public Pair2(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static class Pair {
        int i;
        int j;
        int curTime;

        public Pair(int i, int j, int curTime) {
            this.i = i;
            this.j = j;
            this.curTime = curTime;
        }
    }

    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        int[][] A = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(A[i], Integer.MAX_VALUE);
        }
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        PriorityQueue<Pair> q = new PriorityQueue<>(Comparator.comparingInt(o -> o.curTime));
        q.offer(new Pair(0, 0, 0));

        while (!q.isEmpty()) {
            Pair poll = q.poll();
            for (int[] d : directions) {
                int dI = poll.i + d[0];
                int dJ = poll.j + d[1];
                if (dI >= 0 && dI < n && dJ >= 0 && dJ < m) {
                    int wait = Math.max(moveTime[dI][dJ] - poll.curTime, 0);
                    int newPathTime = poll.curTime + wait + 1;
                    if (A[dI][dJ] > newPathTime) {
                        A[dI][dJ] = newPathTime;
                        q.offer(new Pair(dI, dJ, newPathTime));
                    }
                }
            }
        }
        return A[n - 1][m - 1];
    }

    static class TimeValue {
        String v;
        int ts;

        public TimeValue(String v, int ts) {
            this.v = v;
            this.ts = ts;
        }
    }

    public static class TimeMap {
        private Map<String, List<TimeValue>> keyValue;

        public TimeMap() {
            keyValue = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            keyValue.computeIfAbsent(key, k -> new ArrayList<>()).add(new TimeValue(value, timestamp));
        }

        public String get(String key, int timestamp) {
            List<TimeValue> timestamps = keyValue.get(key);
            if (timestamps == null) {
                return "";
            }

            int l = 0, r = timestamps.size() - 1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (timestamps.get(mid).ts == timestamp) {
                    l = mid;
                    break;
                } else if (timestamps.get(mid).ts < timestamp) {
                    l = mid + 1;
                } else if (timestamps.get(mid).ts > timestamp) {
                    r = mid - 1;
                }
            }
            int findIndex = Math.min(l, r);
            if (findIndex < 0 || findIndex >= timestamps.size()) {
                return "";
            }
            return timestamps.get(findIndex).v;
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Collection<Integer>> mailIndexes = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                mailIndexes.computeIfAbsent(accounts.get(i).get(j), k -> new ArrayList<>()).add(i);
            }
        }

        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, Collection<Integer>> entry : mailIndexes.entrySet()) {
            if (entry.getValue().size() > 1) {
                String name = "";
                Set<String> set = new HashSet<>();
                for (int acc : entry.getValue()) {
                    List<String> account = accounts.get(acc);
                    if (account == null) {
                        continue;
                    }
                    name = account.getFirst();
                    set.addAll(account.subList(1, account.size()));
                    accounts.set(acc, null);
                }
                ArrayList<String> mails = new ArrayList<>(set);
                mails.sort(String::compareTo);
                mails.addFirst(name);
                result.add(mails);
            }
        }

        for (List<String> acc : accounts) {
            if (acc != null) {
                ArrayList<String> strings = new ArrayList<>(acc.subList(1, acc.size()));
                strings.sort(String::compareTo);
                strings.addFirst(acc.getFirst());
                result.add(strings);
            }
        }
        return result;
    }

    public int myAtoi(String s) {
        byte sign = 0;
        Queue<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c == '-' || c == '+') && stack.isEmpty() && (i == 0 || s.charAt(i - 1) == ' ')) {
                sign = (byte) (c == '-' ? 1 : 2);
            } else if (c == ' ' && stack.isEmpty() && sign == 0 && (i == 0 || s.charAt(i - 1) == ' ')) {

            } else if (c == '0' && stack.isEmpty()) {

            } else if (c >= '0' && c <= '9') {
                stack.offer(c);
            } else {
                break;
            }
        }
        if (stack.isEmpty()) {
            return 0;
        }
        long result = stack.poll() - '0';
        while (!stack.isEmpty()) {
            result = result * 10 + stack.poll() - '0';
            if (result > Integer.MAX_VALUE) {
                break;
            }
        }
        return result > Integer.MAX_VALUE ? sign == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE :
                sign == 1 ? (int) -result : (int) result;
    }

    int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private boolean recursedExist(char[][] board, int[][] visited, int i, int j, int indexWord, String word) {
        if (indexWord == word.length()) {
            return true;
        }
        int n = board.length;
        int m = board[0].length;
        visited[i][j] = 1;
        for (int[] direction : directions) {
            int dI = i + direction[0];
            int dJ = j + direction[1];
            if (dI >= 0 && dI < n && dJ >= 0 && dJ < m && visited[dI][dJ] == 0) {
                if (board[dI][dJ] == word.charAt(indexWord)) {
                    boolean exist = recursedExist(board, visited, dI, dJ, indexWord + 1, word);
                    if (exist) {
                        return true;
                    }
                    visited[dI][dJ] = 0;
                }
            }
        }
        return false;
    }

    public boolean exist(char[][] board, String word) {
        int n = board.length;
        int m = board[0].length;
        int[][] visited = new int[n][m];
        if (word.length() > n * m) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean exist = recursedExist(board, visited, i, j, 1, word);
                    if (exist) {
                        return true;
                    }
                    visited[i][j] = 0;
                }
            }
        }
        return false;
    }

    public int leastInterval(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (char c : tasks) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        PriorityQueue<Map.Entry<Character, Integer>> q1 = new PriorityQueue<>(map.size(),
                (o1, o2) -> o2.getValue() - o1.getValue());
        Collection<Map.Entry<Character, Integer>> qTemp = new ArrayList<>();

        map.entrySet().forEach(q1::add);
        int curIndex = 0;
        while (!q1.isEmpty()) {
            int k = n + 1;
            while (k >= 0 && !q1.isEmpty()) {
                Map.Entry<Character, Integer> chooseEntry = q1.poll();
                k--;
                chooseEntry.setValue(chooseEntry.getValue() - 1);
                if (chooseEntry.getValue() > 0) {
                    qTemp.add(chooseEntry);
                }
                curIndex++;
            }
            if (!qTemp.isEmpty()) {
                curIndex += k;
                q1.addAll(qTemp);
                qTemp.clear();
            }
        }
        return curIndex;
    }

    public int trap(int[] height) {
        int n = height.length;
        int count = 0;
        int maxLeft = 0, maxRight = 0, iMaxRight = 0;
        for (int i = 1; i < n; i++) {
            if (height[i] >= maxRight) {
                maxRight = height[i];
                iMaxRight = i;
            }
        }
        for (int i = 0; i < n; i++) {
            maxLeft = Math.max(maxLeft, height[i]);
            if (i > iMaxRight) {
                maxRight = 0;
                for (int j = iMaxRight + 1; j < n; j++) {
                    if (height[j] >= maxRight) {
                        maxRight = height[j];
                        iMaxRight = j;
                    }
                }
            }
            count += Math.max(0, Math.min(maxLeft, maxRight) - height[i]);
        }
        return count;
    }

    public int trap2(int[] height) {
        int n = height.length;
        int l = 0, r = n - 1;
        int maxLeft = height[l], maxRight = height[r];
        int count = 0;
        while (l <= r) {
            if (maxLeft <= maxRight) {
                if (height[l] > maxLeft) {
                    maxLeft = height[l];
                } else {
                    count += maxLeft - height[l];
                }
                l++;
            } else {
                if (height[r] > maxRight) {
                    maxRight = height[r];
                } else {
                    count += maxRight - height[r];
                }
                r--;
            }
        }
        return count;
    }

    public static class MedianFinder {
        int n;
        PriorityQueue<Integer> qLeft = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> qRight = new PriorityQueue<>();

        public MedianFinder() {
            n = 0;
        }

        public void addNum(int num) {
            if (n == 0) {
                qLeft.add(num);
            } else {
                int sL = qLeft.size();
                int sR = qRight.size();
                if (sL == sR) {
                    int maxL = qLeft.peek();
                    if (maxL >= num) {
                        qLeft.poll();
                        qLeft.add(num);
                        qRight.add(maxL);
                    } else {
                        qRight.add(num);
                    }
                } else if (sL < sR) {
                    int minR = qRight.peek();
                    if (minR <= num) {
                        qRight.poll();
                        qRight.add(num);
                        qLeft.add(minR);
                    } else {
                        qLeft.add(num);
                    }
                } else if (sL > sR) {
                    int maxL = qLeft.peek();
                    if (maxL >= num) {
                        qLeft.poll();
                        qLeft.add(num);
                        qRight.add(maxL);
                    } else {
                        qRight.add(num);
                    }
                }
            }
            n++;
        }

        public double findMedian() {
            if (n == 0) {
                return 0;
            } else if (n % 2 == 0) {
                return (double) (qLeft.peek() + qRight.peek()) / 2;
            } else {
                return qLeft.size() > qRight.size() ? qLeft.peek() : qRight.peek();
            }
        }
    }

    public static class ProfitTime {
        int start;
        int end;
        int profit;

        public ProfitTime(int start, int end, int profit) {
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        ProfitTime[] profitTime = new ProfitTime[n];
        for (int i = 0; i < n; i++) {
            profitTime[i] = new ProfitTime(startTime[i], endTime[i], profit[i]);
        }
        Arrays.sort(profitTime, Comparator.comparingInt(o -> o.start));
        int[] L = new int[n + 1];
        L[n] = 0;
        L[n - 1] = profitTime[n - 1].profit;
        for (int i = n - 2; i >= 0; i--) {
            int firstIndexStartGTEEndI = firstStartTake(profitTime[i].end, i + 1, profitTime);
            if (firstIndexStartGTEEndI == n) {
                L[i] = Math.max(L[i], profitTime[i].profit);
            } else {
                L[i] = Math.max(L[i], profitTime[i].profit + L[firstIndexStartGTEEndI]);
            }
            L[i] = Math.max(L[i], L[i + 1]);
        }
        return L[0];
    }

    private int firstStartTake(int end, int i, ProfitTime[] profitTimes) {
        int n = profitTimes.length;
        int l = i, r = n - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (end <= profitTimes[mid].start) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = -1;
        right[n - 1] = n;
        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;
            while (j < n && heights[j] >= heights[i]) {
                j = right[j];
            }
            right[i] = j;
        }
        for (int i = 1; i < n; i++) {
            int j = i - 1;
            while (j >= 0 && heights[j] >= heights[i]) {
                j = left[j];
            }
            left[i] = j;
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, heights[i] * (right[i] - left[i] - 1));
        }
        return max;
    }

    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] L = new boolean[n + 1][m + 1];
        L[0][0] = true;
        for (int j = 2; j <= m; j++) {
            L[0][j] = p.charAt(j - 1) == '*' && L[0][j - 2];
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                    L[i][j] = L[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    L[i][j] = L[i][j - 2];
                    if (!L[i][j] && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.')) {
                        L[i][j] = L[i - 1][j];
                    }
                }
            }
        }
        return L[n][m];
    }

    public boolean canChange(String start, String target) {
        int n = start.length();
        int i1 = 0;
        int i2 = 0;
        while (i1 < n && i2 < n) {
            while (i2 < n && target.charAt(i2) == '_') {
                i2++;
            }
            while (i1 < n && start.charAt(i1) == '_') {
                i1++;
            }
            if (i1 >= n && i2 >= n) {
                return true;
            }
            if (i1 >= n
                    || i2 >= n
                    || target.charAt(i2) != start.charAt(i1)
                    || (target.charAt(i2) == 'L' && i1 < i2)
                    || (target.charAt(i2) == 'R' && i1 > i2)) {
                return false;
            }
            i1++;
            i2++;
        }
        while (i1 < n && start.charAt(i1) == '_') {
            i1++;
        }
        while (i2 < n && target.charAt(i2) == '_') {
            i2++;
        }
        return i1 == n && i2 == n;
    }

    public int maxCount(int[] banned, int n, int maxSum) {
        int sum = 0;
        int count = 0;
        Set<Integer> setBanned = new HashSet<>(banned.length);
        for (int eBanned : banned) {
            setBanned.add(eBanned);
        }
        for (int i = 1; i <= n; i++) {
           if (sum + i > maxSum) {
               break;
           }
           if (!setBanned.contains(i)) {
               sum +=i;
               count++;
           }
        }
        return count;
    }

    public static void main(String[] args) {
        GrindTask task = new GrindTask();
        System.out.println(task.canChange("_L", "LL"));
        System.exit(0);

    }
}
