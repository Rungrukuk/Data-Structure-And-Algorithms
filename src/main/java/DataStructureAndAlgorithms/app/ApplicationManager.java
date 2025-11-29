package DataStructureAndAlgorithms.app;

import java.util.*;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DataStructureAndAlgorithms.creators.PracticeCreator;
import DataStructureAndAlgorithms.creators.ProblemCreator;
import DataStructureAndAlgorithms.exceptions.InvalidInputException;
import DataStructureAndAlgorithms.menus.LabeledOption;
import DataStructureAndAlgorithms.menus.implementations.CreatePracticeMenuOptions;
import DataStructureAndAlgorithms.menus.implementations.MainMenuOptions;
import DataStructureAndAlgorithms.menus.implementations.ManagePracticesMenuOptions;
import DataStructureAndAlgorithms.menus.implementations.ProblemMenuOptions;
import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.models.PracticeResult;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.models.ProblemResult;
import DataStructureAndAlgorithms.runner.ProblemRunner;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.utils.Constants;

public class ApplicationManager {
    private static final Logger log = LogManager.getLogger(App.class);

    private final ProblemRunner problemRunner;
    private final InputService inputService;
    private final List<String> mainMenuOptions;
    private final List<String> problemMenuOptions;
    private final List<String> managePracticeOptions;
    private final List<String> createPracticeMenuOptions;

    private final PracticeCreator practiceCreator;
    private final ProblemCreator problemCreator;

