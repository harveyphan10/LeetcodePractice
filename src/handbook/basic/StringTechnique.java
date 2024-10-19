package handbook.basic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dungptm2
 */
public class StringTechnique {

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a' - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String s) {
        int n = s.length();
        int l = 0, r = n - 1;
        while (l < r) {
            if (!Character.isDigit(s.charAt(l) - 'A') && !Character.isLetter(s.charAt(l) - 'A')) {
                l++;
            } else if (!Character.isDigit(s.charAt(r) - 'A') && !Character.isLetter(s.charAt(r) - 'A')) {
                r--;
            } else if (Character.toLowerCase(s.charAt(l) - 'A') != Character.toLowerCase(s.charAt(r) - 'A')) {
                return false;
            } else {
                l++;
                r--;
            }
        }
        return true;
    }

    public int characterReplacement(String s, int k) {
        int n = s.length();
        int maxLen = 0;
        int[] map = new int[26];
        int l = 0, r = l;
        int use = 0;
        int maxFreChar = 0;

        while (r < n) {
            map[s.charAt(r) - 'A']++;
            maxFreChar = Math.max(maxFreChar, map[s.charAt(r) - 'A']);
            use = r - l + 1 - maxFreChar;

            if (use > k) {
                maxLen = Math.max(maxLen, r - l);
                map[s.charAt(l) - 'A']--;
                l++;
            }
            r++;
        }
        return Math.max(maxLen, r - l);
    }

    public List<Integer> findAnagrams(String s, String p) {
        int n = s.length();
        int m = p.length();
        if (n < m) {
            return Collections.emptyList();
        }
        List<Integer> res = new ArrayList<>();
        int[] mapP = new int[26];
        int[] mapS = new int[26];

        for (int i = 0; i < m; i++) {
            mapP[p.charAt(i) - 'a']++;
            mapS[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i + m - 1 < n; i++) {
            if (i != 0) {
                mapS[s.charAt(i - 1) - 'a']--;
                mapS[s.charAt(i + m - 1) - 'a']++;
            }
            if (checkMap(mapP, mapS)) {
                res.add(i);
            }
        }
        return res;
    }

    private boolean checkMap(int[] mapP, int[] mapS) {
        for (int i = 0; i < mapP.length; i++) {
            if (mapP[i] != mapS[i]) {
                return false;
            }
        }
        return true;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
//        List<List<String>> res = new ArrayList<>();
        int n = strs.length;

        Map<String, List<String>> mapSort = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String sorted = strs[i].chars()
                    .sorted()
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            mapSort.computeIfAbsent(sorted, k -> new ArrayList<>()).add(strs[i]);
        }
        return new ArrayList<>(mapSort.values());
    }

    private boolean checkMap(Map<Character, Integer> mapP, Map<Character, Integer> mapS) {
        for (Map.Entry<Character, Integer> entry : mapP.entrySet()) {
            if (entry.getValue().intValue() != mapS.getOrDefault(entry.getKey(), 0).intValue()) {
                return false;
            }
        }
        return true;
    }


    public String longestPalindrome(String s) {
        int n = s.length();
        int max = 0, start = 0, end = 0;
        boolean[][] A = new boolean[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            A[i][i] = true;
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i <= 2 || A[i + 1][j - 1]) {
                        A[i][j] = true;
                        if (j - i + 1 > max) {
                            max = j - i + 1;
                            start = i;
                            end = j;
                        }
                    }
                }
            }
        }
        return s.substring(start, end + 1);
    }

    public static void main(String[] args) {

        StringTechnique st = new StringTechnique();
        System.out.println(st.longestPalindrome("ababababa"));
        System.exit(0);
    }
}
