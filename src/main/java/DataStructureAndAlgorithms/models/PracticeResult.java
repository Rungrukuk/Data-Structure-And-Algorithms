package DataStructureAndAlgorithms.models;

public class PracticeResult {
    private final String problemName;
    private final Object practiceResult;
    private final Object expectedResult;
    private final boolean isCorrect;

    public PracticeResult(String problemName, Object practiceResult, Object expectedResult, boolean isCorrect) {
        this.problemName = problemName;
        this.practiceResult = practiceResult;
        this.expectedResult = expectedResult;
        this.isCorrect = isCorrect;
    }

    public String getProblemName() {
        return problemName;
    }

    public Object getPracticeResult() {
        return practiceResult;
    }

    public Object getExpectedResult() {
        return expectedResult;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
