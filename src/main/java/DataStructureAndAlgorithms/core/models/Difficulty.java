package DataStructureAndAlgorithms.core.models;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public static Difficulty fromString(String value) {
        if (value == null) return null;
        return switch (value.trim().toLowerCase()) {
            case "easy" -> EASY;
            case "medium" -> MEDIUM;
            case "hard" -> HARD;
            default -> throw new IllegalArgumentException("Unknown difficulty: " + value);
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case EASY -> "Easy";
            case MEDIUM -> "Medium";
            case HARD -> "Hard";
        };
    }
}
