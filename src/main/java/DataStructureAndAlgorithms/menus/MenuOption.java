package DataStructureAndAlgorithms.menus;

public class MenuOption {
    private final MenuKey key;
    private final String label;

    public MenuOption(MenuKey key, String label) {
        this.key = key;
        this.label = label;
    }

    public MenuKey getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}