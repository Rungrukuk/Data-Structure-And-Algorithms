package DataStructureAndAlgorithms;

public abstract class Base_Test<T, P extends Base_Problem<T>> {
    protected P problem;

    public Base_Test(P problem) {
        this.problem = problem;
    }

    public boolean compare() {
        return problem.solve().equals(this.test());
    }

    protected abstract T test();
}
