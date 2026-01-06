package DataStructureAndAlgorithms.Problems.Stack.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.Stack;

@Problem(name = "Min Stack", category = "Stack", difficulty = "Medium")
public class MinStack extends BaseProblem<Integer> {
    private final Stack<Integer> stack = new Stack<>();
    private final Stack<Integer> minStack = new Stack<>();

    @Override
    public Integer solve() {
        return minStack.peek();
    }

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        if (stack.pop().equals(minStack.peek())) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }
}
