package handbook.basic;

import java.util.*;

/**
 * @author dungptm2
 */
public class GraphTechnique {
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int count = 0;
        Stack<Map.Entry<Integer, Integer>> stack = new Stack<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    stack.push(new AbstractMap.SimpleEntry<>(i, j));
                    grid[i][j] = '2';
                    while (!stack.isEmpty()) {
                        Map.Entry<Integer, Integer> pop = stack.pop();
                        int rI = pop.getKey(), rJ = pop.getValue();
                        for (int k = 0; k < 4; k++) {
                            int dI = rI + directions[k][0];
                            int dJ = rJ + directions[k][1];
                            if (dI >= 0 && dI < n && dJ >= 0 && dJ < m && grid[dI][dJ] == '1') {
                                grid[dI][dJ] = '2';
                                stack.push(new AbstractMap.SimpleEntry<>(dI, dJ));
                            }
                        }
                    }
                    count++;
                }
            }
        }
        return count;
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int n = image.length;
        int m = image[0].length;
        int[][] visited = new int[n][m];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Stack<Map.Entry<Integer, Integer>> stack = new Stack<>();
        if (image[sr][sc] == color) {
            return image;
        }
        int origColor = image[sr][sc];
        visited[sr][sc] = 1;
        image[sr][sc] = color;
        stack.push(new AbstractMap.SimpleEntry<>(sr, sc));
        while (!stack.isEmpty()) {
            Map.Entry<Integer, Integer> pop = stack.pop();
            int rI = pop.getKey(), rJ = pop.getValue();
            for (int i = 0; i < 4; i++) {
                int dI = rI + directions[i][0], dJ = rJ + directions[i][1];
                if (dI >= 0 && dI < n && dJ >= 0 && dJ < m && image[dI][dJ] == origColor && visited[dI][dJ] != '1') {
                    visited[dI][dJ] = 1;
                    stack.push(new AbstractMap.SimpleEntry<>(dI, dJ));
                    image[dI][dJ] = color;
                }
            }
        }
        return image;
    }

    static class Step {
        int i;
        int j;

        public Step(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        Queue<Step> q = new LinkedList<>();
        int[][] res = new int[m][n];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    res[i][j] = 0;
                    mat[i][j] = 2;
                    q.offer(new Step(i, j));
                }
            }
        }
        int step = 0;
        while (!q.isEmpty()) {
            step++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Step poll = q.poll();
                for (int k = 0; k < 4; k++) {
                    int di = poll.i + directions[k][0];
                    int dj = poll.j + directions[k][1];
                    if (di >= 0 && di < m && dj >= 0 && dj < n && mat[di][dj] != 2) {
                        q.offer(new Step(di, dj));
                        mat[di][dj] = 2;
                        res[di][dj] = step;
                    }
                }
            }
        }
        return res;
    }

    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int mins = -1;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<Step> q = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    q.offer(new Step(i, j));
                }
            }
        }
        while (!q.isEmpty()) {
            int size = q.size();
            mins++;
            for (int i = 0; i < size; i++) {
                Step poll = q.poll();
                for (int k = 0; k < 4; k++) {
                    int di = poll.i + directions[k][0];
                    int dj = poll.j + directions[k][1];
                    if (di >= 0 && di < m && dj >= 0 && dj < n && grid[di][dj] == 1) {
                        q.offer(new Step(di, dj));
                        grid[di][dj] = 2;
                    }
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }
        return mins;
    }

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        int[][] visited = new int[m][n];
        List<List<Integer>> res = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<Step> qP = new LinkedList<>();
        Queue<Step> qA = new LinkedList<>();

        for (int i = 0; i < m; i++) {
            qP.offer(new Step(i, 0));
            qA.offer(new Step(i, n - 1));
            visited[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            qP.offer(new Step(0, i));
            qA.offer(new Step(m - 1, i));
            visited[0][i] = 1;
        }
        while (!qP.isEmpty()) {
            int size = qP.size();
            for (int i = 0; i < size; i++) {
                Step poll = qP.poll();
                for (int k = 0; k < 4; k++) {
                    int di = poll.i + directions[k][0];
                    int dj = poll.j + directions[k][1];
                    if (di >= 0 && di < m && dj >= 0 && dj < n && visited[di][dj] == 0) {
                        if (heights[di][dj] >= heights[poll.i][poll.j]) {
                            visited[di][dj] = 1;
                            qP.offer(new Step(di, dj));
                        }
                    }
                }
            }
        }
        while (!qA.isEmpty()) {
            int size = qA.size();
            for (int i = 0; i < size; i++) {
                Step poll = qA.poll();
                visited[poll.i][poll.j]++;
                if (visited[poll.i][poll.j] == 2) {
                    res.add(Arrays.asList(poll.i, poll.j));
                }
                visited[poll.i][poll.j] = -1;
                for (int k = 0; k < 4; k++) {
                    int di = poll.i + directions[k][0];
                    int dj = poll.j + directions[k][1];
                    if (di >= 0 && di < m && dj >= 0 && dj < n && visited[di][dj] != -1) {
                        if (heights[di][dj] >= heights[poll.i][poll.j]) {
                            qA.offer(new Step(di, dj));
                        }
                    }
                }
            }
        }
        return res;
    }


    static class Node {
        int course;
        int in;
        Collection<Node> outs = new ArrayList<>();

        Node(int course) {
            this.course = course;
            this.in = 0;
        }
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int m = prerequisites.length;
        Map<Integer, Node> map = new HashMap<>();
        Queue<Node> q = new LinkedList<>();
        int completed = 0;
        for (int i = 0; i < m; i++) {
            Node node1 = map.computeIfAbsent(prerequisites[i][0], Node::new);
            Node node2 = map.computeIfAbsent(prerequisites[i][1], Node::new);
            node2.outs.add(node1);
            node1.in++;
        }
        for (Node node : map.values()) {
            if (node.in == 0) {
                q.offer(node);
                completed++;
            }
        }
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node poll = q.poll();
                for (Node outNode : poll.outs) {
                    outNode.in--;
                    if (outNode.in == 0) {
                        q.offer(outNode);
                        completed++;
                    }
                }
            }
        }
        return map.isEmpty() || completed == map.size();
    }

    static class Pair {
        int k;
        int v;

        public Pair(int k, int v) {
            this.k = k;
            this.v = v;
        }
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>((o1, o2) -> o2.v - o1.v);
        map.forEach((key, value) -> pq.add(new Pair(key, value)));
        int[] res = new int[k];
        int count = 0;
        while (count < k) {
            Pair poll = pq.poll();
            res[count] = poll.k;
            count++;
        }
        return res;
    }

    class NodeTrie {
        char c;
        int end;
        NodeTrie[] children;

        public NodeTrie(char c) {
            this.c = c;
            this.children = new NodeTrie[26];
            end = -1;
        }
    }

