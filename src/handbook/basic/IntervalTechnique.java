package handbook.basic;

import java.util.*;

/**
 * @author dungptm2
 */
public class IntervalTechnique {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int[][] res = new int[intervals.length + 1][2];
        int[] temp = newInterval;
        for (int i = 0; i < intervals.length; i++) {
            if (temp[0] < intervals[i][0]) {
                res[i] = temp;
                temp = intervals[i];
            } else {
                res[i] = intervals[i];
            }
        }
        res[intervals.length] = temp;
        int[] pre = res[0];
        int k = 0;
        for (int i = 1; i < res.length; i++) {
            if (isOverlap(res[i], pre)) {
                int[] merge = mergeOverlap(res[i], pre);
                res[k] = merge;
                pre = merge;
            } else {
                res[++k] = res[i];
                pre = res[i];
            }
        }
        return Arrays.copyOf(res, k + 1);
    }

    private int[] mergeOverlap(int[] a, int[] b) {
        return new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
    }

    private boolean isOverlap(int[] a, int[] b) {
        return a[1] > b[0] && a[0] < b[1];
    }

    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int count = 1;
        int preEnd = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= preEnd) {
                count++;
                preEnd = intervals[i][1];
            }
        }
        return intervals.length - count;
    }


    public static void main(String[] args) {
        IntervalTechnique it = new IntervalTechnique();
        System.out.println(it.eraseOverlapIntervals(new int[][]{{-39, 21}, {-38, 5}, {-67, 67}, {23, 37}, {83, 95}, {-71, -19}, {-19, 64}, {4, 31}, {37, 96}, {30, 57}, {-19, 12}, {53, 75}, {-54, 83}, {-32, 38}, {-18, 16}, {-60, 9}, {92, 93}, {-12, 20}, {-37, 35}, {19, 36}, {46, 56}, {45, 52}, {-67, -29}, {30, 67}, {67, 79}, {52, 98}, {59, 60}, {-63, 7}, {7, 20}, {16, 59}, {83, 96}, {-59, -3}, {33, 41}, {-67, -49}, {-15, 67}}));
        System.exit(0);
    }
}
