package DataStructureAndAlgorithms.core.base;

import java.util.Objects;

public abstract class BasePractice<T, P extends BaseProblem<T>> {
    protected P problem;

    public BasePractice(P problem) {
        this.problem = problem;
    }

    public boolean compare() {
        T solutionResult = problem.solve();
        T practiceResult = this.practice();
        return Objects.deepEquals(practiceResult, solutionResult);
    }

    public abstract T practice();
}
