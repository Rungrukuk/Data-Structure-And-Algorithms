package DataStructureAndAlgorithms.menus;

public interface LabeledOption {
    String getLabel();

    static <E extends Enum<E> & LabeledOption> E fromLabel(Class<E> enumType, String label) {
        for (E constant : enumType.getEnumConstants()) {
            if (constant.getLabel().equals(label)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + label);
    }
}