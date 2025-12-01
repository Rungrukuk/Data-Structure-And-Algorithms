package DataStructureAndAlgorithms.core.base;

public abstract class BasePractice<T, P extends BaseProblem<T>> {
    protected P problem;

    public BasePractice(P problem) {
        this.problem = problem;
    }

    public boolean compare() {
        T solutionResult = problem.solve();
        T practiceResult = this.practice();
        return solutionResult.equals(practiceResult);
    }

    public T getSolutionResult() {
        return problem.solve();
    }

    public T getPracticeResult() {
        return practice();
    }

    public abstract T practice();
}
