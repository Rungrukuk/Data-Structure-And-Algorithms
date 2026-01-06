package DataStructureAndAlgorithms.Problems.Stack.Medium;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.ArrayDeque;
import java.util.Deque;

@Problem(name = "Evaluate Reverse Polish Notation", category = "Stack", difficulty = "Medium")
public class EvaluateReversePolishNotation extends BaseProblem<Integer> {
    public String[] tokens = new String[]{"2", "1", "+", "3", "*"};

    @Override
    public Integer solve() {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            switch (token) {
                case "+" -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a + b);
                }
                case "-" -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                }
                case "*" -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a * b);
                }
                case "/" -> {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                }
                default -> stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }
}
