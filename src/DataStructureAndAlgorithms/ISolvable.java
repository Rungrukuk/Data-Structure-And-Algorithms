package DataStructureAndAlgorithms;

public interface ISolvable {
    enum SolutionKind {
        LinearTime,
        LogarthmicTime,
        ConstantTime,
        ParabolicTime
    };

    void solve(SolutionKind solutionKind);
}
