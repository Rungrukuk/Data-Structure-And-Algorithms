package DataStructureAndAlgorithms.menus.implementations;

import DataStructureAndAlgorithms.menus.LabeledOption;

public enum ProblemMenuOptions implements LabeledOption {
    LIST_PROBLEMS("List All Problems"),
    LIST_BY_CATEGORY("List Problems By Category"),
    RUN_SPECIFIC("Run Problem By Name"),
    RETURN("Return Back");

    private final String label;

    ProblemMenuOptions(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
