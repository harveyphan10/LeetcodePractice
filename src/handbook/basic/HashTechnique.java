package handbook.basic;

import javax.swing.plaf.IconUIResource;
import java.util.*;

/**
 * @author dungptm2
 */
public class HashTechnique {

    public boolean canConstruct(String ransomNote, String magazine) {
        int[] map = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            map[magazine.charAt(i) - 'a']++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            map[ransomNote.charAt(i) - 'a']--;
            if (map[ransomNote.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    class RandomizedSet {
        private List<Integer> list;
        private Map<Integer, Integer> map;

        public RandomizedSet() {
            map = new HashMap<Integer, Integer>();
            list = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (!map.containsKey(val)) {
                map.put(val, list.size());
                list.add(val);
                return true;
            }
            return false;
        }

        public boolean remove(int val) {
            if (map.containsKey(val)) {
                Integer index = map.get(val);
                Integer last = list.getLast();
                list.set(index, last);
                map.put(last, index);
                list.removeLast();
                map.remove(val);
                return true;
            }
            return false;
        }

        public int getRandom() {
            return list.get(new Random().nextInt(list.size()));
        }
    }

    /**
     * Your RandomizedSet object will be instantiated and called as such:
     * RandomizedSet obj = new RandomizedSet();
     * boolean param_1 = obj.insert(val);
     * boolean param_2 = obj.remove(val);
     * int param_3 = obj.getRandom();
     */

    static class LRUCache {
        class Node {
            int key;
            int value;
            Node pre;
            Node next;

            public Node() {
            }

            public Node(int key, int value, Node pre, Node next) {
                this.key = key;
                this.value = value;
                this.pre = pre;
                this.next = next;
            }

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private HashMap<Integer, Node> map;
        private int capacity;
        private Node head;
        private Node tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>(capacity);
        }

        private void moveToTail(Node curNode) {
            if (map.isEmpty()) {
                tail = curNode;
                head = curNode;
            } else {
                if (curNode.key == tail.key) {
                    return;
                }
                if (curNode.key == head.key) {
                    head = head.next;
                    head.pre = null;
                }
                if (curNode.pre != null) {
                    curNode.pre.next = curNode.next;
                }
                if (curNode.next != null) {
                    curNode.next.pre = curNode.pre;
                }
                tail.next = curNode;
                curNode.next = null;
                curNode.pre = tail;
                tail = curNode;
                if (curNode.key == head.key) {
                    head = head.next;
                    head.pre = null;
                }
            }
        }

        private void removeHead() {
            if (map.isEmpty()) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                if (head != null) {
                    head.pre = null;
                }
            }
        }

        private void addTail(Node curNode) {
            if (map.isEmpty()) {
                tail = curNode;
                head = curNode;
            } else {
                tail.next = curNode;
                curNode.pre = tail;
                tail = curNode;
            }
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            Node curNode = map.get(key);
            moveToTail(curNode);
            return curNode.value;
        }

        public void put(int key, int value) {
            if (!map.containsKey(key)) {
                if (map.size() >= capacity) {
                    map.remove(head.key);
                    removeHead();
                }
                Node newNode = new Node(key, value);
                addTail(newNode);
                map.put(key, newNode);
            } else {
                Node curNode = map.get(key);
                moveToTail(curNode);
                curNode.value = value;
            }
        }
    }

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */

    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0 || nums[i] > n) {
                nums[i] = n + 1;
            }
        }
        for (int i = 0; i < n; i++) {
            int curNum = Math.abs(nums[i]);
            if (curNum > n) {
                continue;
            }
            curNum -= 1;
            if (nums[curNum] > 0) {
                nums[curNum] = -nums[curNum];
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }

    static class AllOne {

        class Node {
            String key;
            int value;
            Node pre;
            Node next;

            public Node(String key, int value, Node pre, Node next) {
                this.key = key;
                this.value = value;
                this.pre = pre;
                this.next = next;
            }

            public Node(String key, int value) {
                this.key = key;
                this.value = value;
            }
        }


        class PairNode {
            Node start;
            Node end;

            public PairNode(Node start, Node end) {
                this.start = start;
                this.end = end;
            }
        }

        private Map<String, Node> map;
        private Node head;
        private Node tail;
        private Map<Integer, PairNode> checkpoint;

        public AllOne() {
            map = new HashMap<>();
            checkpoint = new HashMap<>();
        }

        public void inc(String key) {
            if (!map.containsKey(key)) {
                Node newNode = new Node(key, 1, null, head);
                if (head != null) {
                    head.pre = newNode;
                }
                PairNode indexKey = checkpoint.getOrDefault(newNode.value, new PairNode(newNode, newNode));
                indexKey.start = newNode;
                checkpoint.put(newNode.value, indexKey);
                map.put(key, newNode);
                head = newNode;
                if (tail == null) {
                    tail = newNode;
                }
            } else {
                Node curNode = map.get(key);
                PairNode indexNodeSameValue = checkpoint.get(curNode.value);
                if (curNode.pre != null) {
                    curNode.pre.next = curNode.next;
                }
                if (curNode.next != null) {
                    curNode.next.pre = curNode.pre;
                }
                if (indexNodeSameValue.start.key.equals(indexNodeSameValue.end.key)) {
                    checkpoint.remove(curNode.value);
                } else if (indexNodeSameValue.start.key.equals(curNode.key)) {
                    indexNodeSameValue.start = indexNodeSameValue.start.next;
                    checkpoint.put(curNode.value, indexNodeSameValue);
                    if (curNode.key.equals(head.key) && head.next != null) {
                        head = head.next;
                    }
                } else if (indexNodeSameValue.end.key.equals(curNode.key)) {
                    indexNodeSameValue.end = indexNodeSameValue.end.pre;
                    checkpoint.put(curNode.value, indexNodeSameValue);
                }
                curNode.value++;
                PairNode newIndexValue = checkpoint.get(curNode.value);
                if (newIndexValue == null) {
                    newIndexValue = new PairNode(curNode, curNode);
                } else {
                    Node tmp = newIndexValue.end.next;
                    newIndexValue.end.next = curNode;
                    curNode.pre = newIndexValue.end;
                    curNode.next = tmp;
                    tmp.pre = curNode;
                    newIndexValue.end = curNode;
                }
                checkpoint.put(curNode.value, newIndexValue);
                if (tail.next != null) {
                    tail = curNode;
                }
            }
        }

        public void dec(String key) {
            if (!map.containsKey(key)) {
                return;
            }
            Node curNode = map.get(key);
            PairNode indexNodeSameValue = checkpoint.get(curNode.value);
            if (curNode.pre != null) {
                curNode.pre.next = curNode.next;
            }
            if (curNode.next != null) {
                curNode.next.pre = curNode.pre;
            }
            if (indexNodeSameValue.start.key.equals(indexNodeSameValue.end.key)) {
                checkpoint.remove(curNode.value);
            } else if (indexNodeSameValue.start.key.equals(curNode.key)) {
                indexNodeSameValue.start = indexNodeSameValue.start.next;
                checkpoint.put(curNode.value, indexNodeSameValue);
                if (curNode.value == 1 && curNode.key.equals(head.key)) {
                    head = head.next;
                }
            } else if (indexNodeSameValue.end.key.equals(curNode.key)) {
                indexNodeSameValue.end = indexNodeSameValue.end.pre;
                checkpoint.put(curNode.value, indexNodeSameValue);
            }
            curNode.value--;
            PairNode newIndexValue = checkpoint.get(curNode.value);
            if (newIndexValue == null) {
                newIndexValue = new PairNode(curNode, curNode);
            } else {
                Node tmp = newIndexValue.end.next;
                newIndexValue.end.next = curNode;
                curNode.pre = newIndexValue.end;
                curNode.next = tmp;
                tmp.pre = curNode;
                newIndexValue.end = curNode;
            }
            checkpoint.put(curNode.value, newIndexValue);
            if (tail.next != null) {
                tail = curNode;
            }
        }

        public String getMaxKey() {
            return tail != null ? tail.key : "";
        }

        public String getMinKey() {
            return head != null ? head.key : "";
        }
    }

    /**
     * Your AllOne object will be instantiated and called as such:
     * AllOne obj = new AllOne();
     * obj.inc(key);
     * obj.dec(key);
     * String param_3 = obj.getMaxKey();
     * String param_4 = obj.getMinKey();
     */

    public static void main(String[] args) {
        HashTechnique ht = new HashTechnique();

        AllOne obj = new AllOne();
        obj.inc("b");
        obj.inc("a");
        obj.inc("c");
        obj.inc("b");
        obj.inc("a");
        obj.inc("c");
        obj.dec("b");
        obj.inc("c");
        System.out.println(obj.getMaxKey());
        System.out.println(obj.getMinKey());
        System.exit(0);
    }
}
