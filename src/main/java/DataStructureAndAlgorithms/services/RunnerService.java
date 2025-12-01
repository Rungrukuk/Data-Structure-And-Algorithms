package DataStructureAndAlgorithms.services;

import DataStructureAndAlgorithms.core.models.PracticeResult;
import DataStructureAndAlgorithms.core.models.ProblemResult;
import DataStructureAndAlgorithms.infrastructure.runner.ProblemRunner_OLD;
import DataStructureAndAlgorithms.ui.UIService_OLD;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Optional;

public class RunnerService {
    private final ProblemRunner_OLD problemRunner;
    private final UIService_OLD uiService;

    public RunnerService(ProblemRunner_OLD problemRunner, UIService_OLD uiService) {
        this.problemRunner = problemRunner;
        this.uiService = uiService;
    }

    public void runProblemByName(String name) {
        Optional<ProblemResult> result = problemRunner.runProblem(name);
        result.ifPresentOrElse(
                r -> {
                    System.out.println(ApplicationConstants.ANSI_GREEN + r.getProblemName() + " Problem Run Successfully ✅");
                    System.out.println("Result: " + r.getResult() + ApplicationConstants.ANSI_RESET);
                    uiService.waitForEnter();
                },
                () -> uiService.showErrorMessage(ApplicationConstants.DIDNOT_FIND_PROBLEM_NAME + name));
    }

    public void runPracticeByName(String name) {
        Optional<PracticeResult> result = problemRunner.runPractice(name);
        result.ifPresentOrElse(
                r -> {
                    System.out.println("Practice Answer: " + r.getPracticeResult());
                    System.out.println("Expected Answer: " + r.getExpectedResult());
                    System.out.println("Result: "
                            + (r.isCorrect() ? ApplicationConstants.ANSI_GREEN + "✅ CORRECT" : ApplicationConstants.ANSI_RED + "❌ INCORRECT")
                            + ApplicationConstants.ANSI_RESET);
                    uiService.waitForEnter();
                },
                () -> uiService.showErrorMessage(ApplicationConstants.DIDNOT_FIND_PRACTICE_NAME + name));
    }

    public void runProblemWithVariants(String name) {
        var variants = problemRunner.getProblemVariants(name);
        if (variants.isEmpty()) {
            uiService.showErrorMessage(ApplicationConstants.DIDNOT_FIND_PROBLEM_NAME + name);
            return;
        }

        if (variants.size() == 1) {
            runProblemByName(variants.get(0));
        } else {
            String selected = uiService.selectFromList(variants, "Select problem variant");
            if (selected != null) {
                runProblemByName(selected);
            }
        }
    }

    public void runPracticeWithVariants(String name) {
        var variants = problemRunner.getPracticeVariants(name);
        if (variants.isEmpty()) {
            uiService.showErrorMessage(ApplicationConstants.DIDNOT_FIND_PRACTICE_NAME + name);
            return;
        }

        if (variants.size() == 1) {
            runPracticeByName(variants.get(0));
        } else {
            String selected = uiService.selectFromList(variants, "Select practice variant");
            if (selected != null) {
                runPracticeByName(selected);
            }
        }
    }
}