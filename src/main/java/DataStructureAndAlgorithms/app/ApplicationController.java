package DataStructureAndAlgorithms.app;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
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
                case FIND_SPECIFIC_PROBLEM:
                    problemFlow.runByName();
                    break;
                case LIST_ALL_PRACTICES:
                    practiceFlow.runFromAll();
                    break;
                case LIST_PRACTICES_BY_CATEGORY:
                    practiceFlow.runByCategory();
                    break;
                case FIND_SPECIFIC_PRACTICE:
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
                    handleResetPractice();
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

        Optional<ProblemInfo> problemInfoOptional = Optional.empty();

        switch (selectedOption.getKey()) {
            case LIST_ALL_PROBLEMS:
                problemInfoOptional = problemFlow.listAndSelectFromAll();
                break;
            case LIST_PROBLEMS_BY_CATEGORY:
                problemInfoOptional = problemFlow.listAndSelectByCategory();
                break;
            case FIND_SPECIFIC_PROBLEM:
                problemInfoOptional = problemFlow.listAndSelectByName();
                break;
            case RETURN:
                return;
            default:
                ui.showError("Unknown option selected");
        }
        problemInfoOptional.ifPresentOrElse(problemInfo -> {
            practiceFlow.createPractice(problemInfo);
        }, () -> {
            ui.showError("Could not find the specified problem");
        });
        ui.waitForEnter();
    }

    private void handleResetPractice() {
        MenuOption selectedOption = menus.showResetPracticeMenu();
        if (selectedOption == null) {
            ui.showError("Invalid selection.");
            ui.waitForEnter();
            return;
        }
        Optional<PracticeInfo> practiceInfoOptional = Optional.empty();

        switch (selectedOption.getKey()) {
            case LIST_ALL_PRACTICES:
                practiceInfoOptional = practiceFlow.listAndSelectFromAll();
                break;
            case LIST_PRACTICES_BY_CATEGORY:
                practiceInfoOptional = practiceFlow.listAndSelectByCategory();
                break;
            case FIND_SPECIFIC_PRACTICE:
                practiceInfoOptional = practiceFlow.listAndSelectByName();
                break;
            case RETURN:
                return;
            default:
                ui.showError("Unknown option selected");
        }

        practiceInfoOptional.ifPresentOrElse(practiceInfo -> {
            practiceFlow.resetPractice(practiceInfo);
        }, () -> {
            ui.showError("Could not find the specified practice");
        });
        ui.waitForEnter();

    }

    private void shutdown() {
        try {
            // TODO shutdown input handler
            ui.showInfo("Exiting application...");
        } catch (Exception e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
    }
}