package DataStructureAndAlgorithms.domain.practices;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.PracticeResult;
import DataStructureAndAlgorithms.infrastructure.runner.CodeRunner;
import DataStructureAndAlgorithms.utils.ApplicationConstants;
import DataStructureAndAlgorithms.utils.ResultFormatter;

import java.util.Optional;

public class PracticeExecutor {
    private final CodeRunner codeRunner;

    public PracticeExecutor(CodeRunner codeRunner) {
        this.codeRunner = codeRunner;
    }

    public Optional<PracticeResult> runPractice(PracticeInfo practiceInfo) {
        return codeRunner.runPractice(practiceInfo);
    }

    public String formatResult(PracticeResult result) {
        StringBuilder sb = new StringBuilder();

        sb.append("Practice Answer: ")
                .append(ResultFormatter.format(result.getPracticeResult()))
                .append("\n");

        sb.append("Expected Answer: ")
                .append(ResultFormatter.format(result.getExpectedResult()))
                .append("\n");

        if (result.isCorrect()) {
            sb.append(ApplicationConstants.ANSI_GREEN)
                    .append("✅ CORRECT")
                    .append(ApplicationConstants.ANSI_RESET);
        } else {
            sb.append(ApplicationConstants.ANSI_RED)
                    .append("❌ INCORRECT")
                    .append(ApplicationConstants.ANSI_RESET);
        }

        return sb.toString();
    }
}