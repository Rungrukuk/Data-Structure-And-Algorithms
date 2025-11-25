package DataStructureAndAlgorithms;

import DataStructureAndAlgorithms.utils.HelperMethods;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Problem_Class_Creator {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String BASE_PACKAGE = "DataStructureAndAlgorithms.Problems";
    private static final String BASE_SOURCE_PATH = "src/main/java/DataStructureAndAlgorithms/Problems";

    // Common Java types that need imports
    private static final Map<String, String> IMPORT_MAPPINGS = new HashMap<>();
    static {
        // Collections
        IMPORT_MAPPINGS.put("List", "java.util.List");
        IMPORT_MAPPINGS.put("ArrayList", "java.util.ArrayList");
        IMPORT_MAPPINGS.put("LinkedList", "java.util.LinkedList");
        IMPORT_MAPPINGS.put("Set", "java.util.Set");
        IMPORT_MAPPINGS.put("HashSet", "java.util.HashSet");
        IMPORT_MAPPINGS.put("TreeSet", "java.util.TreeSet");
        IMPORT_MAPPINGS.put("Map", "java.util.Map");
        IMPORT_MAPPINGS.put("HashMap", "java.util.HashMap");
        IMPORT_MAPPINGS.put("TreeMap", "java.util.TreeMap");
        IMPORT_MAPPINGS.put("Queue", "java.util.Queue");
        IMPORT_MAPPINGS.put("Deque", "java.util.Deque");
        IMPORT_MAPPINGS.put("Stack", "java.util.Stack");
        IMPORT_MAPPINGS.put("Optional", "java.util.Optional");

        // Other common types
        IMPORT_MAPPINGS.put("Arrays", "java.util.Arrays");
        IMPORT_MAPPINGS.put("Collections", "java.util.Collections");
    }

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("      PROBLEM CLASS CREATOR");
        System.out.println("=========================================\n");

        createNewProblemClass();
    }

    public static void createNewProblemClass() {
        try {
            // Get problem details from user
            String problemName = getProblemName();
            String category = getCategory();
            String returnType = getReturnType();

            // Generate class content
            String classContent = generateClassContent(problemName, category, returnType);

            // Create file
            boolean success = createClassFile(problemName, category, classContent);

            if (success) {
                System.out.println("\n✅ Problem class created successfully!");
                System.out.println("📁 Location: " + getFilePath(problemName, category));
                System.out.println("📋 Generated return type: " + returnType);
            } else {
                System.out.println("\n❌ Failed to create problem class.");
            }

        } catch (Exception e) {
            System.err.println("Error creating problem class: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static String getProblemName() {
        System.out.print("Enter problem name (e.g., 'SplitArrayLargestSum', 'Split Array Largest Sum'): ");
        String input = scanner.nextLine().trim();

        while (input.isEmpty()) {
            System.out.print("Problem name cannot be empty. Please enter problem name: ");
            input = scanner.nextLine().trim();
        }

        return input;
    }

    private static String getCategory() {
        System.out.print("Enter category (e.g., 'Binary Search', 'Dynamic Programming'): ");
        String input = scanner.nextLine().trim();

        while (input.isEmpty()) {
            System.out.print("Category cannot be empty. Please enter category: ");
            input = scanner.nextLine().trim();
        }

        return input;
    }

    private static String getReturnType() {
        while (true) {
            System.out.print("Enter return type (e.g., 'Integer', 'int[]', 'List<String>'): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Return type cannot be empty.");
                continue;
            }

            // First check if the type is valid
            if (!HelperMethods.isValidJavaType(input)) {
                System.out.println("❌ Invalid Java type: '" + input + "'");
                System.out.println("💡 Primitive types (int, boolean, etc.) are not allowed inside generics.");
                System.out.println("💡 Examples: 'Integer', 'String', 'int[]', 'List<String>', 'Map<Integer, String>'");
                continue;
            }

            // Convert to wrapper type
            String convertedType = HelperMethods.convertToWrapperType(input);

            // Validate again after conversion
            if (!HelperMethods.isValidJavaType(convertedType)) {
                System.out.println("❌ Invalid Java type after conversion: '" + convertedType
                        + "'. Please enter a different type.");
                continue;
            }

            System.out.println("✓ Converted to: " + convertedType);
            return convertedType;
        }
    }

    private static String generateClassContent(String problemName, String category, String returnType) {
        String className = HelperMethods.generateClassName(problemName);
        String formattedProblemName = HelperMethods.generateFormattedProblemName(problemName);
        String formattedCategoryName = HelperMethods.generateFormattedCategoryName(category);
        String packageName = generatePackageName(category);

        // Get required imports based on return type
        Set<String> imports = getRequiredImports(returnType);

        // Build imports section
        StringBuilder importsSection = new StringBuilder();
        importsSection.append("import DataStructureAndAlgorithms.Base_Problem;\n");
        importsSection.append("import DataStructureAndAlgorithms.Problem;\n");

        for (String importStmt : imports) {
            importsSection.append("import ").append(importStmt).append(";\n");
        }

        return String.format(
                "package %s;\n\n" +
                        "%s\n" +
                        "@Problem(name = \"%s\", category = \"%s\")\n" +
                        "public class %s extends Base_Problem<%s> {\n\n" +
                        "    @Override\n" +
                        "    protected %s solve() {\n" +
                        "        // TODO: Implement your solution here\n" +
                        "        throw new UnsupportedOperationException(\"Unimplemented method 'solve'\");\n" +
                        "    }\n\n" +
                        "}",
                packageName, importsSection.toString(), formattedProblemName, formattedCategoryName, className,
                returnType, returnType);
    }

    private static Set<String> getRequiredImports(String returnType) {
        Set<String> imports = new TreeSet<>();

        // Check for common collection types and other types that need imports
        for (String type : IMPORT_MAPPINGS.keySet()) {
            if (containsType(returnType, type)) {
                imports.add(IMPORT_MAPPINGS.get(type));
            }
        }

        return imports;
    }

    private static boolean containsType(String returnType, String type) {
        // Simple check - if the return type contains the type name as a whole word
        String pattern = "\\b" + type + "\\b";
        return returnType.matches(".*" + pattern + ".*");
    }

    private static String generatePackageName(String category) {
        String categoryFolder = HelperMethods.generateCategoryFolderName(category);
        return BASE_PACKAGE + "." + categoryFolder;
    }

    private static String getFilePath(String problemName, String category) {
        String className = HelperMethods.generateClassName(problemName);
        String categoryFolder = HelperMethods.generateCategoryFolderName(category);
        return BASE_SOURCE_PATH + "/" + categoryFolder + "/" + className + ".java";
    }

    private static boolean createClassFile(String problemName, String category, String classContent) {
        try {
            String filePath = getFilePath(problemName, category);
            File file = new File(filePath);

            // Create directory if it doesn't exist
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                boolean dirsCreated = parentDir.mkdirs();
                if (!dirsCreated) {
                    System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
                    return false;
                }
                System.out.println("📁 Created category directory: " + parentDir.getName());
            }

            // Create the file
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(classContent);
            }

            return true;

        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return false;
        }
    }
}