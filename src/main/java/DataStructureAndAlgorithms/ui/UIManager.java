package DataStructureAndAlgorithms.ui;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;

public class UIManager {
    private final InputHandler inputHandler;

    public UIManager(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    // ========================= DISPLAY METHODS =========================

    public void showWelcome() {
        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("       PROBLEM & PRACTICE RUNNER");
        System.out.println("=========================================\n");
    }

    public void showSectionTitle(String title) {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }

    public void showSuccess(String message) {
        System.out.println(ApplicationConstants.ANSI_GREEN + "✅ " + message + ApplicationConstants.ANSI_RESET);
    }

    public void showError(String message) {
        System.out.println(ApplicationConstants.ANSI_RED + "❌ " + message + ApplicationConstants.ANSI_RESET);
    }

    public void showInfo(String message) {
        System.out.println("💡 " + message);
    }

    public void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        inputHandler.waitForEnter();
    }

    // ========================= INPUT PROMPTS =========================

    public void showPrompt(String prompt) {
        System.out.print(prompt);
    }

    public void showMenuOptions(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    public void showSelectionPrompt(int maxOption) {
        System.out.print("Select an option (1-" + maxOption + "): ");
    }
}
