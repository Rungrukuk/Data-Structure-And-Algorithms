package DataStructureAndAlgorithms.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import DataStructureAndAlgorithms.core.ProblemManager;
import DataStructureAndAlgorithms.exceptions.InvalidInputException;
import DataStructureAndAlgorithms.menus.LabeledOption;
import DataStructureAndAlgorithms.menus.implementations.MainMenuOptions;
import DataStructureAndAlgorithms.menus.implementations.ProblemMenuOptions;
import DataStructureAndAlgorithms.models.ProblemResult;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.utils.Constants;

public class ProblemRunner {

    private final ProblemManager problemManager;
    private final InputService inputService;
    private final List<String> mainMenuOptions;
    private final List<String> problemMenuOptions;

    public ProblemRunner(ProblemManager problemManager, InputService inputService) {
        this.problemManager = problemManager;
        this.inputService = inputService;
        this.mainMenuOptions = Arrays.stream(MainMenuOptions.values())
                .map(MainMenuOptions::getLabel)
                .toList();
        this.problemMenuOptions = Arrays.stream(ProblemMenuOptions.values())
                .map(ProblemMenuOptions::getLabel)
                .toList();
    }

    public void start() {
        showWelcomeMessage();
        showMainMenu();
    }

    // ========================= MAIN MENU =========================
    private void showMainMenu() {
        while (true) {
            System.out.println("=== MAIN MENU ===");

            String selection = retryUntilSuccessNonEmpty(
                    () -> createMenuAndChooseOption(mainMenuOptions),
                    Constants.INCORRECT_OPTION);

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
                    inputService.shutDownScanner();
                    System.out.println("Exiting...");
                    return;
            }
        }
    }

    // ========================= PROBLEM MENU =========================
    private void handleProblemSelection() {
        while (true) {
            String selection = retryUntilSuccessNonEmpty(
                    () -> createMenuAndChooseOption(problemMenuOptions),
                    Constants.INCORRECT_OPTION);

            ProblemMenuOptions selectedOption = LabeledOption.fromLabel(ProblemMenuOptions.class, selection);

            switch (selectedOption) {
                case RUN_SPECIFIC:
                    runSpecificProblem();
                    break;
                case LIST_PROBLEMS:
                    listAllProblems();
                    break;
                case LIST_BY_CATEGORY:
                    listProblemsByCategory();
                    break;
                case RETURN:
                    return;
            }
        }
    }

    private void handlePracticeSelection() {
        // TODO implement the logic
    }

    private void runSpecificProblem() {
        while (true) {
            Optional<String> optionalName = askProblemName();

            if (optionalName.isEmpty()) {
                System.out.println("Returning to Problem Menu...");
                break;
            }

            String problemName = optionalName.get();
            runProblemByName(problemName);
        }
    }

    private void listAllProblems() {
        List<String> availableProblems = problemManager.getAvailableProblems();
        String problemName = retryUntilSuccessNonEmpty(
                () -> createMenuAndChooseOption(availableProblems),
                Constants.INCORRECT_OPTION);
        runProblemByName(problemName);
    }

    private void listProblemsByCategory() {
        Map<String, List<String>> problemsByCategory = problemManager.getProblemsByCategory();

        List<String> categories = new ArrayList<>(problemsByCategory.keySet());

        String categoryName = retryUntilSuccessNonEmpty(
                () -> createMenuAndChooseOption(categories),
                Constants.INCORRECT_OPTION);

        List<String> problems = problemsByCategory.get(categoryName);

        String problemName = retryUntilSuccessNonEmpty(
                () -> createMenuAndChooseOption(problems),
                Constants.INCORRECT_OPTION);

        runProblemByName(problemName);
    }

    private void runProblemByName(String problemName) {
        Optional<ProblemResult> problemResult = problemManager.runProblem(problemName);

        problemResult.ifPresentOrElse(result -> {
            System.out.println(Constants.ANSI_GREEN + result.getProblemName() + " Problem Run Successfully ✅");
            System.out.println("Result: " + result.getResult() + Constants.ANSI_RESET);
            waitForEnter();
        }, () -> showErrorMessage(Constants.DIDNOT_FIND_PROBLEM_NAME + problemName));
    }

    // ========================= INPUT METHODS =========================
    private Optional<String> askProblemName() {
        return retryUntilSuccess(
                () -> {
                    System.out.print(Constants.ENTER_PROBLEM_NAME);
                    String userInput = inputService.getProblemName();

                    if (userInput.equals(String.valueOf(Constants.RETURN_BACK))) {
                        return Optional.empty();// usig empty to return back
                    }

                    return Optional.of(userInput);
                },
                Constants.INCORRECT_PROBLEM_NAME);
    }

    private String createMenuAndChooseOption(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.printf(Constants.CHOOSE_OPTION, options.size());

        return inputService.selectFromList(options);
    }

    // ========================= RETRY HELPERS =========================
    private <T> T retryUntilSuccessNonEmpty(Supplier<T> supplier, String errorMessage) {
        while (true) {
            try {
                return supplier.get();
            } catch (InvalidInputException e) {
                showErrorMessage(errorMessage, e);
            }
        }
    }

    private <T> Optional<T> retryUntilSuccess(Supplier<Optional<T>> supplier, String errorMessage) {
        while (true) {
            try {
                return supplier.get();
            } catch (InvalidInputException e) {
                showErrorMessage(errorMessage, e);
            }
        }
    }

    // ========================= UI HELPERS =========================
    private void showWelcomeMessage() {
        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("       PROBLEM & PRACTICE RUNNER");
        System.out.println("=========================================");
        System.out.println();
    }

    private void showErrorMessage(String message, Exception e) {
        System.out.println(Constants.ANSI_RED + message + " ❌");
        System.out.println(e.getMessage() + Constants.ANSI_RESET);
    }

    private void showErrorMessage(String message) {
        System.out.println(Constants.ANSI_RED + message + " ❌" + Constants.ANSI_RESET);
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        inputService.continueTheProgram();
    }
}
