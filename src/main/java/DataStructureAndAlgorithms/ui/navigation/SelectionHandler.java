package DataStructureAndAlgorithms.ui.navigation;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.prompts.Prompter;
import DataStructureAndAlgorithms.utils.helpers.TextHelper;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SelectionHandler {
    private final Prompter prompter;
    private final UIManager uiManager;

    public SelectionHandler(Prompter prompter, UIManager uiManager) {
        this.uiManager = uiManager;
        this.prompter = prompter;
    }

    // ========================= MENU SELECTION =========================

    public Optional<String> selectFromMenu(List<String> options, String title) {
        if (options == null || options.isEmpty()) {
            uiManager.showError("No options available.");
            return Optional.empty();
        }

        return selectItem(options, title, s -> s);
    }

    public Optional<String> selectFromOptions(List<String> options, String prompt) {
        while (true) {
            try {
                uiManager.showMenuOptions(options);
                uiManager.showSelectionPrompt(options.size());
                Optional<Integer> choice = prompter.promptForChoiceOptional(prompt);

                if (choice.isEmpty() || choice.get() < 1 || choice.get() > options.size()) {
                    uiManager.showError("Invalid selection. Please enter a number between 1 and " + options.size());
                    continue;
                }

                return Optional.of(options.get(choice.get() - 1));
            } catch (Exception e) {
                uiManager.showError(e.getMessage());
            }
        }
    }

    // ========================= GENERIC ITEM SELECTION =========================

    public <T> Optional<T> selectItem(List<T> items, String prompt, ItemFormatter<T> formatter) {
        if (items == null || items.isEmpty()) {
            uiManager.showError("No items available.");
            return Optional.empty();
        }

        List<String> displayStrings = items.stream()
                .map(formatter::format)
                .toList();

        uiManager.showSectionTitle(prompt);

        Optional<String> selectedDisplay = selectFromOptions(displayStrings, prompt);

        return selectedDisplay.flatMap(display -> {
            for (int i = 0; i < items.size(); i++) {
                if (displayStrings.get(i).equals(display)) {
                    return Optional.of(items.get(i));
                }
            }
            return Optional.empty();
        });
    }

    public <T> Optional<T> selectFromCategory(
            Map<String, List<T>> itemsByCategory,
            Function<T, String> itemDisplayFormatter,
            String prompt) {

        List<String> categories = itemsByCategory.keySet().stream()
                .sorted()
                .toList();

        if (categories.isEmpty()) {
            uiManager.showError("No categories available.");
            return Optional.empty();
        }

        Optional<String> selectedCategory = selectFromOptions(categories, "Select Category");
        if (selectedCategory.isEmpty()) {
            return Optional.empty();
        }

        List<T> itemsInCategory = itemsByCategory.get(selectedCategory.get());
        if (itemsInCategory == null || itemsInCategory.isEmpty()) {
            uiManager.showError("No items found in category: " + selectedCategory.get());
            return Optional.empty();
        }

        return selectItem(itemsInCategory, prompt + " from " + selectedCategory.get(),
                item -> itemDisplayFormatter.apply(item));
    }

    // ========================= GENERIC SUGGESTIONS =========================

    public <T> List<T> showSimilarSuggestions(String input, List<T> allItems,
            Function<T, String> nameExtractor,
            ItemFormatter<T> formatter) {

        List<T> similarItems = findSimilarItems(input, allItems, nameExtractor);

        if (!similarItems.isEmpty()) {
            uiManager.showInfo("Found Similar Suggestions");
            uiManager.showMenuOptions(similarItems.stream().map(formatter::format).toList());
        }
        return similarItems;
    }

    private <T> List<T> findSimilarItems(String input, List<T> allItems,
            Function<T, String> nameExtractor) {
        String normalizedInput = normalizeString(input);

        return allItems.stream()
                .filter(item -> {
                    String itemName = nameExtractor.apply(item);
                    String normalizedItemName = normalizeString(itemName);

                    if (normalizedItemName.equals(normalizedInput)) {
                        return true;
                    }

                    if (normalizedItemName.contains(normalizedInput) ||
                            normalizedInput.contains(normalizedItemName)) {
                        return true;
                    }

                    double similarity = TextHelper.calculateSimilarity(
                            normalizedInput, normalizedItemName);
                    return similarity > ApplicationConstants.MINIMUM_SIMILARITY_VALUE;
                })
                .limit(5)
                .toList();
    }

    private String normalizeString(String input) {
        return input.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    // ========================= FORMATTERS =========================

    public String formatProblem(ProblemInfo problem) {
        return String.format("%s [Category: %s]",
                problem.getName(),
                problem.getCategory());
    }

    public String formatPractice(PracticeInfo practice) {
        ProblemInfo problem = practice.getProblemInfo();
        return String.format("%s [Category: %s]",
                problem.getName(),
                problem.getCategory());
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
        return selectFromOptions(categories, prompt);
    }

    @FunctionalInterface
    public interface ItemFormatter<T> {
        String format(T item);
    }
}