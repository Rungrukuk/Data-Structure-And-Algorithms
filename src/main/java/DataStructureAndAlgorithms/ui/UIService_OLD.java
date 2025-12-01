package DataStructureAndAlgorithms.ui;

import DataStructureAndAlgorithms.exceptions.InvalidInputException;
import DataStructureAndAlgorithms.infrastructure.input.InputService_OLD;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;
import DataStructureAndAlgorithms.utils.helpers.TextHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class UIService_OLD {
    private final InputService_OLD inputService;

    public UIService_OLD(InputService_OLD inputService) {
        this.inputService = inputService;
    }

    // ========================= DISPLAY METHODS =========================

    public void showWelcomeMessage() {
        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("       PROBLEM & PRACTICE RUNNER");
        System.out.println("=========================================\n");
    }

    public void showSectionTitle(String title) {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }

    public void showSuccessMessage(String message) {
        System.out.println(ApplicationConstants.ANSI_GREEN + "✅ " + message + ApplicationConstants.ANSI_RESET);
    }

    public void showErrorMessage(String message) {
        System.out.println(ApplicationConstants.ANSI_RED + "❌ " + message + ApplicationConstants.ANSI_RESET);
    }

    public void showErrorMessage(String message, String cause) {
        System.out.println(ApplicationConstants.ANSI_RED + "❌ " + message);
        if (cause != null && !cause.trim().isEmpty()) {
            System.out.println("   " + cause);
        }
        System.out.print(ApplicationConstants.ANSI_RESET);
    }

    public void showInfoMessage(String message) {
        System.out.println("💡 " + message);
    }

    public void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        inputService.continueTheProgram();
    }

    // ========================= INPUT METHODS =========================

    public String getNameWithPrompt(String prompt) {
        System.out.print(prompt);
        return inputService.getNameInput();
    }

    public String getReturnTypeWithPrompt(String prompt) {
        System.out.print(prompt);
        return inputService.getReturnType();
    }

    public Optional<String> getOptionalNameInputWithPrompt(String prompt) {
        System.out.print(prompt);
        String input = inputService.getNameInput();

        if (input.equals(ApplicationConstants.RETURN_BACK)) {
            return Optional.empty();
        }
        return Optional.of(input);
    }

    // ========================= MENU & SELECTION METHODS =========================

    public String showMenuAndGetSelection(List<String> options, String title) {
        showSectionTitle(title);
        return createMenuAndChooseOption(options);
    }

    public String showMenuAndGetSelection(List<String> options) {
        return createMenuAndChooseOption(options);
    }

    private String createMenuAndChooseOption(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.printf(ApplicationConstants.CHOOSE_OPTION, options.size());
        return inputService.selectFromList(options);
    }

    public String selectFromList(List<String> options, String prompt) {
        if (options == null || options.isEmpty()) {
            showErrorMessage("No options available");
            return null;
        }

        System.out.println("\n" + prompt);
        return createMenuAndChooseOption(options);
    }

    public String selectCategory(Collection<String> categories) {
        List<String> categoryList = new ArrayList<>(categories);
        return selectFromList(categoryList, "Select Category:");
    }

    // ========================= INPUT VALIDATION HELPERS =========================

    public <T> T getValidatedInput(Supplier<T> inputSupplier, String errorMessage) {
        return retryUntilSuccess(inputSupplier, errorMessage);
    }

    public <T> Optional<T> getValidatedOptionalInput(Supplier<Optional<T>> inputSupplier, String errorMessage) {
        return retryUntilSuccessOptional(inputSupplier, errorMessage);
    }

    public String getValidatedProblemName(String prompt) {
        return getValidatedInput(
                () -> getNameWithPrompt(prompt),
                ApplicationConstants.INVALID_PROBLEM_NAME);
    }

    public String getValidatedCategory(String prompt) {
        return getValidatedInput(
                () -> getNameWithPrompt(prompt),
                ApplicationConstants.INVALID_CATEGORY_NAME);
    }

    public String getValidatedReturnType(String prompt) {
        return getValidatedInput(
                () -> getReturnTypeWithPrompt(prompt),
                ApplicationConstants.INVALID_RETURN_TYPE);
    }

    // ========================= RETRY HELPERS =========================

    public <T> T retryUntilSuccess(Supplier<T> supplier, String errorMessage) {
        while (true) {
            try {
                T result = supplier.get();
                if (result != null && !result.toString().trim().isEmpty()) {
                    return result;
                }
                showErrorMessage(errorMessage);
            } catch (InvalidInputException e) {
                showErrorMessage(errorMessage, e.getMessage());
            } catch (Exception e) {
                showErrorMessage("Unexpected error occurred");
            }
        }
    }

    public <T> Optional<T> retryUntilSuccessOptional(Supplier<Optional<T>> supplier, String errorMessage) {
        while (true) {
            try {
                Optional<T> result = supplier.get();
                if (result.isPresent()) {
                    return result;
                }
                showErrorMessage(errorMessage);
            } catch (InvalidInputException e) {
                showErrorMessage(errorMessage, e.getMessage());
            } catch (Exception e) {
                showErrorMessage("Unexpected error occurred");
            }
        }
    }

    // ========================= SUGGESTION HELPERS =========================

    public void showSimilarSuggestions(String input, List<String> allOptions) {
        List<String> similar = findSimilarOptions(input, allOptions);
        if (!similar.isEmpty()) {
            System.out.println("💡 Did you mean:");
            similar.forEach(name -> System.out.println("   - " + name));
        }
    }

    private List<String> findSimilarOptions(String input, List<String> allOptions) {
        String normalizedInput = input.toLowerCase().replaceAll("[^a-z0-9]", "");
        return allOptions.stream()
                .filter(option -> {
                    String normalizedOption = option.toLowerCase().replaceAll("[^a-z0-9]", "");
                    return normalizedOption.contains(normalizedInput) ||
                            normalizedInput.contains(normalizedOption) ||
                            TextHelper.calculateSimilarity(normalizedInput,
                                    normalizedOption) > ApplicationConstants.MINIMUM_SIMILARITY_VALUE;
                })
                .limit(3)
                .toList();
    }

    // ========================= CLEANUP =========================

    public void shutdown() {
        inputService.shutDownScanner();
    }
}