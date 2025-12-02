package DataStructureAndAlgorithms.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.ui.UIService_OLD;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

public class SelectionService {
    private final UIService_OLD uiService;
    private final InfoDisplayService infoDisplayService;

    public SelectionService(UIService_OLD uiService, InfoDisplayService infoDisplayService) {
        this.uiService = uiService;
        this.infoDisplayService = infoDisplayService;
    }

    // ========================= PROBLEM SELECTION =========================

    public Optional<ProblemInfo> selectProblemFromAll() {
        List<String> available = infoDisplayService.getAllProblemDisplayStrings();
        if (available.isEmpty()) {
            uiService.showErrorMessage("No problems available.");
            return Optional.empty();
        }

        String selected = uiService.selectFromList(available, "Select Problem to Run");
        if (selected == null)
            return Optional.empty();

        return infoDisplayService.findProblemByDisplayString(selected);
    }

    public Optional<ProblemInfo> selectProblemByCategory() {
        Map<String, List<String>> byCategory = infoDisplayService.getProblemDisplaysByCategory();
        if (byCategory.isEmpty()) {
            uiService.showErrorMessage("No problems available.");
            return Optional.empty();
        }

        String category = uiService.selectCategory(byCategory.keySet());
        if (category == null)
            return Optional.empty();

        List<String> problems = byCategory.get(category);
        String selected = uiService.selectFromList(problems, "Select Problem from " + category);
        if (selected == null)
            return Optional.empty();

        return infoDisplayService.findProblemByDisplayString(selected);
    }

    public Optional<ProblemInfo> selectProblemByName() {
        Optional<String> nameOptional = askProblemName();
        if (nameOptional.isEmpty())
            return Optional.empty();

        String name = nameOptional.get();
        List<ProblemInfo> variants = infoDisplayService.findProblemInfosByName(name);

        if (variants.isEmpty()) {
            uiService.showErrorMessage("No problems found with name: " + name);
            uiService.showSimilarSuggestions(name, infoDisplayService.getAllProblemDisplayStrings());
            return Optional.empty();
        }

        return selectVariant(variants, "problem");
    }

    // ========================= PRACTICE SELECTION =========================

    public Optional<PracticeInfo> selectPracticeFromAll() {
        List<String> available = infoDisplayService.getAllPracticeDisplayStrings();
        if (available.isEmpty()) {
            uiService.showErrorMessage("No practices available.");
            return Optional.empty();
        }

        String selected = uiService.selectFromList(available, "Select Practice to Run");
        if (selected == null)
            return Optional.empty();

        return infoDisplayService.findPracticeByDisplayString(selected);
    }

    public Optional<PracticeInfo> selectPracticeByCategory() {
        Map<String, List<String>> byCategory = infoDisplayService.getPracticeDisplaysByCategory();
        if (byCategory.isEmpty()) {
            uiService.showErrorMessage("No practices available.");
            return Optional.empty();
        }

        String category = uiService.selectCategory(byCategory.keySet());
        if (category == null)
            return Optional.empty();

        List<String> practices = byCategory.get(category);
        String selected = uiService.selectFromList(practices, "Select Practice from " + category);
        if (selected == null)
            return Optional.empty();

        return infoDisplayService.findPracticeByDisplayString(selected);
    }

    public Optional<PracticeInfo> selectPracticeByName() {
        Optional<String> nameOptional = askPracticeName();
        if (nameOptional.isEmpty())
            return Optional.empty();

        String name = nameOptional.get();
        List<PracticeInfo> variants = infoDisplayService.findPracticeInfosByName(name);

        if (variants.isEmpty()) {
            uiService.showErrorMessage("No practices found with name: " + name);
            return Optional.empty();
        }

        return selectVariant(variants, "practice");
    }

    // ========================= PROBLEM SELECTION FOR PRACTICE CREATION
    // =========================

