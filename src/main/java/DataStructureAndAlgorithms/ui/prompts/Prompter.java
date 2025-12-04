package DataStructureAndAlgorithms.ui.prompts;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Optional;

public class Prompter {
    private final InputHandler inputHandler;
    private final UIManager uiManager;

    public Prompter(InputHandler inputHandler, UIManager uiManager) {
        this.inputHandler = inputHandler;
        this.uiManager = uiManager;
    }

    // ========================= PROBLEM METHODS =========================

    private Optional<String> promptForName(String message, String errorMessage) {
        uiManager.showPrompt(message);
        try {
            String input = inputHandler.readName();
            return Optional.of(input);
        } catch (Exception e) {
            uiManager.showError(errorMessage);
            return Optional.empty();
        }
    }

    public Optional<String> promptForReturnTypeOptional() {
        uiManager.showPrompt(ApplicationConstants.ENTER_RETURN_TYPE);
        try {
            String input = inputHandler.readReturnType();
            if (input.equals(ApplicationConstants.RETURN_BACK)) {
                return Optional.empty();
            }
            return Optional.of(input);
        } catch (Exception e) {
            uiManager.showError(ApplicationConstants.INVALID_RETURN_TYPE);
            return Optional.empty();
        }
    }

    public Optional<String> promptForProblemNameOptional() {
        return promptForName(ApplicationConstants.ENTER_PROBLEM_NAME,
                ApplicationConstants.INVALID_PROBLEM_NAME);
    }

    public Optional<String> promptForPracticeNameOptional() {
        return promptForName(ApplicationConstants.ENTER_PRACTICE_NAME,
                ApplicationConstants.INVALID_PRACTICE_NAME);
    }

    public Optional<String> promptForCategoryNameOptional() {
        return promptForName(ApplicationConstants.ENTER_CATEGORY_NAME, ApplicationConstants.INVALID_CATEGORY_NAME);
    }

    public Optional<Integer> promptForChoiceOptional(String prompt) {
        return Optional.of(inputHandler.readInt());
    }

}
