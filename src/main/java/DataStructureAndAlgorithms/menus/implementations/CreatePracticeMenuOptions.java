package DataStructureAndAlgorithms.menus.implementations;

import DataStructureAndAlgorithms.menus.LabeledOption;

public enum CreatePracticeMenuOptions implements LabeledOption {
    LIST_PROBLEMS("List All Problems"),
    LIST_PROBLEMS_BY_CATEGORY("List Problems By Category"),
    FIND_SPECIFIC_PROBLEM("Run Problem By Name"),
    RETURN("Return Back");

    private final String label;

    CreatePracticeMenuOptions(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