    public Optional<ProblemInfo> selectProblemForPracticeCreation() {
        List<ProblemInfo> allProblems = infoDisplayService.getAllProblemInfos();
        if (allProblems.isEmpty()) {
            uiService.showErrorMessage("No problems found. Please create a problem first.");
            return Optional.empty();
        }

        List<String> problemDisplays = allProblems.stream()
                .map(problem -> problem.getName() + " [" + problem.getCategory() + "]")
                .toList();

        String selectedDisplay = uiService.selectFromList(problemDisplays, "Select Problem from List");
        if (selectedDisplay == null)
            return Optional.empty();

        return allProblems.stream()
                .filter(problem -> (problem.getName() + " [" + problem.getCategory() + "]").equals(selectedDisplay))
                .findFirst();
    }

    public Optional<ProblemInfo> selectProblemForPracticeByCategory() {
        Map<String, List<ProblemInfo>> problemsByCategory = infoDisplayService.getProblemsByCategory();
        if (problemsByCategory.isEmpty()) {
            uiService.showErrorMessage("No problems found. Please create a problem first.");
            return Optional.empty();
        }

        String selectedCategory = uiService.selectCategory(problemsByCategory.keySet());
        if (selectedCategory == null)
            return Optional.empty();

        List<ProblemInfo> categoryProblems = problemsByCategory.get(selectedCategory);
        if (categoryProblems == null || categoryProblems.isEmpty()) {
            uiService.showErrorMessage("No problems found in category: " + selectedCategory);
            return Optional.empty();
        }

        List<String> problemDisplays = categoryProblems.stream()
                .map(problem -> problem.getName() + " [" + problem.getCategory() + "]")
                .toList();

        String selectedDisplay = uiService.selectFromList(problemDisplays, "Select Problem from " + selectedCategory);
        if (selectedDisplay == null)
            return Optional.empty();

        return categoryProblems.stream()
                .filter(problem -> (problem.getName() + " [" + problem.getCategory() + "]").equals(selectedDisplay))
                .findFirst();
    }

    public Optional<ProblemInfo> selectProblemForPracticeByName() {
        uiService.showSectionTitle("FIND PROBLEM BY NAME");

        String problemName = uiService.getValidatedProblemName("Enter problem name: ");
        List<ProblemInfo> matchingProblems = infoDisplayService.findProblemInfosByName(problemName);

        if (matchingProblems.isEmpty()) {
            uiService.showErrorMessage("No problems found with name: " + problemName);
            uiService.showSimilarSuggestions(problemName, infoDisplayService.getAllProblemDisplayStrings());
            return Optional.empty();
        }

        if (matchingProblems.size() == 1) {
            ProblemInfo problem = matchingProblems.get(0);
            uiService.showSuccessMessage("Found: " + problem.getName() + " [" + problem.getCategory() + "]");
            return Optional.of(problem);
        }

        List<String> problemDisplays = matchingProblems.stream()
                .map(problem -> problem.getName() + " [" + problem.getCategory() + "]")
                .toList();

        String selectedDisplay = uiService.selectFromList(problemDisplays, "Multiple problems found");
        if (selectedDisplay == null)
            return Optional.empty();

        return matchingProblems.stream()
                .filter(problem -> (problem.getName() + " [" + problem.getCategory() + "]").equals(selectedDisplay))
                .findFirst();
    }

    // ========================= HELPER METHODS =========================

    private Optional<String> askProblemName() {
        return uiService.getValidatedOptionalInput(
                () -> uiService.getOptionalNameInputWithPrompt("Enter problem name: "),
                ApplicationConstants.INVALID_PROBLEM_NAME);
    }

    private Optional<String> askPracticeName() {
        return uiService.getValidatedOptionalInput(
                () -> uiService.getOptionalNameInputWithPrompt("Enter practice name: "),
                ApplicationConstants.INVALID_PRACTICE_NAME);
    }

    private <T> Optional<T> selectVariant(List<T> variants, String type) {
        if (variants.size() == 1) {
            return Optional.of(variants.get(0));
        }

        // For variants, we need to format them for display
        List<String> variantDisplays = variants.stream()
                .map(Object::toString)
                .toList();

        String selected = uiService.selectFromList(variantDisplays, "Select " + type + " variant");
        if (selected == null)
            return Optional.empty();

        // Find the variant that matches the display string
        for (int i = 0; i < variants.size(); i++) {
            if (variantDisplays.get(i).equals(selected)) {
                return Optional.of(variants.get(i));
            }
        }
        return Optional.empty();
    }
}