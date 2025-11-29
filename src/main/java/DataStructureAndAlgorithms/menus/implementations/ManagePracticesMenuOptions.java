package DataStructureAndAlgorithms.menus.implementations;

import DataStructureAndAlgorithms.menus.LabeledOption;

public enum ManagePracticesMenuOptions implements LabeledOption {
    CREATE_PRACTICE("Create Practice Class"),
    RESET_PRACTICE("Reset Practices"),
    RETURN("Return Back");

    private final String label;

    ManagePracticesMenuOptions(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
