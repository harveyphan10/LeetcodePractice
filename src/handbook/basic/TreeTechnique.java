package handbook.basic;

import java.util.*;

/**
 * @author dungptm2
 */
public class TreeTechnique {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepth(root.left) + 1;
        int rightDepth = maxDepth(root.right) + 1;
        return Math.max(leftDepth, rightDepth);
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        invertTree(root.left);
        invertTree(root.right);
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        return root;
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathP = new ArrayList<>();
        List<TreeNode> pathQ = new ArrayList<>();
        searchNode(root, p, pathP);
        searchNode(root, q, pathQ);

        if (pathP.size() < pathQ.size()) {
            for (int i = pathP.size() - 1; i >= 0; i--) {
                if (pathQ.contains(pathP.get(i))) {
                    return pathP.get(i);
                }
            }
        } else {
            for (int i = pathQ.size() - 1; i >= 0; i--) {
                if (pathP.contains(pathQ.get(i))) {
                    return pathQ.get(i);
                }
            }
        }
        return null;
    }

    private void searchNode(TreeNode root, TreeNode node, List<TreeNode> path) {
        if (root.val == node.val) {
            path.add(root);
            return;
        }
        path.add(root);
        if (node.val < root.val) {
            searchNode(root.left, node, path);
        } else {
            searchNode(root.right, node, path);
        }
    }

    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        int minVal = Math.min(p.val, q.val);
        int maxVal = Math.max(p.val, q.val);

        while (root != null) {
            if (minVal <= root.val && root.val <= maxVal) {
                return root;
            } else if (maxVal < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return null;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> valLevel = new ArrayList<>();
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.poll();
                valLevel.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            if (!valLevel.isEmpty()) {
                res.add(valLevel);
            }
        }
        return res;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode lcaLeft = lowestCommonAncestor(root.left, p, q);
        TreeNode lcaRight = lowestCommonAncestor(root.right, p, q);
        if (lcaLeft != null && lcaRight != null) {
            return root;
        }
        return lcaLeft != null ? lcaLeft : lcaRight;
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int count = queue.size();
            res.add(queue.peek().val);
            while (count-- > 0) {
                TreeNode node = queue.poll();
                if (node.right != null) {
                    queue.add(node.right);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
            }
        }
        return res;
    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null || subRoot == null) {
            return false;
        }
        boolean sameTree = sameTree(root, subRoot);
        if (!sameTree) {
            boolean subtree = isSubtree(root.left, subRoot);
            if (!subtree) {
                subtree = isSubtree(root.right, subRoot);
            }
            return subtree;
        }
        return true;
    }

    private boolean sameTree(TreeNode tree1, TreeNode tree2) {
        if (tree1 == null && tree2 == null) {
            return true;
        }
        if (tree1 == null || tree2 == null) {
            return false;
        }
        if (tree1.val != tree2.val) {
            return false;
        }
        return sameTree(tree1.left, tree2.left) && sameTree(tree1.right, tree2.right);
    }

    private int maxVal = Integer.MIN_VALUE;
    private boolean isFirst = true;

    public boolean isValidBST(TreeNode root) {
        if (root.left == null && root.right == null) {
            return true;
        }
        return recurseValidBST2(root);
    }

    public boolean recurseValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean validLeft = recurseValidBST2(root.left);
        if (validLeft && (isFirst || maxVal < root.val)) {
            maxVal = root.val;
            isFirst = false;
            return recurseValidBST2(root.right);
        }
        return false;
    }

    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        return recurseKSmallest(stack, root, k);
    }

    private int recurseKSmallest(Stack<TreeNode> stack, TreeNode root, int k) {
        if (root == null) {
            return -1;
        }
        int kvalue = recurseKSmallest(stack, root.left, k);
        if (kvalue < 0) {
            stack.push(root);
            if (stack.size() == k) {
                return stack.peek().val;
            }
            return recurseKSmallest(stack, root.right, k);
        }
        return kvalue;
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int hLeft = height(root.left);
        int hRight = height(root.right);
        if (Math.abs(hLeft - hRight) > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    private int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int max = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode curNode = q.poll();
                max = Math.max(max, height(curNode.left) + height(curNode.right));
                if (curNode.left != null) {
                    q.offer(curNode.left);
                }
                if (curNode.right != null) {
                    q.offer(curNode.right);
                }
            }
        }
        return max;
    }

    public TreeNode reverseOddLevels(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            List<TreeNode> revertNodes = new ArrayList<>(size);
            List<Integer> revertVals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode curNode = q.poll();
                if (level % 2 == 1) {
                    revertNodes.add(curNode);
                    revertVals.add(curNode.val);
                }
                if (curNode.left != null) {
                    q.offer(curNode.left);
                    q.offer(curNode.right);
                }
            }
            if (!revertNodes.isEmpty()) {
                for (int i = 0; i < size; i++) {
                    revertNodes.get(i).val = revertVals.get(size - 1 - i);
                }
            }
            level++;
        }
        return root;
    }

    public int minimumOperations(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int swap = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            int[] vals = new int[size];
            for (int i = 0; i < size; i++) {
                TreeNode poll = q.poll();
                vals[i] = poll.val;
                if (poll.left != null) {
                    q.offer(poll.left);
                }
                if (poll.right != null) {
                    q.offer(poll.right);
                }
            }
            PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a));
            Map<Integer, Integer> mapIndex = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                heap.offer(vals[i]);
                mapIndex.put(vals[i], i);
            }
            int i = 0;
            while (!heap.isEmpty()) {
                int poll = heap.poll();
                if (poll != vals[i]) {
                    swap++;
                    int iSmallest = mapIndex.get(poll);
                    mapIndex.put(vals[i], iSmallest);
                    int temp = vals[i];
                    vals[i] = poll;
                    vals[iSmallest] = temp;
                }
                i++;
            }
        }
        return swap;
    }

    public static void main(String[] args) {
        TreeTechnique treeTechnique = new TreeTechnique();
        TreeNode root = new TreeNode(6,
                new TreeNode(2, new TreeNode(0), new TreeNode(4, new TreeNode(3), new TreeNode(5))),
                new TreeNode(8, new TreeNode(1), new TreeNode(9)));
        TreeNode root2 = new TreeNode(31,
                new TreeNode(30,
                        new TreeNode(3,
                                new TreeNode(0, null, new TreeNode(2, new TreeNode(1, null, null), null)), null), null), null);
        TreeNode root3 = new TreeNode(2,
                new TreeNode(1, new TreeNode(4, new TreeNode(8, new TreeNode(9), null), null),
                        new TreeNode(5, new TreeNode(6, new TreeNode(7), null), null)), new TreeNode(3));
        System.out.println(treeTechnique.diameterOfBinaryTree(root3));
        System.exit(0);
    }
}
