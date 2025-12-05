package DataStructureAndAlgorithms.Problems.Tree;

import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayDeque;
import java.util.Deque;

import DataStructureAndAlgorithms.core.annotations.Problem;

@Problem(name = "Path Sum", category = "Tree")
public class PathSum extends BaseProblem<Boolean> {
    TreeNode root1 = new TreeNode(5,
            new TreeNode(4, new TreeNode(11, new TreeNode(7), new TreeNode(2)), new TreeNode(8)),
            new TreeNode(8, new TreeNode(13), new TreeNode(4, new TreeNode(1), null)));
    TreeNode root = new TreeNode(1, new TreeNode(-2), new TreeNode(3));
    int targetSum = 1;

    @Override
    public Boolean solve() {
        int sum = 0;
        Deque<TreeNode> bucket = new ArrayDeque<>();
        while (root != null) {
            sum += root.val;
            if (sum == targetSum && root.left == null && root.right == null) {
                return true;
            }
            if (root.left != null && root.right != null) {
                root.right.val += sum;
                bucket.push(root.right);
                root = root.left;
            } else if (root.left != null && root.right == null) {
                root = root.left;
            } else if (root.left == null && root.right != null) {
                root = root.right;
            } else if (root.left == null && root.right == null && !bucket.isEmpty()) {
                root = bucket.pop();
                sum = 0;
            } else {
                break;
            }
        }
        return false;
    }

    public class TreeNode {
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

}