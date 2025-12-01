package DataStructureAndAlgorithms.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.creators.PracticeCreator_OLD;
import DataStructureAndAlgorithms.domain.creators.ProblemCreator_OLD;
import DataStructureAndAlgorithms.infrastructure.runner.ProblemRunner_OLD;
import DataStructureAndAlgorithms.menus.MenuOption;
import DataStructureAndAlgorithms.menus.MenuRegistry;
import DataStructureAndAlgorithms.services.*;
import DataStructureAndAlgorithms.ui.UIService_OLD;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Optional;

public class ApplicationManager_OLD {
    private static final Logger log = LogManager.getLogger(AppLauncher.class);

    private final UIService_OLD uiService;
    private final ProblemCreator_OLD problemCreator;
    private final SelectionService selectionService;
    private final RunnerService runnerService;
    private final PracticeCreationService practiceCreationService;

    public ApplicationManager_OLD(
            UIService_OLD uiService,
            ProblemCreator_OLD problemCreator,
            PracticeCreator_OLD practiceCreator,
            InfoDisplayService infoDisplayService,
            ProblemRunner_OLD problemRunner) {

        this.uiService = uiService;
        this.problemCreator = problemCreator;
        this.selectionService = new SelectionService(uiService, infoDisplayService);
        this.runnerService = new RunnerService(problemRunner, uiService);
        this.practiceCreationService = new PracticeCreationService(practiceCreator, selectionService, uiService);
    }

    public void start() {
        uiService.showWelcomeMessage();
        showMainMenu();
    }

    private void showMainMenu() {
        while (true) {
            String selection = uiService.getValidatedInput(
                    () -> uiService.showMenuAndGetSelection(MenuRegistry.getLabels(MenuRegistry.MAIN_MENU), "MAIN MENU"),
                    ApplicationConstants.INCORRECT_OPTION);

            MenuOption option = MenuRegistry.findByLabel(MenuRegistry.MAIN_MENU, selection);
            if (option == null)
                continue;

            switch (option.getKey()) {
                case RUN_PROBLEMS -> showProblemMenu();
                case CREATE_PROBLEM -> handleCreateProblemMenu();
                case MANAGE_PRACTICES -> handleManagePracticesMenu();
                case EXIT -> {
                    uiService.shutdown();
                    return;
                }
                default -> uiService.showErrorMessage("Unknown option");
            }
        }
    }

    private void showProblemMenu() {
        while (true) {
            String selection = uiService.getValidatedInput(
                    () -> uiService.showMenuAndGetSelection(MenuRegistry.getLabels(MenuRegistry.PROBLEM_MENU),
                            "PROBLEM MENU"),
                    ApplicationConstants.INCORRECT_OPTION);

            MenuOption option = MenuRegistry.findByLabel(MenuRegistry.PROBLEM_MENU, selection);
            if (option == null)
                continue;

            switch (option.getKey()) {
                case LIST_ALL_PROBLEMS -> runSelectedProblem(selectionService.selectProblemFromAll());
                case LIST_PROBLEMS_BY_CATEGORY -> runSelectedProblem(selectionService.selectProblemByCategory());
                case RUN_PROBLEM_BY_NAME -> runSelectedProblem(selectionService.selectProblemByName());
                case LIST_ALL_PRACTICES -> runSelectedPractice(selectionService.selectPracticeFromAll());
                case LIST_PRACTICES_BY_CATEGORY -> runSelectedPractice(selectionService.selectPracticeByCategory());
                case RUN_PRACTICE_BY_NAME -> runSelectedPractice(selectionService.selectPracticeByName());
                case RETURN -> {
                    return;
                }
                default -> uiService.showErrorMessage("Unknown option");
            }
        }
    }

    private void handleCreateProblemMenu() {
        uiService.showSectionTitle("CREATE NEW PROBLEM");

        String problemName = uiService.getValidatedProblemName("Enter problem name: ");
        String category = uiService.getValidatedCategory("Enter problem category: ");
        String returnType = uiService.getValidatedReturnType("Enter return type (e.g., Integer, List<String>): ");

        ProblemInfo info = new ProblemInfo(problemName, category, null, returnType, null);

        try {
            problemCreator.createProblem(info);
            uiService.showSuccessMessage("Problem created successfully!");
        } catch (Exception e) {
            uiService.showErrorMessage("Error creating problem", e.getMessage());
            log.error("Error creating problem", e);
        }

        uiService.waitForEnter();
    }

    private void handleManagePracticesMenu() {
        while (true) {
            String selection = uiService.getValidatedInput(
                    () -> uiService.showMenuAndGetSelection(MenuRegistry.getLabels(MenuRegistry.MANAGE_PRACTICES_MENU),
                            "MANAGE PRACTICES"),
                    ApplicationConstants.INCORRECT_OPTION);

            MenuOption option = MenuRegistry.findByLabel(MenuRegistry.MANAGE_PRACTICES_MENU, selection);
            if (option == null)
                continue;

            switch (option.getKey()) {
                case CREATE_PRACTICE -> practiceCreationService.showCreatePracticeMenu();
                case RESET_PRACTICE -> handleResetPracticeMenu();
                case RETURN -> {
                    return;
                }
                default -> uiService.showErrorMessage("Unknown option");
            }
        }
    }

    private void handleResetPracticeMenu() {
        uiService.showSectionTitle("RESET PRACTICE");
        uiService.showInfoMessage("Reset practice functionality will be implemented here.");
        uiService.waitForEnter();
    }

    private void runSelectedProblem(Optional<ProblemInfo> problemInfo) {
        problemInfo.ifPresentOrElse(
                info -> runnerService.runProblemByName(info.getUniqueId()),
                () -> uiService.showErrorMessage("No problem selected"));
    }

    private void runSelectedPractice(Optional<PracticeInfo> practiceInfo) {
        practiceInfo.ifPresentOrElse(
                info -> runnerService.runPracticeByName(info.getUniqueId()),
                () -> uiService.showErrorMessage("No practice selected"));
    }
}