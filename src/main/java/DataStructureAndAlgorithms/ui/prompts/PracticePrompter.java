package DataStructureAndAlgorithms.ui.prompts;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Optional;

public class PracticePrompter {
    private final InputHandler inputHandler;
    private final UIManager uiManager;

    public PracticePrompter(InputHandler inputHandler, UIManager uiManager) {
        this.inputHandler = inputHandler;
        this.uiManager = uiManager;
    }

    public String promptForPracticeName(String message) {
        System.out.print(message);
        return uiManager.getValidatedInput(
                () -> inputHandler.readProblemName(),
                ApplicationConstants.INVALID_PRACTICE_NAME);
    }

    public Optional<String> promptForPracticeNameOptional(String message) {
        System.out.print(message);
        String input = inputHandler.readLine().trim();

        if (input.equals(ApplicationConstants.RETURN_BACK)) {
            return Optional.empty();
        }

        return Optional.of(uiManager.getValidatedInput(
                () -> {
                    if (input.isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be empty");
                    }
                    return inputHandler.readProblemName();
                },
                ApplicationConstants.INVALID_PRACTICE_NAME));
    }

    public void showProblemSelectionMenu() {
        uiManager.showSectionTitle("SELECT PROBLEM FOR PRACTICE");
    }
}