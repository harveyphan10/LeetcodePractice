package handbook.basic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author dungptm2
 */
public class QueueTechnique {
    public static class MyStack {
        private LinkedList<Integer> queue;

        public MyStack() {
            queue = new LinkedList<Integer>();
        }

        public void push(int x) {
            queue.add(x);
        }

        public int pop() {
            return queue.removeLast();
        }

        public int top() {
            return queue.getLast();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }
    class MyCircularQueue {
        private final ArrayList<Integer> queue;
        private final int capacity;
        public MyCircularQueue(int k) {
            queue = new ArrayList<>(k);
            capacity = k;
        }

        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            return queue.add(value);
        }

        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            queue.removeFirst();
            return true;
        }

        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return queue.getFirst();
        }

        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            return queue.getLast();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public boolean isFull() {
            return queue.size() == capacity;
        }
    }
}
