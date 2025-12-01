package DataStructureAndAlgorithms.ui.navigation;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.utils.helpers.TextHelper;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;
import java.util.Optional;

public class SelectionHandler {
    private final UIManager uiManager;

    public SelectionHandler(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    public <T> Optional<T> selectItem(List<T> items, String prompt, ItemFormatter<T> formatter) {
        if (items == null || items.isEmpty()) {
            uiManager.showError("No items available.");
            return Optional.empty();
        }

        List<String> displayStrings = items.stream()
                .map(formatter::format)
                .toList();

        String selectedDisplay = uiManager.getValidatedInput(
                () -> {
                    return uiManager.selectFromList(displayStrings, prompt);
                },
                "Invalid selection");

        if (selectedDisplay == null) {
            return Optional.empty();
        }

        for (int i = 0; i < items.size(); i++) {
            if (displayStrings.get(i).equals(selectedDisplay)) {
                return Optional.of(items.get(i));
            }
        }

        return Optional.empty();
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

        String selected = uiManager.getValidatedInput(
                () -> uiManager.selectFromCollection(categories, prompt),
                "Invalid category selection");

        return Optional.ofNullable(selected);
    }

    public void showSimilarSuggestions(String input, List<String> allOptions) {
        List<String> similar = findSimilarOptions(input, allOptions);
        if (!similar.isEmpty()) {
            uiManager.showInfo("💡 Did you mean:");
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