package DataStructureAndAlgorithms.app;

import DataStructureAndAlgorithms.core.exceptions.ValidationException;
import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.problems.ProblemOrchestrator;
import DataStructureAndAlgorithms.domain.practices.PracticeOrchestrator;
import DataStructureAndAlgorithms.menus.MenuOption;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.MenuNavigator;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.prompts.Prompter;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ApplicationController {
    private final UIManager uiManager;
    private final MenuNavigator menuNavigator;
    private final SelectionHandler selectionHandler;
    private final Prompter prompter;
    private final ProblemOrchestrator problemOrchestrator;
    private final PracticeOrchestrator practiceOrchestrator;

    public ApplicationController(UIManager uiManager,
            MenuNavigator menuNavigator,
            SelectionHandler selectionHandler,
            Prompter prompter,
            ProblemOrchestrator problemOrchestrator,
            PracticeOrchestrator practiceOrchestrator) {
        this.uiManager = uiManager;
        this.menuNavigator = menuNavigator;
        this.selectionHandler = selectionHandler;
        this.prompter = prompter;
        this.problemOrchestrator = problemOrchestrator;
        this.practiceOrchestrator = practiceOrchestrator;
    }

    public void start() {
        try {
            uiManager.showWelcome();
            runMainLoop();
        } finally {
            shutdown();
        }
    }

    private void runMainLoop() {
        boolean running = true;

        while (running) {
            MenuOption selectedOption = menuNavigator.showMainMenu();

            if (selectedOption == null) {
                uiManager.showError("Invalid selection. Please try again.");
                continue;
            }

            switch (selectedOption.getKey()) {
                case RUN_PROBLEMS:
                    handleRunProblems();
                    break;
                case CREATE_PROBLEM:
                    handleCreateProblem();
                    break;
                case MANAGE_PRACTICES:
                    handleManagePractices();
                    break;
                case EXIT:
                    running = false;
                    break;
                default:
                    uiManager.showError("Unknown option selected");
            }
        }
    }

    private void handleRunProblems() {
        boolean inProblemMenu = true;

        while (inProblemMenu) {
            MenuOption selectedOption = menuNavigator.showProblemMenu();

            if (selectedOption == null) {
                uiManager.showError("Invalid selection. Please try again.");
                continue;
            }

            switch (selectedOption.getKey()) {
                case LIST_ALL_PROBLEMS:
                    runSelectedProblemFromAll();
                    break;
                case LIST_PROBLEMS_BY_CATEGORY:
                    runSelectedProblemByCategory();
                    break;
                case RUN_PROBLEM_BY_NAME:
                    runSelectedProblemByName();
                    break;
                case LIST_ALL_PRACTICES:
                    runSelectedPracticeFromAll();
                    break;
                case LIST_PRACTICES_BY_CATEGORY:
                    runSelectedPracticeByCategory();
                    break;
                case RUN_PRACTICE_BY_NAME:
                    runSelectedPracticeByName();
                    break;
                case RETURN:
                    inProblemMenu = false;
                    break;
                default:
                    uiManager.showError("Unknown option selected");
            }
        }
    }

    private void handleCreateProblem() {
        uiManager.showSectionTitle("CREATE PROBLEM");
        try {
            Optional<String> name = Optional.empty();
            Optional<String> category = Optional.empty();
            Optional<String> returnType = Optional.empty();
            while (name.isEmpty()) {
                name = prompter.promptForProblemNameOptional();
            }
            while (category.isEmpty()) {
                category = prompter.promptForCategoryNameOptional();
            }
            while (returnType.isEmpty()) {
                returnType = prompter.promptForReturnTypeOptional();
            }

            if (problemOrchestrator.problemExists(name.get(), category.get())) {
                uiManager.showError("A problem with this name and category already exists.");
                uiManager.waitForEnter();
                return;
            }

            problemOrchestrator.createNewProblem(name.get(), category.get(), returnType.get());
            uiManager.showSuccess("Problem created successfully!");

        } catch (ValidationException e) {
            uiManager.showError("Validation error: " + e.getMessage());
        } catch (Exception e) {
            uiManager.showError("Failed to create problem: " + e.getMessage());
        }
        uiManager.waitForEnter();
    }

    private void handleManagePractices() {
        boolean inPracticeMenu = true;

        while (inPracticeMenu) {
            MenuOption selectedOption = menuNavigator.showManagePracticesMenu();

            if (selectedOption == null) {
                uiManager.showError("Invalid selection. Please try again.");
                continue;
            }

            switch (selectedOption.getKey()) {
                case CREATE_PRACTICE:
                    handleCreatePractice();
                    break;
                case RESET_PRACTICE:
                    handleResetPractice();
                    break;
                case RETURN:
                    inPracticeMenu = false;
                    break;
                default:
                    uiManager.showError("Unknown option selected");
            }
        }
    }

    private void handleCreatePractice() {
        MenuOption selectedOption = menuNavigator.showCreatePracticeMenu();

        if (selectedOption == null) {
            uiManager.showError("Invalid selection.");
            uiManager.waitForEnter();
            return;
        }

        try {
            switch (selectedOption.getKey()) {
                case LIST_ALL_PROBLEMS:
                    createPracticeFromAllProblems();
                    break;
                case LIST_PROBLEMS_BY_CATEGORY:
                    createPracticeByCategory();
                    break;
                case FIND_SPECIFIC_PROBLEM:
                    createPracticeByName();
                    break;
                case RETURN:
                    return;
                default:
                    uiManager.showError("Unknown option selected");
            }
        } catch (Exception e) {
            uiManager.showError("Failed to create practice: " + e.getMessage());
            uiManager.waitForEnter();
        }
    }

    private void handleResetPractice() {
        uiManager.showSectionTitle("RESET PRACTICE");
        uiManager.showInfo("Reset practice functionality will be implemented here.");
        uiManager.waitForEnter();
    }

    // ========================= PROBLEM RUNNING METHODS =========================

    private void runSelectedProblemFromAll() {
        List<ProblemInfo> problemDisplays = problemOrchestrator.listAllProblems();

        if (problemDisplays.isEmpty()) {
            uiManager.showError("No problems available.");
            uiManager.waitForEnter();
            return;
        }

        Optional<ProblemInfo> selectedDisplay = selectionHandler.selectProblem(
                problemDisplays,
                "Select Problem to Run");

        selectedDisplay.ifPresent(display -> {
            problemOrchestrator.runProblem(display)
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run problem: " + display);
                                uiManager.waitForEnter();
                            });
        });
    }

    private void runSelectedProblemByCategory() {
        Map<String, List<ProblemInfo>> problemsByCategory = problemOrchestrator.listProblemsByCategory();

        if (problemsByCategory.isEmpty()) {
            uiManager.showError("No problems available.");
            uiManager.waitForEnter();
            return;
        }

        List<String> categories = problemOrchestrator.getAllCategories();
        Optional<String> selectedCategory = selectionHandler.selectCategory(categories, "Select Category");

        if (selectedCategory.isEmpty()) {
            return;
        }

        List<ProblemInfo> problemsInCategory = problemsByCategory.get(selectedCategory.get());
        if (problemsInCategory == null || problemsInCategory.isEmpty()) {
            uiManager.showError("No problems found in category: " + selectedCategory.get());
            uiManager.waitForEnter();
            return;
        }

        Optional<ProblemInfo> selectedDisplay = selectionHandler.selectProblem(
                problemsInCategory,
                "Select Problem to Run");

        selectedDisplay.ifPresent(display -> {
            problemOrchestrator.runProblem(display)
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run problem: " + display);
                                uiManager.waitForEnter();
                            });
        });
    }

    private void runSelectedProblemByName() {
        Optional<String> nameOptional = prompter.promptForProblemNameOptional();

        if (nameOptional.isEmpty() || nameOptional.get().equals(ApplicationConstants.RETURN_BACK)) {
            return;
        }

        String name = nameOptional.get();
        List<ProblemInfo> variants = problemOrchestrator.listProblemVariants(name);

        if (variants.isEmpty()) {
            uiManager.showError(ApplicationConstants.DIDNOT_FIND_PROBLEM_NAME + name);
            selectionHandler.showSimilarSuggestions(name, problemOrchestrator.listAllProblems());
            uiManager.waitForEnter();
            return;
        }

        if (variants.size() == 1) {
            problemOrchestrator.runProblem(variants.get(0))
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run problem: " + variants.get(0));
                                uiManager.waitForEnter();
                            });
            return;
        }

        Optional<ProblemInfo> selectedProblem = selectionHandler.selectProblem(
                variants, "Select Problem Variant");

        selectedProblem.ifPresent(problem -> {
            problemOrchestrator.runProblem(problem)
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run problem: " + problem);
                                uiManager.waitForEnter();
                            });
        });
    }

    // ========================= PRACTICE RUNNING METHODS =========================

    private void runSelectedPracticeFromAll() {
        List<PracticeInfo> practices = practiceOrchestrator.listAllPractices();

        if (practices.isEmpty()) {
            uiManager.showError("No practices available.");
            uiManager.waitForEnter();
            return;
        }

        Optional<PracticeInfo> selectedDisplay = selectionHandler.selectPractice(
                practices,
                "Select Practice to Run");

        selectedDisplay.ifPresent(display -> {
            practiceOrchestrator.runPractice(display)
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run practice: " + display);
                                uiManager.waitForEnter();
                            });
        });
    }

    private void runSelectedPracticeByCategory() {
        Map<String, List<PracticeInfo>> practicesByCategory = practiceOrchestrator.listPracticesByCategory();

        if (practicesByCategory.isEmpty()) {
            uiManager.showError("No practices available.");
            uiManager.waitForEnter();
            return;
        }

        List<String> categories = practiceOrchestrator.getAllCategories();
        Optional<String> selectedCategory = selectionHandler.selectCategory(categories, "Select Category");

        if (selectedCategory.isEmpty()) {
            return;
        }

        List<PracticeInfo> practicesInCategory = practicesByCategory.get(selectedCategory.get());
        if (practicesInCategory == null || practicesInCategory.isEmpty()) {
            uiManager.showError("No practices found in category: " + selectedCategory.get());
            uiManager.waitForEnter();
            return;
        }

        Optional<PracticeInfo> selectedDisplay = selectionHandler.selectPractice(
                practicesInCategory,
                "Select Practice from " + selectedCategory.get());

        selectedDisplay.ifPresent(practice -> {
            practiceOrchestrator.runPractice(practice)
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run practice: " + practice);
                                uiManager.waitForEnter();
                            });
        });
    }

    private void runSelectedPracticeByName() {
        Optional<String> nameOptional = prompter.promptForPracticeNameOptional();

        if (nameOptional.isEmpty() || nameOptional.get().equals(ApplicationConstants.RETURN_BACK)) {
            return;
        }

        String name = nameOptional.get();
        List<PracticeInfo> variants = practiceOrchestrator.listPracticeVariants(name);

        if (variants.isEmpty()) {
            uiManager.showError(ApplicationConstants.DIDNOT_FIND_PRACTICE_NAME + name);
            uiManager.waitForEnter();
            return;
        }

        if (variants.size() == 1) {
            practiceOrchestrator.runPractice(variants.get(0))
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run practice: " + variants.get(0));
                                uiManager.waitForEnter();
                            });
            return;
        }

        Optional<PracticeInfo> selectedDisplay = selectionHandler.selectPractice(
                variants,
                "Select Practice Variant");

        selectedDisplay.ifPresent(practice -> {
            practiceOrchestrator.runPractice(practice)
                    .ifPresentOrElse(
                            result -> {
                                System.out.println(result);
                                uiManager.waitForEnter();
                            },
                            () -> {
                                uiManager.showError("Failed to run practice: " + practice);
                                uiManager.waitForEnter();
                            });
        });
    }

    // ========================= PRACTICE CREATION METHODS =========================

    private void createPracticeFromAllProblems() {
        List<ProblemInfo> problemDisplays = problemOrchestrator.listAllProblems();

        if (problemDisplays.isEmpty()) {
            uiManager.showError("No problems found. Please create a problem first.");
            uiManager.waitForEnter();
            return;
        }

        Optional<ProblemInfo> selectedDisplay = selectionHandler.selectProblem(
                problemDisplays,
                "Select Problem to Create Practice For");

        selectedDisplay.ifPresent(display -> {
            uiManager.showError("Implementation note: Need to map display string back to ProblemInfo");
            uiManager.waitForEnter();
        });
    }

    private void createPracticeByCategory() {
        Map<String, List<ProblemInfo>> problemsByCategory = problemOrchestrator.listProblemsByCategory();

        if (problemsByCategory.isEmpty()) {
            uiManager.showError("No problems found. Please create a problem first.");
            uiManager.waitForEnter();
            return;
        }

        List<String> categories = problemOrchestrator.getAllCategories();
        Optional<String> selectedCategory = selectionHandler.selectCategory(categories, "Select Category");

        if (selectedCategory.isEmpty()) {
            return;
        }

        List<ProblemInfo> problemsInCategory = problemsByCategory.get(selectedCategory.get());
        if (problemsInCategory == null || problemsInCategory.isEmpty()) {
            uiManager.showError("No problems found in category: " + selectedCategory.get());
            uiManager.waitForEnter();
            return;
        }

        Optional<ProblemInfo> selectedDisplay = selectionHandler.selectProblem(
                problemsInCategory,
                "Select Problem from " + selectedCategory.get());

        selectedDisplay.ifPresent(problem -> {
            uiManager.showError("Implementation note: Need to map display string back to ProblemInfo");
            uiManager.waitForEnter();
        });
    }

    private void createPracticeByName() {
        Optional<String> nameOptional = prompter.promptForProblemNameOptional();

        if (nameOptional.isEmpty()) {
            return;
        }

        String name = nameOptional.get();
        List<ProblemInfo> variants = problemOrchestrator.listProblemVariants(name);

        if (variants.isEmpty()) {
            uiManager.showError("No problems found with name: " + name);
            selectionHandler.showSimilarSuggestions(name, problemOrchestrator.listAllProblems());
            uiManager.waitForEnter();
            return;
        }

        if (variants.size() == 1) {
            uiManager.showInfo("Found: " + variants.get(0));
            uiManager.showError("Implementation note: Need to map display string back to ProblemInfo");
            uiManager.waitForEnter();
            return;
        }

        Optional<ProblemInfo> selectedDisplay = selectionHandler.selectProblem(
                variants,
                "Multiple problems found");

        selectedDisplay.ifPresent(display -> {
            uiManager.showError("Implementation note: Need to map display string back to ProblemInfo");
            uiManager.waitForEnter();
        });
    }

    private void shutdown() {
        try {
            uiManager.showInfo("Exiting application...");
            // TODO close the input handler
        } catch (Exception e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
    }
}