//    class Trie {
//        NodeTrie root;
//
//        public Trie() {
//            root = new NodeTrie('\0');
//        }
//
//        public void insert(String word) {
//            NodeTrie cur = root;
//            for (Character c : word.toCharArray()) {
//                NodeTrie child = cur.children[c - 'a'];
//                if (child == null) {
//                    child = new NodeTrie(c);
//                    cur.children[c - 'a'] = child;
//                }
//                cur = child;
//            }
//            cur.end = true;
//        }
//
//        public boolean search(String word) {
//            NodeTrie cur = root;
//            for (Character c : word.toCharArray()) {
//                cur = cur.children[c - 'a'];
//                if (cur == null) {
//                    return false;
//                }
//            }
//            return cur.end;
//        }
//
//        public boolean startsWith(String prefix) {
//            NodeTrie cur = root;
//            for (Character c : prefix.toCharArray()) {
//                cur = cur.children[c - 'a'];
//                if (cur == null) {
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        public boolean searchWordBreak(String s) {
//            Queue<Integer> qEnd = new LinkedList<>();
//            int[] visited = new int[s.length()];
//            qEnd.add(-1);
//            while (!qEnd.isEmpty()) {
//                int start = qEnd.poll() + 1;
//                NodeTrie cur = root;
//                for (int i = start; i < s.length(); i++) {
//                    cur = cur.children[s.charAt(i) - 'a'];
//                    if (cur == null) {
//                        break;
//                    }
//                    if (cur.end) {
//                        if (i == s.length() - 1) {
//                            return true;
//                        }
//                        if (visited[i] == 0) {
//                            qEnd.add(i);
//                            visited[i] = 1;
//                        }
//                    }
//                }
//            }
//            return false;
//        }
//    }

//    public boolean wordBreak(String s, List<String> wordDict) {
//        Trie trie = new Trie();
//        for (String word : wordDict) {
//            trie.insert(word);
//        }
//        return trie.searchWordBreak(s);
//    }

    //    public static class Node {
//        public int val;
//        public List<Node> neighbors;
//
//        public Node() {
//            val = 0;
//            neighbors = new ArrayList<Node>();
//        }
//
//        public Node(int _val) {
//            val = _val;
//            neighbors = new ArrayList<Node>();
//        }
//
//        public Node(int _val, ArrayList<Node> _neighbors) {
//            val = _val;
//            neighbors = _neighbors;
//        }
//    }
//
//    public Node cloneGraph(Node node) {
//        if (node == null) {
//            return node;
//        }
//        Queue<Node> q = new LinkedList<Node>();
//        Map<Integer, Node> map = new HashMap<Integer, Node>();
//        q.offer(node);
//        while (!q.isEmpty()) {
//            int size = q.size();
//            for (int i = 0; i < size; i++) {
//                Node poll = q.poll();
//                Node newNode = map.getOrDefault(poll.val, new Node(poll.val));
//                map.put(poll.val, newNode);
//
//                for (Node neighbor : poll.neighbors) {
//                    Node newNeighbor = map.getOrDefault(neighbor.val, new Node(neighbor.val));
//                    newNode.neighbors.add(newNeighbor);
//                    if (!map.containsKey(neighbor.val)) {
//                        map.put(neighbor.val, newNeighbor);
//                        q.offer(neighbor);
//                    }
//                }
//            }
//        }
//        return map.get(node.val);
//    }
   class TrieW {
        NodeTrie root;

        public TrieW() {
            root = new NodeTrie('\0');
        }

        public void insert(String word, int index) {
            NodeTrie cur = root;
            for (Character c : word.toCharArray()) {
                NodeTrie child = cur.children[c - 'a'];
                if (child == null) {
                    child = new NodeTrie(c);
                    cur.children[c - 'a'] = child;
                }
                cur = child;
            }
            cur.end = index;
        }

        public List<Integer> searchStar(String word) {
            List<Integer> res = new ArrayList<>();
            int len = word.length();
            for (int star = 0; star < len; star++) {
                Queue<NodeTrie> q = new LinkedList<>();
                q.offer(root);
                int i = 0;
                while (!q.isEmpty() && i < len) {
                    int size = q.size();
                    for (int j = 0; j < size; j++) {
                        NodeTrie cur = q.poll();
                        if (i == star) {
                            for (NodeTrie child : cur.children) {
                                if (child != null) {
                                    if (i == len - 1) {
                                        res.add(child.end);
                                    } else {
                                        q.offer(child);
                                    }
                                }
                            }
                        } else {
                            if (cur.children[word.charAt(i) - 'a'] != null) {
                                if (cur.children[word.charAt(i) - 'a'].end != -1) {
                                    res.add(cur.children[word.charAt(i) - 'a'].end);
                                } else {
                                    q.add(cur.children[word.charAt(i) - 'a']);
                                }
                            }
                        }
                    }
                    i++;
                }
            }
            return res;
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!contains(wordList, endWord)) {
            return 0;
        }
        TrieW trie = new TrieW();
        for (int i = 0; i < wordList.size(); i++) {
            trie.insert(wordList.get(i), i);
        }
        Queue<String> q = new LinkedList<>();
        q.offer(beginWord);
        int count = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String curString = q.poll();
                if (curString.equals(endWord)) {
                    return count;
                }
                List<Integer> iFounds = trie.searchStar(curString);
                for (Integer iFound : iFounds) {
                    if (!wordList.get(iFound).isEmpty()) {
                        q.offer(wordList.get(iFound));
                        wordList.set(iFound, "");
                    }
                }
            }
            count++;
        }
        return 0;
    }

    private boolean contains(List<String> wordList, String word) {
        for (String s : wordList) {
            if (s.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GraphTechnique gt = new GraphTechnique();
        List<String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
        wordList.add("ait");
        wordList.add("lit");
        System.out.println(gt.ladderLength("hit", "cog", wordList));
        System.exit(0);
    }
}
