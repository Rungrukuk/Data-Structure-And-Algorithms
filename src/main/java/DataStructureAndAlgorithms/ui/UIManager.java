package DataStructureAndAlgorithms.ui;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class UIManager {
    private final InputHandler inputHandler;
    private final MenuService menuService;

    public UIManager(InputHandler inputHandler, MenuService menuService) {
        this.inputHandler = inputHandler;
        this.menuService = menuService;
    }

    // ========================= DISPLAY METHODS =========================

    public void showWelcome() {
        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("       PROBLEM & PRACTICE RUNNER");
        System.out.println("=========================================\n");
    }

    public void showSectionTitle(String title) {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }

    public void showSuccess(String message) {
        System.out.println(ApplicationConstants.ANSI_GREEN + "✅ " + message + ApplicationConstants.ANSI_RESET);
    }

    public void showError(String message) {
        System.out.println(ApplicationConstants.ANSI_RED + "❌ " + message + ApplicationConstants.ANSI_RESET);
    }

    public void showError(String message, String cause) {
        System.out.println(ApplicationConstants.ANSI_RED + "❌ " + message);
        if (cause != null && !cause.trim().isEmpty()) {
            System.out.println("   " + cause);
        }
        System.out.print(ApplicationConstants.ANSI_RESET);
    }

    public void showInfo(String message) {
        System.out.println("💡 " + message);
    }

    public void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        inputHandler.waitForEnter();
    }

    // ========================= INPUT VALIDATION =========================

    public <T> T getValidatedInput(Supplier<T> inputSupplier, String errorMessage) {
        return retryUntilSuccess(inputSupplier, errorMessage);
    }

    public <T> T getValidatedInput(Supplier<T> inputSupplier, String errorMessage,
            Supplier<String> retryMessageSupplier) {
        return retryUntilSuccess(inputSupplier, errorMessage, retryMessageSupplier);
    }

    public <T> Optional<T> getValidatedOptionalInput(Supplier<Optional<T>> inputSupplier, String errorMessage) {
        return retryUntilSuccessOptional(inputSupplier, errorMessage);
    }

    // ========================= MENU NAVIGATION =========================

    public String showMenuAndGetSelection(List<String> options, String title) {
        showSectionTitle(title);
        return menuService.showMenu(options);
    }

    public String showMenuAndGetSelection(List<String> options) {
        return menuService.showMenu(options);
    }

    public String selectFromList(List<String> options, String prompt) {
        return menuService.selectFromList(options, prompt);
    }

    public String selectFromCollection(List<String> items, String prompt) {
        return menuService.selectFromCollection(items, prompt);
    }

    // ========================= INPUT PROMPTS =========================

    public String getProblemNameWithPrompt(String prompt) {
        System.out.print(prompt);
        return getValidatedInput(() -> inputHandler.readProblemName(),
                ApplicationConstants.INVALID_PROBLEM_NAME);
    }

    public String getCategoryWithPrompt(String prompt) {
        System.out.print(prompt);
        return getValidatedInput(() -> inputHandler.readCategory(),
                ApplicationConstants.INVALID_CATEGORY_NAME);
    }

    public String getReturnTypeWithPrompt(String prompt) {
        System.out.print(prompt);
        return getValidatedInput(() -> inputHandler.readReturnType(),
                ApplicationConstants.INVALID_RETURN_TYPE);
    }

    public Optional<String> getOptionalInputWithPrompt(String prompt, String exitKeyword) {
        System.out.print(prompt);
        String input = inputHandler.readLine().trim();

        if (input.equals(exitKeyword)) {
            return Optional.empty();
        }
        return Optional.of(input);
    }

    // ========================= SUGGESTIONS =========================

    public void showSimilarSuggestions(String input, List<String> allOptions) {
        List<String> similar = findSimilarOptions(input, allOptions);
        if (!similar.isEmpty()) {
            showInfo("Did you mean:");
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
                            DataStructureAndAlgorithms.utils.helpers.TextHelper.calculateSimilarity(normalizedInput,
                                    normalizedOption) > ApplicationConstants.MINIMUM_SIMILARITY_VALUE;
                })
                .limit(3)
                .toList();
    }

    // ========================= RETRY LOGIC =========================

    private <T> T retryUntilSuccess(Supplier<T> supplier, String errorMessage) {
        return retryUntilSuccess(supplier, errorMessage, () -> errorMessage);
    }

    private <T> T retryUntilSuccess(Supplier<T> supplier, String errorMessage,
            Supplier<String> retryMessageSupplier) {
        while (true) {
            try {
                T result = supplier.get();
                if (result != null) {
                    if (result instanceof String str && str.trim().isEmpty()) {
                        showError(errorMessage);
                        continue;
                    }
                    return result;
                }
                showError(errorMessage);
            } catch (Exception e) {
                String message = e.getMessage();
                if (message == null || message.isEmpty()) {
                    showError(retryMessageSupplier.get());
                } else {
                    showError(retryMessageSupplier.get(), message);
                }
            }
        }
    }

    private <T> Optional<T> retryUntilSuccessOptional(Supplier<Optional<T>> supplier, String errorMessage) {
        while (true) {
            try {
                Optional<T> result = supplier.get();
                if (result.isPresent()) {
                    return result;
                }
                showError(errorMessage);
            } catch (Exception e) {
                String message = e.getMessage();
                if (message == null || message.isEmpty()) {
                    showError(errorMessage);
                } else {
                    showError(errorMessage, message);
                }
            }
        }
    }

    // ========================= CLEANUP =========================

    public void shutdown() {
        inputHandler.close();
    }
}