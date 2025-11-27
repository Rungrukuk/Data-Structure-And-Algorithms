package DataStructureAndAlgorithms.menus.implementations;

import DataStructureAndAlgorithms.menus.LabeledOption;

public enum MainMenuOptions implements LabeledOption {
    RUN("Run Problems & Practices"),
    CREATE_PROBLEM("Create New Problem"),
    MANAGE_PRACTICES("Manage Practices"),
    EXIT("Exit");

    private final String label;

    MainMenuOptions(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
