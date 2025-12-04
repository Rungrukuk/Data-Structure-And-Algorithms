package DataStructureAndAlgorithms.domain.problems;

import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.core.models.ProblemResult;
import DataStructureAndAlgorithms.infrastructure.runner.CodeRunner;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Optional;

public class ProblemExecutor {
    private final CodeRunner codeRunner;

    public ProblemExecutor(CodeRunner codeRunner) {
        this.codeRunner = codeRunner;
    }

    public Optional<ProblemResult> runProblem(ProblemInfo problemInfo) {
        return codeRunner.runProblem(problemInfo);
    }

    // public Optional<ProblemResult> runProblemByDisplay(String displayString,
    // ProblemSelector selector) {
    // return selector.findProblemByDisplay(displayString)
    // .flatMap(this::runProblem);
    // }

    public String formatResult(ProblemResult result) {
        return ApplicationConstants.ANSI_GREEN +
                result.getProblemName() + " Problem Run Successfully ✅\n" +
                "Result: " + result.getResult() +
                ApplicationConstants.ANSI_RESET;
    }
}