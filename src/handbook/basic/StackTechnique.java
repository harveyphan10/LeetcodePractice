package handbook.basic;

import java.util.*;

/**
 * @author dungptm2
 */
public class StackTechnique {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (Character c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                Character pop = stack.pop();
                if ((c == ')' && pop != '(')
                        || (c == '}' && pop != '{')
                        || (c == ']' && pop != '[')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    class MinStack {
        private final Stack<Integer> stack;
        private ListNode min;

        class ListNode {
            int val;
            ListNode next;

            ListNode(int x, ListNode next) {
                this.val = x;
                this.next = next;
            }
        }

        public MinStack() {
            stack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            if (min == null || val <= min.val) {
                ListNode newMin = new ListNode(val, min);
                min = newMin;
            }
        }

        public void pop() {
            Integer topVal = stack.pop();
            if (topVal == min.val) {
                min = min.next;
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min.val;
        }
    }

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            if (stack.isEmpty()) {
                stack.push(asteroid);
            } else {
                boolean destroyed = false;
                int collision = asteroid;
                while (!stack.isEmpty() && (stack.peek() > 0 && collision < 0)) {
                    if (Math.abs(stack.peek()) == Math.abs(collision)) {
                        stack.pop();
                        destroyed = true;
                        break;
                    }
                    collision = Math.abs(stack.peek()) > Math.abs(collision) ? stack.peek() : collision;
                    stack.pop();
                }
                if (!destroyed) {
                    stack.push(collision);
                }
            }
        }
        int[] result = new int[stack.size()];
        while (!stack.isEmpty()) {
            result[stack.size() - 1] = stack.pop();
        }
        return result;
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s : tokens) {
            switch (s) {
                case "+" -> {
                    int num2 = stack.pop();
                    int num1 = stack.pop();
                    stack.push(num1 + num2);
                }
                case "-" -> {
                    int num2 = stack.pop();
                    int num1 = stack.pop();
                    stack.push(num1 - num2);
                }
                case "*" -> {
                    int num2 = stack.pop();
                    int num1 = stack.pop();
                    stack.push(num1 * num2);
                }
                case "/" -> {
                    int num2 = stack.pop();
                    int num1 = stack.pop();
                    stack.push(num1 / num2);
                }
                default -> stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }

    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        char sign = '+';
        int num = 0;
        for (int i = 0; i <= s.length(); i++) {
            if (i < s.length() && Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            } else if (i == s.length() || s.charAt(i) != ' ') {
                switch (sign) {
                    case '+' -> stack.push(num);
                    case '-' -> stack.push(-num);
                    case '*' -> stack.push(stack.pop() * num);
                    case '/' -> stack.push(stack.pop() / num);
                    default -> {
                    }
                }
                if (i < s.length()) {
                    sign = s.charAt(i);
                }
                num = 0;
            }
        }
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }

    public int calculate2(String s) {
        Stack<String> operatorStack = new Stack<>();
        List<String> query = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        sb.append(s.charAt(0));
        for (int i = 1; i <= s.length(); i++) {
            if (i == s.length() || isSplit(s.charAt(i - 1), s.charAt(i))) {
                String str = sb.toString().trim();
                if (str.isEmpty()) {
                    sb.append(s.charAt(i));
                    continue;
                }
                if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                    if (operatorStack.isEmpty()) {
                        operatorStack.push(str);
                    } else {
                        while (!operatorStack.isEmpty() && compareOp(str, operatorStack.peek()) <= 0) {
                            query.add(operatorStack.pop());
                        }
                        operatorStack.push(str);
                    }
                } else {
                    query.add(str);
                }
                sb = new StringBuffer();
            }
            if (i < s.length()) {
                sb.append(s.charAt(i));
            }
        }

        while (!operatorStack.isEmpty()) {
            query.add(operatorStack.pop());
        }
        Stack<Integer> calStack = new Stack<>();
        for (String str : query) {
            switch (str) {
                case "+" -> {
                    int num2 = calStack.pop();
                    int num1 = calStack.pop();
                    calStack.push(num1 + num2);
                }
                case "-" -> {
                    int num2 = calStack.pop();
                    int num1 = calStack.pop();
                    calStack.push(num1 - num2);
                }
                case "*" -> {
                    int num2 = calStack.pop();
                    int num1 = calStack.pop();
                    calStack.push(num1 * num2);
                }
                case "/" -> {
                    int num2 = calStack.pop();
                    int num1 = calStack.pop();
                    calStack.push(num1 / num2);
                }
                default -> {
                    calStack.push(Integer.parseInt(str));
                }
            }
        }
        return calStack.pop();
    }

    private int compareOp(String op1, String op2) {
        if ((addMinusOps.contains(op1) && addMinusOps.contains(op2)) || (productDivideOps.contains(op1) && productDivideOps.contains(op2))) {
            return 0;
        } else if (addMinusOps.contains(op1) && productDivideOps.contains(op2)) {
            return -1;
        }
        return 1;
    }

    Set<String> addMinusOps = new HashSet<>(Arrays.asList("+", "-"));
    Set<String> productDivideOps = new HashSet<>(Arrays.asList("*", "/"));
    Set<Character> setDigit = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    Set<Character> setOps = new HashSet<>(Arrays.asList('+', '-', '*', '*', '/', ' '));

    private boolean isSplit(Character c1, Character c2) {
        return (setDigit.contains(c1) && setOps.contains(c2)) || (setDigit.contains(c2) && setOps.contains(c1));
    }

    public static void main(String[] args) {
        StackTechnique st = new StackTechnique();
        System.out.println(st.calculate("3+2*2"));
        System.exit(0);
    }
}
