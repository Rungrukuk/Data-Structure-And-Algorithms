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

    public String promptForName(String message, String errorMessage) {
        while (true) {
            uiManager.showPrompt(message);
            try {
                return inputHandler.readValidatedInput(() -> inputHandler.readName());
            } catch (Exception e) {
                uiManager.showError(errorMessage);
            }
        }
    }

    public Optional<String> promptForNameOptional(String message, String errorMessage, String exitKeyword) {
        uiManager.showPrompt(message);
        try {
            return Optional.of(inputHandler.readValidatedInput(() -> inputHandler.readName()));
        } catch (Exception e) {
            uiManager.showError(errorMessage);
            return Optional.empty();
        }
    }

    public String promptForReturnType(String message, String errorMessage) {
        while (true) {
            uiManager.showPrompt(message);
            try {
                return inputHandler.readValidatedInput(() -> inputHandler.readReturnType());
            } catch (Exception e) {
                uiManager.showError(errorMessage);
            }
        }
    }

    public String[] promptForProblemDetails() {
        uiManager.showSectionTitle("CREATE NEW PROBLEM");

        String name = promptForName("Enter problem name: ", ApplicationConstants.INVALID_PROBLEM_NAME);
        String category = promptForName("Enter problem category: ", ApplicationConstants.INVALID_CATEGORY_NAME);
        String returnType = promptForReturnType("Enter return type (e.g., Integer, List<String>): ",
                ApplicationConstants.INVALID_RETURN_TYPE);
        // TODO change return type to map
        return new String[] { name, category, returnType };
    }

    public Optional<String> promptForProblemNameOptional(String message) {
        return promptForNameOptional(message,
                ApplicationConstants.INVALID_PROBLEM_NAME,
                ApplicationConstants.RETURN_BACK);
    }

    // ========================= PRACTICE NAME SPECIFIC =========================

    public Optional<String> promptForPracticeNameOptional(String message) {
        return promptForNameOptional(message,
                ApplicationConstants.INVALID_PRACTICE_NAME,
                ApplicationConstants.RETURN_BACK);
    }

}