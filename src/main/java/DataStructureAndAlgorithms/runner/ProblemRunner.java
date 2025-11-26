package DataStructureAndAlgorithms.runner;

import DataStructureAndAlgorithms.core.ProblemManager;
import DataStructureAndAlgorithms.services.InputService;

public class ProblemRunner {
    private final ProblemManager problemManager;
    private final InputService inputService;

    public ProblemRunner(ProblemManager problemManager, InputService inputService) {
        this.problemManager = problemManager;
        this.inputService = inputService;
    }

    public void start() {
        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("       PROBLEM & PRACTICE RUNNER");
        System.out.println("=========================================");
    }

    private void showMainMenu() {
    }

    private void handleProblemSelection() {
    }

    private void handlePracticeSelection() {
    }
}
