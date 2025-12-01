package DataStructureAndAlgorithms.ui;

import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;

public class MenuService {
    private final InputHandler inputHandler;

    public MenuService(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public String showMenu(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        System.out.printf(ApplicationConstants.CHOOSE_OPTION, options.size());
        return inputHandler.selectFromList(options);
    }

    public String selectFromList(List<String> options, String prompt) {
        System.out.println("\n" + prompt);
        return showMenu(options);
    }

    public String selectFromCollection(List<String> items, String prompt) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        return selectFromList(items, prompt);
    }
}