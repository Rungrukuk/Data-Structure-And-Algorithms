package DataStructureAndAlgorithms.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    private Constants() {
    }

    // ========================= FILE PATHS =========================
    public static final String BASE_SOURCE_PATH = "src/main/java/";
    public static final String BASE_PACKAGE = "DataStructureAndAlgorithms";
    public static final String DOT_SEPERATOR = ".";
    public static final String SLASH_SEPERATOR = "/";
    public static final String PROBLEM_PACKAGE = BASE_PACKAGE + DOT_SEPERATOR + "Problems";
    public static final String PRACTICE_PACKAGE = BASE_PACKAGE + DOT_SEPERATOR + "Practices";
    public static final String BASE_PROBLEM_PACKAGE = BASE_SOURCE_PATH + BASE_PACKAGE + SLASH_SEPERATOR + "Problems";
    public static final String BASE_PRACTICE_PACKAGE = BASE_SOURCE_PATH + BASE_PACKAGE + SLASH_SEPERATOR + "Practices";

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
    public static final String INVALID_PROBLEM_NAME = "Invalid problem name";
    public static final String INVALID_PRACTICE_NAME = "Invalid practice name";
    public static final String INVALID_CATEGORY_NAME = "Invalid category name";
    public static final String INVALID_RETURN_TYPE = "Invalid reutrn type";

    // ========================= IMPORTS =========================
    public static final Map<String, String> IMPORT_MAPPINGS = new HashMap<>();
    static {
        IMPORT_MAPPINGS.put("List", "java.util.List");
        IMPORT_MAPPINGS.put("ArrayList", "java.util.ArrayList");
        IMPORT_MAPPINGS.put("LinkedList", "java.util.LinkedList");
        IMPORT_MAPPINGS.put("Set", "java.util.Set");
        IMPORT_MAPPINGS.put("HashSet", "java.util.HashSet");
        IMPORT_MAPPINGS.put("TreeSet", "java.util.TreeSet");
        IMPORT_MAPPINGS.put("Map", "java.util.Map");
        IMPORT_MAPPINGS.put("HashMap", "java.util.HashMap");
        IMPORT_MAPPINGS.put("TreeMap", "java.util.TreeMap");
        IMPORT_MAPPINGS.put("Optional", "java.util.Optional");

        IMPORT_MAPPINGS.put("Arrays", "java.util.Arrays");
        IMPORT_MAPPINGS.put("Collections", "java.util.Collections");
    }
    public static final String IMPORT = "import ";
    public static final String BASE_PROBLEM_IMPORT = IMPORT + "DataStructureAndAlgorithms.core.BaseProblem;\n";
    public static final String BASE_PRACTICE_IMPORT = IMPORT + "DataStructureAndAlgorithms.core.BasePractice;\n";
    public static final String PRACTICE_IMPORT = IMPORT + "DataStructureAndAlgorithms.core.Practice;\n";
    public static final String PROBLEM_IMPORT = IMPORT + "DataStructureAndAlgorithms.core.Problem;\n";

}
