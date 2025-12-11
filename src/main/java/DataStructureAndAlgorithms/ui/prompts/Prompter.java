package DataStructureAndAlgorithms.ui.prompts;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.ui.UIManager;

import java.util.Optional;

public class Prompter {
    private final InputHandler inputHandler;
    private final UIManager uiManager;

    public Prompter(InputHandler inputHandler, UIManager uiManager) {
        this.inputHandler = inputHandler;
        this.uiManager = uiManager;
    }

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
        uiManager.showPrompt("Enter return type {e.g.,Integer, List<String>}(0 to return): ");
        try {
            String input = inputHandler.readReturnType();
            return Optional.of(input);
        } catch (Exception e) {
            uiManager.showError("Invalid return type");
            return Optional.empty();
        }
    }

    public Optional<Boolean> promptForConfirmationOptional(String message) {
        uiManager.showPrompt(message + " (Y/N): ");
        try {
            return Optional.of(inputHandler.readConfirmation());
        } catch (Exception e) {
            uiManager.showError("Error reading input: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> promptForProblemNameOptional() {
        return promptForName("Enter a problem name (0 to return): ",
                "Invalid problem name");
    }

    public Optional<String> promptForPracticeNameOptional() {
        return promptForName("Enter a practice name (0 to return): ",
                "Invalid practice name");
    }

    public Optional<String> promptForCategoryNameOptional() {
        return promptForName("Enter a category name (0 to return): ", "Invalid category name");
    }

    public Optional<String> promptForDifficultyOptional() {
        uiManager.showPrompt("Enter difficulty {H (Hard), M (Medium), E (Easy)}(0 to return): ");
        try {
            return Optional.of(inputHandler.readDifficulty());
        } catch (Exception e) {
            uiManager.showError("Error reading input: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Integer> promptForChoiceOptional() {
        return Optional.of(inputHandler.readInt());
    }

}
