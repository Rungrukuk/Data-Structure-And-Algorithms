package DataStructureAndAlgorithms.utils;

public class Constants {

    private Constants() {
    }

    // ========================= FILE PATHS =========================
    public static final String BASE_SOURCE_PATH = "src/main/java/";
    public static final String BASE_PACKAGE = "DataStructureAndAlgorithms";
    public static final String SEPERATOR = ".";
    public static final String PROBLEM_PACKAGE = BASE_PACKAGE + SEPERATOR + "Problems";
    public static final String PRACTICE_PACKAGE = BASE_PACKAGE + SEPERATOR + "Practices";

    public static final String PRACTICE_CLASS_SUFFIX = "_Practice";
    public static final String JAVA_FILE_SUFFIX = ".java";

    // ========================= TERMINAL COLORS =========================
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final int RETURN_TYPE_POSITION = 0;
    public static final int PROBLEM_CLASS_POSITION = 1;
    public static final int NUMBER_OF_GENERICS_PROBLEM = 1;
    public static final int NUMBER_OF_GENERICS_PRACTICE = 2;

    // ========================= MENU CONSTANTS =========================
    public static final int RETURN_BACK = 0;
    public static final String INCORRECT_OPTION = "Incorrect option";
    public static final String INCORRECT_PROBLEM_NAME = "Incorrect problem name";
    public static final String INCORRECT_PRACTICE_NAME = "Incorrect practice name";
    public static final String CHOOSE_OPTION = "Choose an option between 1 - %d: ";
    public static final String ENTER_PROBLEM_NAME = "Enter a problem name (0 to return): ";
    public static final String ENTER_PRACTICE_NAME = "Enter a practice name (0 to return): ";
    public static final String DIDNOT_FIND_PROBLEM_NAME = "Could not find specified problem: ";
    public static final String DIDNOT_FIND_PRACTICE_NAME = "Could not find specified practice: ";

}
