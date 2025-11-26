package DataStructureAndAlgorithms.models;

public class ProblemResult {
    private final String problemName;
    private final Object result;

    public ProblemResult(String problemName, Object result) {
        this.problemName = problemName;
        this.result = result;
    }

    public String getProblemName() {
        return problemName;
    }

    public Object getResult() {
        return result;
    }
}
