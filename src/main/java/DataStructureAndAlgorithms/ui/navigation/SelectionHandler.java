package DataStructureAndAlgorithms.ui.navigation;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.utils.helpers.TextHelper;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;
import java.util.Optional;

public class SelectionHandler {
    private final InputHandler inputHandler;
    private final UIManager uiManager;

    public SelectionHandler(InputHandler inputHandler, UIManager uiManager) {
        this.inputHandler = inputHandler;
        this.uiManager = uiManager;
    }

    // ========================= MENU SELECTION =========================

    public Optional<String> selectFromMenu(List<String> options, String title) {
        if (options == null || options.isEmpty()) {
            uiManager.showError("No options available.");
            return Optional.empty();
        }

        // uiManager.showMenuOptions(options, title);

        return selectItem(options, title, s -> s);
    }

    public Optional<String> selectFromOptions(List<String> options, String prompt, String errorMessage) {
        while (true) {
            try {
                uiManager.showMenuOptions(options);
                uiManager.showSelectionPrompt(options.size());
                int choice = inputHandler.readInt();

                if (choice < 1 || choice > options.size()) {
                    uiManager.showError(errorMessage + ". Please enter a number between 1 and " + options.size());
                    continue;
                }

                return Optional.of(options.get(choice - 1));
            } catch (Exception e) {
                uiManager.showError(errorMessage + ". Please enter a valid number.");
            }
        }
    }

    // ========================= ITEM SELECTION =========================

    public <T> Optional<T> selectItem(List<T> items, String prompt, ItemFormatter<T> formatter) {
        if (items == null || items.isEmpty()) {
            uiManager.showError("No items available.");
            return Optional.empty();
        }

        List<String> displayStrings = items.stream()
                .map(formatter::format)
                .toList();

        uiManager.showSectionTitle(prompt);

        Optional<String> selectedDisplay = selectFromOptions(displayStrings, prompt, "Invalid selection");

        return selectedDisplay.flatMap(display -> {
            for (int i = 0; i < items.size(); i++) {
                if (displayStrings.get(i).equals(display)) {
                    return Optional.of(items.get(i));
                }
            }
            return Optional.empty();
        });
    }

    public Optional<ProblemInfo> selectProblem(List<ProblemInfo> problems, String prompt) {
        return selectItem(problems, prompt, this::formatProblem);
    }

    public Optional<PracticeInfo> selectPractice(List<PracticeInfo> practices, String prompt) {
        return selectItem(practices, prompt, this::formatPractice);
    }

    public Optional<String> selectCategory(List<String> categories, String prompt) {
        if (categories == null || categories.isEmpty()) {
            uiManager.showError("No categories available.");
            return Optional.empty();
        }

        return selectFromOptions(categories, prompt, "Invalid category selection");
    }

    // ========================= SUGGESTIONS =========================

    public void showSimilarSuggestions(String input, List<String> allOptions) {
        List<String> similar = findSimilarOptions(input, allOptions);
        if (!similar.isEmpty()) {
            uiManager.showInfo("Did you mean:");
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

    // ========================= FORMATTERS =========================

    private String formatProblem(ProblemInfo problem) {
        return String.format("%s [Category: %s, Type: %s]",
                problem.getName(),
                problem.getCategory(),
                problem.getReturnType());
    }

    private String formatPractice(PracticeInfo practice) {
        return String.format("%s [Category: %s, Practice]",
                practice.getProblemInfo().getName(),
                practice.getProblemInfo().getCategory());
    }

    @FunctionalInterface
    public interface ItemFormatter<T> {
        String format(T item);
    }
}