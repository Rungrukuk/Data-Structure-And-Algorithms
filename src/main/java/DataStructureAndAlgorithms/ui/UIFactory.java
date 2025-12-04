package DataStructureAndAlgorithms.ui;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.ui.navigation.MenuNavigator;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.prompts.Prompter;

public class UIFactory {

    public static UIManager createUIManager(InputHandler inputHandler) {
        return new UIManager(inputHandler);
    }

    public static MenuNavigator createMenuNavigator(SelectionHandler selectionHandler) {
        return new MenuNavigator(selectionHandler);
    }

    public static SelectionHandler createSelectionHandler(Prompter prompter, UIManager uiManager) {
        return new SelectionHandler(prompter, uiManager);
    }

    public static Prompter createPrompter(InputHandler inputHandler, UIManager uiManager) {
        return new Prompter(inputHandler, uiManager);
    }

    public static InputHandler createInputHandler() {
        return new InputHandler();
    }
}