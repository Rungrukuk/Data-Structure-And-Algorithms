package DataStructureAndAlgorithms;

public abstract class Base_Test<T, S extends Base_Solution<T>> {
    protected S solution;

    public Base_Test(S solution) {
        this.solution = solution;
    }

    public boolean compare() {
        return solution.solve().equals(this.test());
    }

    protected abstract T test();
}
