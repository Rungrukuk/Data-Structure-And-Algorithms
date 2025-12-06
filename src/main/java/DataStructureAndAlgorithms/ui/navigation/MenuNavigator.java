package DataStructureAndAlgorithms.ui.navigation;

import DataStructureAndAlgorithms.menus.MenuOption;
import DataStructureAndAlgorithms.menus.MenuRegistry;

import java.util.List;
import java.util.Optional;

public class MenuNavigator {
    private final SelectionHandler selectionHandler;

    public MenuNavigator(SelectionHandler selectionHandler) {
        this.selectionHandler = selectionHandler;
    }

    public MenuOption showMainMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.MAIN_MENU);
        Optional<String> selection = selectionHandler.selectFromMenu(menuLabels, "MAIN MENU");
        return selection.map(label -> MenuRegistry.findByLabel(MenuRegistry.MAIN_MENU, label))
                .orElse(null);
    }

    public MenuOption showProblemMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.PROBLEM_MENU);
        Optional<String> selection = selectionHandler.selectFromMenu(menuLabels, "PROBLEM MENU");
        return selection.map(label -> MenuRegistry.findByLabel(MenuRegistry.PROBLEM_MENU, label))
                .orElse(null);
    }

    public MenuOption showManagePracticesMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.MANAGE_PRACTICES_MENU);
        Optional<String> selection = selectionHandler.selectFromMenu(menuLabels, "MANAGE PRACTICES");
        return selection.map(label -> MenuRegistry.findByLabel(MenuRegistry.MANAGE_PRACTICES_MENU, label))
                .orElse(null);
    }

    public MenuOption showCreatePracticeMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.CREATE_PRACTICE_MENU);
        Optional<String> selection = selectionHandler.selectFromMenu(menuLabels, "CREATE PRACTICE");
        return selection.map(label -> MenuRegistry.findByLabel(MenuRegistry.CREATE_PRACTICE_MENU, label))
                .orElse(null);
    }

    public MenuOption showResetPracticeMenu() {
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.RESET_PRACTICE_MENU);
        Optional<String> selection = selectionHandler.selectFromMenu(menuLabels, "RESET PRACTICE");
        return selection.map(label -> MenuRegistry.findByLabel(MenuRegistry.RESET_PRACTICE_MENU, label))
                .orElse(null);
    }

    public MenuOption showBulkResetPracticesMenu(){
        List<String> menuLabels = MenuRegistry.getLabels(MenuRegistry.BULK_RESET_PRACTICES_MENU);
        Optional<String> selection = selectionHandler.selectFromMenu(menuLabels, "BULK RESET PRACTICES");
        return selection.map(label -> MenuRegistry.findByLabel(MenuRegistry.BULK_RESET_PRACTICES_MENU, label))
                .orElse(null);
    }
}
