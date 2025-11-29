package DataStructureAndAlgorithms.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import DataStructureAndAlgorithms.exceptions.InvalidInputException;
import DataStructureAndAlgorithms.menus.LabeledOption;
import DataStructureAndAlgorithms.menus.implementations.MainMenuOptions;
import DataStructureAndAlgorithms.menus.implementations.ProblemMenuOptions;
import DataStructureAndAlgorithms.models.PracticeResult;
import DataStructureAndAlgorithms.models.ProblemResult;
import DataStructureAndAlgorithms.runner.ProblemRunner;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.utils.Constants;

public class ApplicationManager {

    private final ProblemRunner problemRunner;
    private final InputService inputService;
    private final List<String> mainMenuOptions;
    private final List<String> problemMenuOptions;

    public ApplicationManager(ProblemRunner problemRunner, InputService inputService) {
        this.problemRunner = problemRunner;
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
                    showProblemMenu();
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
    private void showProblemMenu() {
        while (true) {
            String selection = retryUntilSuccessNonEmpty(
                    () -> createMenuAndChooseOption(problemMenuOptions),
                    Constants.INCORRECT_OPTION);

            ProblemMenuOptions option = LabeledOption.fromLabel(ProblemMenuOptions.class, selection);

            switch (option) {
                case RUN_SPECIFIC_PROBLEM:
                    chooseAndRun(true);
                    break;
                case LIST_PROBLEMS:
                    listAll(true);
                    break;
                case LIST_PROBLEMS_BY_CATEGORY:
                    listByCategory(true);
                    break;
                case RUN_SPECIFIC_PRACTICE:
                    chooseAndRun(false);
                    break;
                case LIST_PRACTICES:
                    listAll(false);
                    break;
                case LIST_PRACTICES_BY_CATEGORY:
                    listByCategory(false);
                    break;
                case RETURN:
                    return;
            }
        }
    }

    private void chooseAndRun(boolean isProblem) {
        while (true) {
            Optional<String> nameOptional = askName(isProblem);
            if (nameOptional.isEmpty()) {
                System.out.println("Returning...");
                break;
            }
            String name = nameOptional.get();

            List<String> variants = isProblem
                    ? problemRunner.getProblemVariants(name)
                    : problemRunner.getPracticeVariants(name);

            String selectedVariant;
            if (variants.size() == 1) {
                selectedVariant = variants.get(0); // only one variant
            } else {
                selectedVariant = retryUntilSuccessNonEmpty(
                        () -> createMenuAndChooseOption(variants),
                        Constants.INCORRECT_OPTION);
            }

            if (isProblem)
                runProblemByName(selectedVariant);
            else
                runPracticeByName(selectedVariant);
        }
    }

    private void listAll(boolean isProblem) {
        List<String> available = isProblem
                ? problemRunner.getAvailableProblems()
                : problemRunner.getAvailablePractices();

        String selected = retryUntilSuccessNonEmpty(
                () -> createMenuAndChooseOption(available),
                Constants.INCORRECT_OPTION);

        if (isProblem)
            runProblemByName(selected);
        else
            runPracticeByName(selected);
    }

    private void listByCategory(boolean isProblem) {
        Map<String, List<String>> byCategory = isProblem
                ? problemRunner.getProblemsByCategory()
                : problemRunner.getPracticesByCategory();

        List<String> categories = new ArrayList<>(byCategory.keySet());

        String category = retryUntilSuccessNonEmpty(
                () -> createMenuAndChooseOption(categories),
                Constants.INCORRECT_OPTION);

        List<String> items = byCategory.get(category);

        String selected = retryUntilSuccessNonEmpty(
                () -> createMenuAndChooseOption(items),
                Constants.INCORRECT_OPTION);

        if (isProblem)
            runProblemByName(selected);
        else
            runPracticeByName(selected);
    }

    // ========================= RUNNERS =========================
    private void runProblemByName(String name) {
        Optional<ProblemResult> result = problemRunner.runProblem(name);
        result.ifPresentOrElse(r -> {
            System.out.println(Constants.ANSI_GREEN + r.getProblemName() + " Problem Run Successfully ✅");
            System.out.println("Result: " + r.getResult() + Constants.ANSI_RESET);
            waitForEnter();
        }, () -> showErrorMessage(Constants.DIDNOT_FIND_PROBLEM_NAME + name));
    }

    private void runPracticeByName(String name) {
        Optional<PracticeResult> result = problemRunner.runPractice(name);
        result.ifPresentOrElse(r -> {
            System.out.println("Practice Answer: " + r.getPracticeResult());
            System.out.println("Expected Answer: " + r.getExpectedResult());
            System.out.println("Result: "
                    + (r.isCorrect() ? Constants.ANSI_GREEN + "✅ CORRECT" : Constants.ANSI_RED + "❌ INCORRECT")
                    + Constants.ANSI_RESET);
            waitForEnter();
        }, () -> showErrorMessage(Constants.DIDNOT_FIND_PROBLEM_NAME + name));
    }

    // ========================= INPUT & MENU =========================
    private Optional<String> askName(boolean isProblem) {
        return retryUntilSuccess(() -> {
            System.out.print(isProblem ? Constants.ENTER_PROBLEM_NAME : Constants.ENTER_PRACTICE_NAME);
            String input = inputService.getProblemOrPracticeName();

            if (input.equals(String.valueOf(Constants.RETURN_BACK)))
                return Optional.empty();
            return Optional.of(input);
        }, isProblem ? Constants.INCORRECT_PROBLEM_NAME : Constants.INCORRECT_PRACTICE_NAME);
    }

    private String createMenuAndChooseOption(List<String> options) {
        for (int i = 0; i < options.size(); i++)
            System.out.println((i + 1) + ". " + options.get(i));
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
        System.out.println("=========================================\n");
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
