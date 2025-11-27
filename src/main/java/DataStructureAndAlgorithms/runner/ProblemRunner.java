package DataStructureAndAlgorithms.runner;

import java.util.Arrays;
import java.util.List;

import DataStructureAndAlgorithms.core.ProblemManager;
import DataStructureAndAlgorithms.menus.LabeledOption;
import DataStructureAndAlgorithms.menus.implementations.MainMenuOptions;
import DataStructureAndAlgorithms.menus.implementations.ProblemMenuOptions;
import DataStructureAndAlgorithms.models.ProblemResult;
import DataStructureAndAlgorithms.services.InputService;

public class ProblemRunner {
    private final ProblemManager problemManager;
    private final InputService inputService;
    private final List<String> mainMenuOptions;
    private final List<String> problemMenuOptions;

    public ProblemRunner(ProblemManager problemManager, InputService inputService) {
        this.problemManager = problemManager;
        this.inputService = inputService;
        this.mainMenuOptions = Arrays.stream(MainMenuOptions.values()).map(MainMenuOptions::getLabel).toList();
        this.problemMenuOptions = Arrays.stream(ProblemMenuOptions.values()).map(ProblemMenuOptions::getLabel).toList();
    }

    public void start() {
        showWelcomeMessage();
        showMainMenu();
    }

    private void showMainMenu() {
        try {
            while (true) {
                System.out.println("=== MAIN MENU ===");

                String selection = createMenuAndChooseOption(mainMenuOptions);

                MainMenuOptions selectedOption = LabeledOption.fromLabel(MainMenuOptions.class, selection);

                switch (selectedOption) {
                    case RUN:
                        handleProblemSelection();
                        break;
                    case CREATE_PROBLEM:
                        // TODO
                        break;

                    case MANAGE_PRACTICES:
                        // TODO
                        break;

                    case EXIT:
                        System.out.println("Exiting...");
                        return;
                }
            }
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        } finally {
            inputService.shutDownScanner();
        }
    }

    private void showWelcomeMessage() {
        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("       PROBLEM & PRACTICE RUNNER");
        System.out.println("=========================================");
        System.out.println();
    }

    private void handleProblemSelection() {
        while (true) {
            String selection = createMenuAndChooseOption(problemMenuOptions);

            ProblemMenuOptions selectedOption = LabeledOption.fromLabel(ProblemMenuOptions.class, selection);

            switch (selectedOption) {
                case RUN_SPECIFIC:
                    runProblemByName();
                    break;

                case LIST_PROBLEMS:
                    // TODO
                    break;

                case LIST_BY_CATEGORY:
                    // TODO
                    break;

                case RETURN:
                    return;
            }
        }
    }

    private void handlePracticeSelection() {
    }

    private void runProblemByName() {
        System.out.print("Enter a Problem Name: ");
        String problemName = inputService.getProblemName();
        ProblemResult problemResult = problemManager.runProblem(problemName);
        System.out.println(problemResult.getProblemName() + " Problem Run Successfully ✅"); // ❌
        System.out.println("Result: " + problemResult.getResult());
        waitForEnter();
    }

    private String createMenuAndChooseOption(List<String> options) {
        int i = 1;
        for (String option : options) {
            System.out.println(i + ". " + option);
            i++;
        }
        System.out.printf("Choose an option between 1 - %d: ", options.size());
        return inputService.selectFromList(options);
    }

    private void showErrorMessage(String message) {
        System.out.println("The program has been terminated due to an error: ");
        System.out.println(message);
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        inputService.continueTheProgram();
    }
}
