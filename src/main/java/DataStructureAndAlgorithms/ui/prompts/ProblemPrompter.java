package DataStructureAndAlgorithms.ui.prompts;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Optional;

public class ProblemPrompter {
    private final InputHandler inputHandler;
    private final UIManager uiManager;

    public ProblemPrompter(InputHandler inputHandler, UIManager uiManager) {
        this.inputHandler = inputHandler;
        this.uiManager = uiManager;
    }

    public String promptForProblemName(String message) {
        System.out.print(message);
        return uiManager.getValidatedInput(
                () -> inputHandler.readProblemName(),
                ApplicationConstants.INVALID_PROBLEM_NAME);
    }

    public Optional<String> promptForProblemNameOptional(String message) {
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
                ApplicationConstants.INVALID_PROBLEM_NAME));
    }

    public String promptForCategory(String message) {
        System.out.print(message);
        return uiManager.getValidatedInput(
                () -> inputHandler.readCategory(),
                ApplicationConstants.INVALID_CATEGORY_NAME);
    }

    public String promptForReturnType(String message) {
        System.out.print(message);
        return uiManager.getValidatedInput(
                () -> inputHandler.readReturnType(),
                ApplicationConstants.INVALID_RETURN_TYPE);
    }

    public String[] promptForProblemDetails() {
        uiManager.showSectionTitle("CREATE NEW PROBLEM");

        String name = promptForProblemName("Enter problem name: ");
        String category = promptForCategory("Enter problem category: ");
        String returnType = promptForReturnType("Enter return type (e.g., Integer, List<String>): ");

        return new String[] { name, category, returnType };
    }
}