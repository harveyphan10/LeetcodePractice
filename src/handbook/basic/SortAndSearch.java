package handbook.basic;

/**
 * @author dungptm2
 */
public class SortAndSearch {
    public int search(int[] nums, int target) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (target < nums[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    public int kthSmallest(int[][] m, int k) {
        int n = m.length;
        int l = m[0][0], r = m[n - 1][n - 1];
        int minMid = m[n - 1][n - 1];
        while (l <= r) {
            int mid = (l + r) / 2;
            int count = countElemLE(mid, m);
            if (count < k) {
                l = mid + 1;
            } else if (count >= k) {
                minMid = Math.min(minMid, mid);
                r = mid - 1;
            }
        }
        return minMid;
    }

    private int countElemLE(int mid, int[][] m) {
        int n = m.length;
        int col = n - 1;
        int count = 0;
        for (int row = 0; row < n; row++) {
            while (col >= 0 && m[row][col] > mid) {
                col--;
            }
            count += col + 1;
        }
        return count;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int targetRow = findRowTarget(matrix, target);
        if (targetRow == -1) {
            return false;
        }
        return binarySearch(matrix[targetRow], target);
    }

    private boolean binarySearch(int[] a, int target) {
        int n = a.length;
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (a[mid] == target) {
                return true;
            } else if (target < a[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return false;
    }

    private int findRowTarget(int[][] m, int target) {
        int n = m[0].length - 1;
        int l = 0, r = m.length - 1;
        int targetRow = -1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (m[mid][0] <= target && target <= m[mid][n]) {
                targetRow = mid;
                break;
            } else if (target < m[mid][n]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return targetRow;
    }

    public int findKthLargest(int[] nums, int k) {
        int n = nums.length - 1;
        for (int i = n / 2; i >= 0; i--) {
            heapify(nums, i, n);
        }
        int count = 0;
        while (count < k) {
            swapElem(nums, 0, n - count);
            heapify(nums, 0, n - count - 1);
            count++;
        }
        return nums[n - k + 1];
    }

    private void heapify(int[] nums, int i, int n) {
        if (2 * i + 1 > n) {
            return;
        }
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int max = r > n ? nums[l] : Math.max(nums[l], nums[r]);
        if (nums[i] < max && max == nums[l]) {
            swapElem(nums, i, l);
            heapify(nums, l, n);
        } else if (nums[i] < max && max == nums[r]) {
            swapElem(nums, i, r);
            heapify(nums, r, n);
        }
    }

    public int findKthLargest2(int[] nums, int k) {
        int n = nums.length;
        int l = 0, r = n - 1;
        return quickSelect(nums, l, r, k);
    }

    private int quickSelect(int[] nums, int l, int r, int k) {
        if (l <= r) {
            return nums[l];
        }
        int pIndex = l + ((int) (Math.random() * (r - l + 1)));
        pIndex = partition(nums, l, r, pIndex);
        if (pIndex == k) {
            return nums[k];
        } else if (k < pIndex) {
            return quickSelect(nums, l, pIndex - 1, k);
        } else {
            return quickSelect(nums, pIndex + 1, r, k);
        }
    }

    private int partition(int[] nums, int l, int r, int pIndex) {
        int pivot = nums[pIndex];
        swapElem(nums, pIndex, r);
        pIndex = l;
        for (int i = l + 1; i <= r; i++) {
            if (nums[i] <= pivot) {
                swapElem(nums, pIndex, i);
                pIndex++;
            }
        }
        return pIndex - 1;
    }

    private void swapElem(int[] nums, int i, int l) {
        int temp = nums[i];
        nums[i] = nums[l];
        nums[l] = temp;
    }

    public int findMin(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        int min = Integer.MAX_VALUE;
        return recurseFindMin(nums, l, r, min);
    }

    private int recurseFindMin(int[] nums, int l, int r, int min) {
        if (l >= r) {
            return min;
        }
        int mid = (l + r) / 2;
        min = Math.min(Math.min(Math.min(min, nums[l]), nums[r]), nums[mid]);

        if (nums[l] < nums[mid]) {
            return recurseFindMin(nums, mid + 1, r, min);
        } else {
            return recurseFindMin(nums, l, mid - 1, min);
        }
    }

    public static void main(String[] args) {
        SortAndSearch s = new SortAndSearch();
        System.out.println(s.findMin(new int[]{11,13,15,17}));
        System.exit(0);
    }
}