    public ApplicationManager(
            ProblemRunner problemRunner,
            InputService inputService,
            ProblemCreator problemCreator,
            PracticeCreator practiceCreator) {

        this.problemRunner = problemRunner;
        this.inputService = inputService;
        this.problemCreator = problemCreator;
        this.practiceCreator = practiceCreator;

        this.mainMenuOptions = Arrays.stream(MainMenuOptions.values())
                .map(MainMenuOptions::getLabel)
                .toList();

        this.problemMenuOptions = Arrays.stream(ProblemMenuOptions.values())
                .map(ProblemMenuOptions::getLabel)
                .toList();

        this.managePracticeOptions = Arrays.stream(ManagePracticesMenuOptions.values())
                .map(ManagePracticesMenuOptions::getLabel)
                .toList();
        this.createPracticeMenuOptions = Arrays.stream(CreatePracticeMenuOptions.values())
                .map(CreatePracticeMenuOptions::getLabel)
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
                    handleCreateProblemMenu();
                    break;
                case MANAGE_PRACTICES:
                    handleManagePracticesMenu();
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
                    runSelectedProblem();
                    break;
                case LIST_PROBLEMS:
                    listAndRunProblem();
                    break;
                case LIST_PROBLEMS_BY_CATEGORY:
                    listAndRunProblemByCategory();
                    break;
                case RUN_SPECIFIC_PRACTICE:
                    runSelectedPractice();
                    break;
                case LIST_PRACTICES:
                    listAndRunPractice();
                    break;
                case LIST_PRACTICES_BY_CATEGORY:
                    listAndRunPracticeByCategory();
                    break;
                case RETURN:
                    return;
            }
        }
    }

    // ========================= PROBLEM SELECTION & RUNNING ====================
    private void runSelectedProblem() {
        Optional<String> nameOptional = askProblemName();
        if (nameOptional.isEmpty())
            return;

        String name = nameOptional.get();
        List<String> variants = problemRunner.getProblemVariants(name);

        if (variants.isEmpty()) {
            showErrorMessage(Constants.DIDNOT_FIND_PROBLEM_NAME + name);
            return;
        }

        String selectedVariant = selectVariant(variants, "problem");
        runProblemByName(selectedVariant);
    }

    private void listAndRunProblem() {
        List<String> available = problemRunner.getAvailableProblems();
        if (available.isEmpty()) {
            showErrorMessage("No problems available.");
            return;
        }

        String selected = selectFromList(available, "Select Problem to Run");
        runProblemByName(selected);
    }

    private void listAndRunProblemByCategory() {
        Map<String, List<String>> byCategory = problemRunner.getProblemsByCategory();
        if (byCategory.isEmpty()) {
            showErrorMessage("No problems available.");
            return;
        }

        String category = selectCategory(byCategory.keySet());
        if (category == null)
            return;

        List<String> problems = byCategory.get(category);
        String selected = selectFromList(problems, "Select Problem from " + category);
        runProblemByName(selected);
    }

    // ========================= PRACTICE SELECTION & RUNNING ====================
    private void runSelectedPractice() {
        Optional<String> nameOptional = askPracticeName();
        if (nameOptional.isEmpty())
            return;

        String name = nameOptional.get();
        List<String> variants = problemRunner.getPracticeVariants(name);

        if (variants.isEmpty()) {
            showErrorMessage(Constants.DIDNOT_FIND_PRACTICE_NAME + name);
            return;
        }

        String selectedVariant = selectVariant(variants, "practice");
        runPracticeByName(selectedVariant);
    }

    private void listAndRunPractice() {
        List<String> available = problemRunner.getAvailablePractices();
        if (available.isEmpty()) {
            showErrorMessage("No practices available.");
            return;
        }

        String selected = selectFromList(available, "Select Practice to Run");
        runPracticeByName(selected);
    }

    private void listAndRunPracticeByCategory() {
        Map<String, List<String>> byCategory = problemRunner.getPracticesByCategory();
        if (byCategory.isEmpty()) {
            showErrorMessage("No practices available.");
            return;
        }

        String category = selectCategory(byCategory.keySet());
        if (category == null)
            return;

        List<String> practices = byCategory.get(category);
        String selected = selectFromList(practices, "Select Practice from " + category);
        runPracticeByName(selected);
    }

    // ========================= PROBLEM CREATION =========================
    private void handleCreateProblemMenu() {
        System.out.println("=== CREATE NEW PROBLEM ===");

        String problemName = getValidatedInput(
                () -> {
                    System.out.print("Enter problem name: ");
                    return inputService.getProblemOrPracticeName();
                },
                Constants.INVALID_PROBLEM_NAME);

        String category = getValidatedInput(
                () -> {
                    System.out.print("Enter problem category: ");
                    return inputService.getCategory();
                },
                Constants.INVALID_CATEGORY_NAME);

        String returnType = getValidatedInput(
                () -> {
                    System.out.print("Enter return type (e.g., Integer, List<String>): ");
                    return inputService.getReturnType();
                },
                Constants.INVALID_RETURN_TYPE);

        ProblemInfo info = new ProblemInfo(problemName, category, null, returnType, null);

        try {
            problemCreator.createProblem(info);
            showSuccessMessage("Problem created successfully!");
        } catch (Exception e) {
            showErrorMessage("Error occurred in creation of file", e.getMessage() != null ? e.getMessage() : "");
            log.error(e);
        }

        waitForEnter();
    }

    // ========================= PRACTICE MANAGEMENT =========================
    private void handleManagePracticesMenu() {
        while (true) {
            System.out.println("=== MANAGE PRACTICES ===");

            String selection = retryUntilSuccessNonEmpty(
                    () -> createMenuAndChooseOption(managePracticeOptions),
                    Constants.INCORRECT_OPTION);

            ManagePracticesMenuOptions option = LabeledOption.fromLabel(ManagePracticesMenuOptions.class, selection);

            switch (option) {
                case CREATE_PRACTICE:
                    handleCreatePracticeMenu();
                    break;
                case RESET_PRACTICE:
                    handleResetPracticeMenu();
                    break;
                case RETURN:
                    return;
            }
        }
    }

    private void handleCreatePracticeMenu() {
        System.out.println("=== CREATE PRACTICE FOR EXISTING PROBLEM ===");

        String selection = retryUntilSuccessNonEmpty(
                () -> createMenuAndChooseOption(createPracticeMenuOptions),
                Constants.INCORRECT_OPTION);

        CreatePracticeMenuOptions option = LabeledOption.fromLabel(CreatePracticeMenuOptions.class, selection);

        Optional<ProblemInfo> chosenProblem = Optional.empty();

        switch (option) {
            case LIST_PROBLEMS:
                chosenProblem = chooseProblemFromList();
                break;
            case LIST_PROBLEMS_BY_CATEGORY:
                chosenProblem = chooseProblemByCategory();
                break;
            case FIND_SPECIFIC_PROBLEM:
                chosenProblem = chooseProblemByName();
                break;
            case RETURN:
                return;
        }

        if (chosenProblem.isEmpty()) {
            System.out.println("Returning...");
            waitForEnter();
            return;
        }

        createPracticeForProblem(chosenProblem.get());
    }

    private void handleResetPracticeMenu() {
        System.out.println("=== RESET PRACTICE ===");
        System.out.println("Reset practice functionality will be implemented here.");
        waitForEnter();
    }

    // ============== PROBLEM SELECTION FOR PRACTICE CREATION ==============
    private Optional<ProblemInfo> chooseProblemFromList() {
        try {
            List<ProblemInfo> allProblems = practiceCreator.getProblemInfoList();
            if (allProblems.isEmpty()) {
                showErrorMessage("No problems found. Please create a problem first.");
                return Optional.empty();
            }

            List<String> problemDisplays = allProblems.stream()
                    .map(problem -> problem.getName() + " [" + problem.getCategory() + "]")
                    .toList();

            String selectedDisplay = selectFromList(problemDisplays, "Select Problem from List");

            return allProblems.stream()
                    .filter(problem -> (problem.getName() + " [" + problem.getCategory() + "]").equals(selectedDisplay))
                    .findFirst();

        } catch (Exception e) {
            showErrorMessage("Error listing problems", e.getMessage());
            log.error("Error in chooseProblemFromList", e);
            return Optional.empty();
        }
    }

    private Optional<ProblemInfo> chooseProblemByCategory() {
        try {
            Map<String, List<ProblemInfo>> problemsByCategory = practiceCreator.getProblemInfoByCategory();
            if (problemsByCategory.isEmpty()) {
                showErrorMessage("No problems found. Please create a problem first.");
                return Optional.empty();
            }

            String selectedCategory = selectCategory(problemsByCategory.keySet());
            if (selectedCategory == null)
                return Optional.empty();

            List<ProblemInfo> categoryProblems = problemsByCategory.get(selectedCategory);
            if (categoryProblems == null || categoryProblems.isEmpty()) {
                showErrorMessage("No problems found in category: " + selectedCategory);
                return Optional.empty();
            }

            List<String> problemDisplays = categoryProblems.stream()
                    .map(problem -> problem.getName() + " [" + problem.getCategory() + "]")
                    .toList();

            String selectedDisplay = selectFromList(problemDisplays, "Select Problem from " + selectedCategory);

            return categoryProblems.stream()
                    .filter(problem -> (problem.getName() + " [" + problem.getCategory() + "]").equals(selectedDisplay))
                    .findFirst();

        } catch (Exception e) {
            showErrorMessage("Error selecting problem by category", e.getMessage());
            log.error("Error in chooseProblemByCategory", e);
            return Optional.empty();
        }
    }

    private Optional<ProblemInfo> chooseProblemByName() {
        try {
            System.out.println("=== FIND PROBLEM BY NAME ===");
            String problemName = getValidatedInput(
                    () -> {
                        System.out.print("Enter problem name: ");
                        return inputService.getProblemOrPracticeName();
                    },
                    Constants.INVALID_PROBLEM_NAME);

            List<ProblemInfo> matchingProblems = practiceCreator.findProblemInfoByName(problemName);

            if (matchingProblems.isEmpty()) {
                showErrorMessage("No problems found with name: " + problemName);
                suggestSimilarProblems(problemName, problemRunner.getAvailableProblems());
                return Optional.empty();
            }

            if (matchingProblems.size() == 1) {
                ProblemInfo problem = matchingProblems.get(0);
                showSuccessMessage("Found: " + problem.getName() + " [" + problem.getCategory() + "]");
                return Optional.of(problem);
            }

            List<String> problemDisplays = matchingProblems.stream()
                    .map(problem -> problem.getName() + " [" + problem.getCategory() + "]")
                    .toList();

            String selectedDisplay = selectFromList(problemDisplays, "Multiple problems found");

            return matchingProblems.stream()
                    .filter(problem -> (problem.getName() + " [" + problem.getCategory() + "]").equals(selectedDisplay))
                    .findFirst();

        } catch (Exception e) {
            showErrorMessage("Error finding problem by name", e.getMessage());
            log.error("Error in chooseProblemByName", e);
            return Optional.empty();
        }
    }

    private void createPracticeForProblem(ProblemInfo problemInfo) {
        Optional<ProblemInfo> existingProblem = practiceCreator.getProblemIfExists(problemInfo.getUniqueId());

        if (existingProblem.isEmpty()) {
            showErrorMessage(Constants.DIDNOT_FIND_PRACTICE_NAME + problemInfo.getName());
            waitForEnter();
            return;
        }

        ProblemInfo info = existingProblem.get();
        PracticeInfo practiceInfo = new PracticeInfo(info, null, null);

        try {
            practiceCreator.createPractice(practiceInfo);
            showSuccessMessage("Practice created successfully for: " + info.getName());
        } catch (Exception e) {
            showErrorMessage("Failed to create practice", e.getMessage());
            log.error(e);
        }

        waitForEnter();
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

    // ==================== REUSABLE SELECTION METHODS ====================
    private Optional<String> askProblemName() {
        return askName(true);
    }

    private Optional<String> askPracticeName() {
        return askName(false);
    }

    private Optional<String> askName(boolean isProblem) {
        return retryUntilSuccess(() -> {
            System.out.print(isProblem ? Constants.ENTER_PROBLEM_NAME : Constants.ENTER_PRACTICE_NAME);
            String input = inputService.getProblemOrPracticeName();

            if (input.equals(String.valueOf(Constants.RETURN_BACK)))
                return Optional.empty();
            return Optional.of(input);
        }, isProblem ? Constants.INCORRECT_PROBLEM_NAME : Constants.INCORRECT_PRACTICE_NAME);
    }

    private String selectVariant(List<String> variants, String type) {
        if (variants.size() == 1) {
            return variants.get(0);
        }
        return selectFromList(variants, "Select " + type + " variant");
    }

    private String selectCategory(Collection<String> categories) {
        List<String> categoryList = new ArrayList<>(categories);
        if (categoryList.isEmpty())
            return null;

        return selectFromList(categoryList, "Select Category");
    }

    private String selectFromList(List<String> options, String title) {
        System.out.println("=== " + title + " ===");
        return createMenuAndChooseOption(options);
    }

    // ========================= INPUT & MENU =========================
    private String createMenuAndChooseOption(List<String> options) {
        for (int i = 0; i < options.size(); i++)
            System.out.println((i + 1) + ". " + options.get(i));
        System.out.printf(Constants.CHOOSE_OPTION, options.size());
        return inputService.selectFromList(options);
    }

    private String getValidatedInput(Supplier<String> inputSupplier, String errorMessage) {
        return retryUntilSuccessNonEmpty(inputSupplier, errorMessage);
    }

    // ========================= RETRY HELPERS =========================
    private <T> T retryUntilSuccessNonEmpty(Supplier<T> supplier, String errorMessage) {
        while (true) {
            try {
                return supplier.get();
            } catch (InvalidInputException e) {
                showErrorMessage(errorMessage, e.getMessage());
            }
        }
    }

    private <T> Optional<T> retryUntilSuccess(Supplier<Optional<T>> supplier, String errorMessage) {
        while (true) {
            try {
                return supplier.get();
            } catch (InvalidInputException e) {
                showErrorMessage(errorMessage, e.getMessage());
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

    private void showSuccessMessage(String message) {
        System.out.println(Constants.ANSI_GREEN + "✅ " + message + Constants.ANSI_RESET);
    }

    private void showErrorMessage(String message, String cause) {
        System.out.println(Constants.ANSI_RED + message + " ❌");
        if (cause != null && !cause.trim().isEmpty()) {
            System.out.println(cause);
        }
        System.out.print(Constants.ANSI_RESET);
    }

    private void showErrorMessage(String message) {
        System.out.println(Constants.ANSI_RED + message + " ❌" + Constants.ANSI_RESET);
    }

    private void suggestSimilarProblems(String input, List<String> allProblems) {
        List<String> similar = findSimilarProblems(input, allProblems);
        if (!similar.isEmpty()) {
            System.out.println("💡 Did you mean:");
            similar.forEach(name -> System.out.println("   - " + name));
        }
    }

    private List<String> findSimilarProblems(String input, List<String> allProblems) {
        String normalizedInput = input.toLowerCase().replaceAll("[^a-z0-9]", "");
        return allProblems.stream()
                .filter(problem -> {
                    String normalizedProblem = problem.toLowerCase().replaceAll("[^a-z0-9]", "");
                    return normalizedProblem.contains(normalizedInput) ||
                            normalizedInput.contains(normalizedProblem) ||
                            calculateSimilarity(normalizedInput, normalizedProblem) > 0.7;
                })
                .limit(5)
                .toList();
    }

    private double calculateSimilarity(String s1, String s2) {
        String longer = s1.length() > s2.length() ? s1 : s2;
        String shorter = s1.length() > s2.length() ? s2 : s1;

        if (longer.length() == 0)
            return 1.0;

        return (longer.length() - editDistance(longer, shorter)) / (double) longer.length();
    }

    private int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        inputService.continueTheProgram();
    }
}