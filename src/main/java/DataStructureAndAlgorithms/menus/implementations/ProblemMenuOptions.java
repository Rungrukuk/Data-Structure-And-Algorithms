package DataStructureAndAlgorithms.menus.implementations;

import DataStructureAndAlgorithms.menus.LabeledOption;

public enum ProblemMenuOptions implements LabeledOption {
    LIST_PROBLEMS("List All Problems"),
    LIST_PROBLEMS_BY_CATEGORY("List Problems By Category"),
    RUN_SPECIFIC_PROBLEM("Run Problem By Name"),
    LIST_PRACTICES("List All Practices"),
    LIST_PRACTICES_BY_CATEGORY("List Practices By Category"),
    RUN_SPECIFIC_PRACTICE("Run Practice By Name"),
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
