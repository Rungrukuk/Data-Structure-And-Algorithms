package DataStructureAndAlgorithms.ui.navigation;

import DataStructureAndAlgorithms.menus.MenuOption;
import DataStructureAndAlgorithms.menus.MenuRegistry;
import DataStructureAndAlgorithms.ui.UIManager;

import java.util.List;

public class MenuNavigator {
    private final UIManager uiManager;

    public MenuNavigator(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    public MenuOption showMainMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.MAIN_MENU);
        String selection = uiManager.showMenuAndGetSelection(menuLabels, "MAIN MENU");
        return MenuRegistry.findByLabel(MenuRegistry.MAIN_MENU, selection);
    }

    public MenuOption showProblemMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.PROBLEM_MENU);
        String selection = uiManager.showMenuAndGetSelection(menuLabels, "PROBLEM MENU");
        return MenuRegistry.findByLabel(MenuRegistry.PROBLEM_MENU, selection);
    }

    public MenuOption showManagePracticesMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.MANAGE_PRACTICES_MENU);
        String selection = uiManager.showMenuAndGetSelection(menuLabels, "MANAGE PRACTICES");
        return MenuRegistry.findByLabel(MenuRegistry.MANAGE_PRACTICES_MENU, selection);
    }

    public MenuOption showCreatePracticeMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.CREATE_PRACTICE_MENU);
        String selection = uiManager.showMenuAndGetSelection(menuLabels, "CREATE PRACTICE");
        return MenuRegistry.findByLabel(MenuRegistry.CREATE_PRACTICE_MENU, selection);
    }
}