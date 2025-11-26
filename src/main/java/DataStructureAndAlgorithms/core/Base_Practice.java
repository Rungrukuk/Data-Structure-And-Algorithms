package DataStructureAndAlgorithms.core;

public abstract class Base_Practice<T, P extends Base_Problem<T>> {
    protected P problem;

    public Base_Practice(P problem) {
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

    protected abstract T practice();
}