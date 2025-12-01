package DataStructureAndAlgorithms.ui;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.ui.navigation.MenuNavigator;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.prompts.ProblemPrompter;
import DataStructureAndAlgorithms.ui.prompts.PracticePrompter;

public class UIFactory {

    public static UIManager createUIManager() {
        InputHandler inputHandler = new InputHandler();
        MenuService menuService = new MenuService(inputHandler);
        return new UIManager(inputHandler, menuService);
    }

    public static MenuNavigator createMenuNavigator(UIManager uiManager) {
        return new MenuNavigator(uiManager);
    }

    public static SelectionHandler createSelectionHandler(UIManager uiManager) {
        return new SelectionHandler(uiManager);
    }

    public static ProblemPrompter createProblemPrompter(InputHandler inputHandler, UIManager uiManager) {
        return new ProblemPrompter(inputHandler, uiManager);
    }

    public static PracticePrompter createPracticePrompter(InputHandler inputHandler, UIManager uiManager) {
        return new PracticePrompter(inputHandler, uiManager);
    }

    public static InputHandler createInputHandler() {
        return new InputHandler();
    }
}