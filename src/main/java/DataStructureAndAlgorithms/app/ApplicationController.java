package DataStructureAndAlgorithms.app;

import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.flows.PracticeFlowHandler;
import DataStructureAndAlgorithms.domain.flows.ProblemFlowHandler;
import DataStructureAndAlgorithms.menus.MenuOption;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.MenuNavigator;

import java.util.Optional;

public class ApplicationController {
    private final UIManager ui;
    private final MenuNavigator menus;
    private final ProblemFlowHandler problemFlow;
    private final PracticeFlowHandler practiceFlow;

    public ApplicationController(
            UIManager ui,
            MenuNavigator menus,
            ProblemFlowHandler problemFlow,
            PracticeFlowHandler practiceFlow) {
        this.ui = ui;
        this.menus = menus;
        this.problemFlow = problemFlow;
        this.practiceFlow = practiceFlow;
    }

    public void start() {
        try {
            ui.showWelcome();
            runMainLoop();
        } finally {
            shutdown();
        }
    }

    private void runMainLoop() {
        boolean running = true;
        while (running) {
            MenuOption selectedOption = menus.showMainMenu();
            if (selectedOption == null) {
                ui.showError("Invalid selection. Please try again.");
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
                    ui.showError("Unknown option selected");
            }
        }
    }

    private void handleRunProblems() {
        boolean inProblemMenu = true;
        while (inProblemMenu) {
            MenuOption selectedOption = menus.showProblemMenu();
            if (selectedOption == null) {
                ui.showError("Invalid selection. Please try again.");
                continue;
            }

            switch (selectedOption.getKey()) {
                case LIST_ALL_PROBLEMS:
                    problemFlow.runFromAll();
                    break;
                case LIST_PROBLEMS_BY_CATEGORY:
                    problemFlow.runByCategory();
                    break;
                case RUN_PROBLEM_BY_NAME:
                    problemFlow.runByName();
                    break;
                case LIST_ALL_PRACTICES:
                    practiceFlow.runFromAll();
                    break;
                case LIST_PRACTICES_BY_CATEGORY:
                    practiceFlow.runByCategory();
                    break;
                case RUN_PRACTICE_BY_NAME:
                    practiceFlow.runByName();
                    break;
                case RETURN:
                    inProblemMenu = false;
                    break;
                default:
                    ui.showError("Unknown option selected");
            }
        }
    }

    private void handleCreateProblem() {
        problemFlow.createProblem();
    }

    private void handleManagePractices() {
        boolean inPracticeMenu = true;
        while (inPracticeMenu) {
            MenuOption selectedOption = menus.showManagePracticesMenu();
            if (selectedOption == null) {
                ui.showError("Invalid selection. Please try again.");
                continue;
            }

            switch (selectedOption.getKey()) {
                case CREATE_PRACTICE:
                    handleCreatePractice();
                    break;
                case RESET_PRACTICE:
                    practiceFlow.resetPractice();
                    break;
                case RETURN:
                    inPracticeMenu = false;
                    break;
                default:
                    ui.showError("Unknown option selected");
            }
        }
    }

    private void handleCreatePractice() {
        MenuOption selectedOption = menus.showCreatePracticeMenu();
        if (selectedOption == null) {
            ui.showError("Invalid selection.");
            ui.waitForEnter();
            return;
        }

        try {
            Optional<ProblemInfo> problemInfo = Optional.empty();

            switch (selectedOption.getKey()) {
                case LIST_ALL_PROBLEMS:
                    problemInfo = problemFlow.selectProblemForPractice();
                    break;
                case LIST_PROBLEMS_BY_CATEGORY:
                    problemInfo = problemFlow.selectProblemByCategoryForPractice();
                    break;
                case FIND_SPECIFIC_PROBLEM:
                    problemInfo = problemFlow.findProblemByNameForPractice();
                    break;
                case RETURN:
                    return;
                default:
                    ui.showError("Unknown option selected");
            }

            if (problemInfo.isPresent()) {
                ui.showInfo("Selected problem for practice creation: " + problemInfo.get());
                ui.showError("Implementation note: Need to complete practice creation logic");
                ui.waitForEnter();
            }
        } catch (Exception e) {
            ui.showError("Failed to create practice: " + e.getMessage());
            ui.waitForEnter();
        }
    }

    private void shutdown() {
        try {
            ui.showInfo("Exiting application...");
        } catch (Exception e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
    }
}