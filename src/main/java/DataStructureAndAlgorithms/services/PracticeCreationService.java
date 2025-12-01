package DataStructureAndAlgorithms.services;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.creators.PracticeCreator_OLD;
import DataStructureAndAlgorithms.menus.MenuOption;
import DataStructureAndAlgorithms.menus.MenuRegistry;
import DataStructureAndAlgorithms.ui.UIService_OLD;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Optional;

public class PracticeCreationService {
    private final PracticeCreator_OLD practiceCreator;
    private final SelectionService selectionService;
    private final UIService_OLD uiService;

    public PracticeCreationService(PracticeCreator_OLD practiceCreator, SelectionService selectionService,
            UIService_OLD uiService) {
        this.practiceCreator = practiceCreator;
        this.selectionService = selectionService;
        this.uiService = uiService;
    }

    public void showCreatePracticeMenu() {
        uiService.showSectionTitle("CREATE PRACTICE FOR EXISTING PROBLEM");

        String selection = uiService.getValidatedInput(
                () -> uiService.showMenuAndGetSelection(MenuRegistry.getLabels(MenuRegistry.CREATE_PRACTICE_MENU),
                        "CREATE PRACTICE"),
                ApplicationConstants.INCORRECT_OPTION);

        MenuOption option = MenuRegistry.findByLabel(MenuRegistry.CREATE_PRACTICE_MENU, selection);
        if (option == null) {
            uiService.showErrorMessage("Invalid selection");
            return;
        }

        Optional<ProblemInfo> chosenProblem = switch (option.getKey()) {
            case LIST_ALL_PROBLEMS -> selectionService.selectProblemForPracticeCreation();
            case LIST_PROBLEMS_BY_CATEGORY -> selectionService.selectProblemForPracticeByCategory();
            case FIND_SPECIFIC_PROBLEM -> selectionService.selectProblemForPracticeByName();
            case RETURN -> {
                uiService.showInfoMessage("Returning...");
                yield Optional.empty();
            }
            default -> {
                uiService.showErrorMessage("Unknown option selected");
                yield Optional.empty();
            }
        };

        if (chosenProblem.isEmpty()) {
            uiService.waitForEnter();
            return;
        }

        createPracticeForProblem(chosenProblem.get());
    }

    private void createPracticeForProblem(ProblemInfo problemInfo) {
        Optional<ProblemInfo> existingProblem = practiceCreator.getProblemIfExists(problemInfo.getUniqueId());

        if (existingProblem.isEmpty()) {
            uiService.showErrorMessage(ApplicationConstants.DIDNOT_FIND_PRACTICE_NAME + problemInfo.getName());
            uiService.waitForEnter();
            return;
        }

        ProblemInfo info = existingProblem.get();
        PracticeInfo practiceInfo = new PracticeInfo(info, null, null);

        try {
            practiceCreator.createPractice(practiceInfo);
            uiService.showSuccessMessage("Practice created successfully for: " + info.getName());
        } catch (Exception e) {
            uiService.showErrorMessage("Failed to create practice", e.getMessage());
        }

        uiService.waitForEnter();
    }
}