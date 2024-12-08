package handbook.basic;

import java.util.*;

/**
 * @author dungptm2
 */
public class LinkedListTechnique {
    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        List<ListNode> list = new ArrayList<>();
        ListNode tmp = head;
        while (tmp != null) {
            list.add(tmp);
            tmp = tmp.next;
        }
        int i = 0, j = list.size() - 1;
        while (i < j) {
            swapValNode(list.get(i), list.get(j));
            i++;
            j--;
        }
        return head;
    }

    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode head = new ListNode(0);
        ListNode prev = head;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                head.next = list1;
                head = head.next;
                list1 = list1.next;
            } else {
                head.next = list2;
                head = head.next;
                list2 = list2.next;
            }
        }
        if (list1 != null) {
            head.next = list1;
        } else if (list2 != null) {
            head.next = list2;
        }
        return prev.next;
    }

    private void swapValNode(ListNode node1, ListNode node2) {
        int tmpVal = node1.val;
        node1.val = node2.val;
        node2.val = tmpVal;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || (head.next == null && n == 1)) {
            return null;
        }
        ListNode preSlow = head;
        ListNode slow = head;
        ListNode fast = head;
        int count = 1;
        while (fast.next != null && count < n) {
            count++;
            fast = fast.next;
        }
        while (fast.next != null) {
            preSlow = slow;
            slow = slow.next;
            fast = fast.next;
        }
        if (slow == head) {
            return head.next;
        }
        preSlow.next = slow.next;
        return head;
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            stack.push(tail);
        }
        ListNode connectNode = head;
        while (connectNode.next != null) {
            ListNode tmp = connectNode.next;
            ListNode curTail = stack.pop();
            if (connectNode == curTail || connectNode == tmp) {
                connectNode.next = null;
                break;
            }
            connectNode.next = curTail;
            curTail.next = tmp;
            connectNode = tmp;
        }
    }

    public void reorderList2(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast.next != null) {
            fast = fast.next;
        }
        ListNode prev = slow;
        ListNode middle = slow;
        slow = slow.next;
        while (slow.next != null) {
            ListNode tmp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = tmp;
        }
        fast.next = prev;
        middle.next = null;
        ListNode connect = head;
        while (connect != null ||  fast != null) {
            if (connect == null) {
                connect = fast;
                break;
            } else if (fast == null) {
                break;
            }
            ListNode nextConnect = connect.next;
            ListNode preFast = fast.next;
            connect.next = fast;
            fast.next = nextConnect;
            connect = nextConnect;
            fast = preFast;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> q = new PriorityQueue<>((Comparator.comparingInt(o -> o.val)));
        for (ListNode node : lists) {
            if (node != null) {
                q.offer(node);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while (!q.isEmpty()) {
            ListNode minNode = q.poll();
            head.next = minNode;
            head = minNode;
            if (minNode.next != null) {
                q.offer(minNode.next);
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        LinkedListTechnique ll = new LinkedListTechnique();
        ListNode head = new ListNode(1);
        head.next = new ListNode(5);
        ListNode head2 = new ListNode(2);
        head2.next = new ListNode(4);
        ListNode head3 = new ListNode(3);
        head3.next = new ListNode(6);
        ListNode listNode = ll.mergeKLists(new ListNode[]{head, head2, head3});
        System.exit(0);
    }
}
