package DataStructureAndAlgorithms.Problems.Stack.Easy;

import DataStructureAndAlgorithms.core.annotations.Problem;
import DataStructureAndAlgorithms.core.base.BaseProblem;

import java.util.Stack;

@Problem(name = "Valid Parentheses", category = "Stack", difficulty = "Easy")
public class ValidParentheses extends BaseProblem<Boolean> {
    public String s = "([)]";

    @Override
    public Boolean solve() {
        if (s.length() % 2 == 1) return false;

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;

                char open = stack.pop();
                if ((open == '(' && c != ')') ||
                        (open == '[' && c != ']') ||
                        (open == '{' && c != '}')) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
