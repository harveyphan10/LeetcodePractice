package handbook.basic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dungptm2
 */
public class RecursionTechnique {

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<String>();
        recurse(res, 0, 0, "", n);
        return res;
    }

    private void recurse(List<String> res, int l, int r, String s, int n) {
        if (s.length() == n * 2) {
            res.add(s);
            return;
        }
        if (l < n) {
            recurse(res, l + 1, r, s + "(", n);
        }
        if (r < l) {
            recurse(res, l, r + 1, s + ")", n);
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> combine = new ArrayList<>(k);
        recurseCombine(res, combine, 1, n, k);
        return res;
    }

    private void recurseCombine(List<List<Integer>> res, List<Integer> combine, int i, int n, int k) {
        if (combine.size() == k) {
            res.add(new ArrayList<>(combine));
            return;
        }
        for (int inx = i; inx <= n; ++inx) {
            combine.add(inx);
            recurseCombine(res, combine, inx + 1, n, k);
            combine.removeLast();
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        List<Integer> subset = new ArrayList<>();
        res.add(new ArrayList<>(subset));
        recurseSubset(res, subset, 0, nums, n);
        return res;
    }

    private void recurseSubset(List<List<Integer>> res, List<Integer> subset, int inx, int[] nums, int n) {
        if (inx >= n) {
            return;
        }
        for (int i = inx; i < n; ++i) {
            subset.add(nums[i]);
            res.add(new ArrayList<>(subset));
            recurseSubset(res, subset, i + 1, nums, n);
            subset.removeLast();
        }
    }

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) {
            return res;
        }
        Map<Character, List<Character>> map = new HashMap<>();
        map.put('2', Arrays.asList('a', 'b', 'c'));
        map.put('3', Arrays.asList('d', 'e', 'f'));
        map.put('4', Arrays.asList('g', 'h', 'i'));
        map.put('5', Arrays.asList('j', 'k', 'l'));
        map.put('6', Arrays.asList('m', 'n', 'o'));
        map.put('7', Arrays.asList('p', 'q', 'r', 's'));
        map.put('8', Arrays.asList('t', 'u', 'v'));
        map.put('9', Arrays.asList('w', 'x', 'y', 'z'));

        List<Character> strCombine = new ArrayList<>(digits.length());
        recurseLetter(map, res, strCombine, digits, 0);
        return res;
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> subset = new ArrayList<>();
        res.add(new ArrayList<>(subset));
        recurseSubsetWithDup(res, subset, 0, nums);
        return res;
    }

    private void recurseSubsetWithDup(List<List<Integer>> res, List<Integer> subset, int index, int[] nums) {
       if (index >= nums.length) {
           return;
       }
       for (int i = index; i < nums.length; ++i) {
           if (i > index && nums[i] == nums[i-1]) {
              continue;
           }
           subset.add(nums[i]);
           res.add(new ArrayList<>(subset));
           recurseSubsetWithDup(res, subset, i+ 1, nums);
           subset.removeLast();
       }
    }

    private void recurseLetter(Map<Character, List<Character>> map, List<String> res, List<Character> strCombine, String digits, int iDigit) {
        if (strCombine.size() == digits.length()) {
            res.add(strCombine.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining()));
            return;
        }
        for (int i = 0; i < map.get(digits.charAt(iDigit)).size(); ++i) {
            strCombine.add(map.get(digits.charAt(iDigit)).get(i));
            recurseLetter(map, res, strCombine, digits, iDigit + 1);
            strCombine.removeLast();
        }
    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> subPermute = new ArrayList<>();
        recursePermute(res, subPermute, nums);
        return res;
    }

    private void recursePermute(List<List<Integer>> res, List<Integer> subPermute, int[] nums) {
       if (subPermute.size() == nums.length) {
           res.add(new ArrayList<>(subPermute));
           return;
       }
       for (int i = 0; i < nums.length; ++i) {
           if (subPermute.contains(nums[i])) {
               continue;
           }
           subPermute.add(nums[i]);
           recursePermute(res, subPermute, nums);
           subPermute.removeLast();
       }
    }

    public static void main(String[] args) {
        RecursionTechnique rt = new RecursionTechnique();
        System.out.println(rt.permute(new int[]{1, 2, 3}));
        System.exit(0);
    }
}
