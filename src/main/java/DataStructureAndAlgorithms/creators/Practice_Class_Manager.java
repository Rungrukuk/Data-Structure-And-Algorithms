package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.core.Practice;
import DataStructureAndAlgorithms.core.Problem;
import DataStructureAndAlgorithms.utils.HelperMethods;
// import DataStructureAndAlgorithms.Base_Problem;
// import DataStructureAndAlgorithms.Base_Practice;
// import DataStructureAndAlgorithms.Problem;
// import DataStructureAndAlgorithms.Practice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class Practice_Class_Manager {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String PROBLEMS_BASE_PACKAGE = "DataStructureAndAlgorithms.Problems";
    private static final String PRACTICES_BASE_PACKAGE = "DataStructureAndAlgorithms.Practices";
    private static final String PROBLEMS_SOURCE_PATH = "src/main/java/DataStructureAndAlgorithms/Problems";
    private static final String PRACTICES_SOURCE_PATH = "src/main/java/DataStructureAndAlgorithms/Practices";

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("      PRACTICE CLASS MANAGER");
        System.out.println("=========================================\n");

        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== PRACTICE MANAGER MENU ===");
            System.out.println("1. Create new practice class");
            System.out.println("2. Reset practice by name");
            System.out.println("3. Reset practice from list");
            System.out.println("4. Reset practices by category");
            System.out.println("5. Reset all practice classes");
            System.out.println("6. Exit");
            System.out.print("\nChoose an option (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createNewPracticeClass();
                    break;
                case "2":
                    resetPracticeByName();
                    break;
                case "3":
                    resetPracticeFromList();
                    break;
                case "4":
                    resetPracticesByCategory();
                    break;
                case "5":
                    resetAllPracticeClasses();
                    break;
                case "6":
                    System.out.println("Returning to main application...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-6.");
            }
        }
    }

    // Option 1: Create new practice class
    private static void createNewPracticeClass() {
        try {
            System.out.println("\n=== CREATE NEW PRACTICE CLASS ===");

            // Get all available problems
            Map<String, Class<?>> problems = getAllProblemClasses();
            if (problems.isEmpty()) {
                System.out.println("No problems found. Please create problems first.");
                return;
            }

            // Let user choose how to select the problem
            System.out.println("How would you like to choose the problem?");
            System.out.println("1. List all problems");
            System.out.println("2. List by category");
            System.out.print("Choose option (1-2): ");

            String option = scanner.nextLine().trim();
            String selectedProblemName = null;

            if (option.equals("1")) {
                selectedProblemName = selectProblemFromList(new ArrayList<>(problems.keySet()));
            } else if (option.equals("2")) {
                selectedProblemName = selectProblemByCategory(problems);
            } else {
                System.out.println("Invalid option.");
                return;
            }

            if (selectedProblemName != null) {
                Class<?> problemClass = problems.get(selectedProblemName);
                createPracticeClassForProblem(selectedProblemName, problemClass);
            }

        } catch (Exception e) {
            System.err.println("Error creating practice class: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Option 2: Reset practice by name
    private static void resetPracticeByName() {
        try {
            System.out.println("\n=== RESET PRACTICE BY NAME ===");

            Map<String, PracticeInfo> practices = getAllPracticeInfo();
            if (practices.isEmpty()) {
                System.out.println("No practice classes found.");
                return;
            }

            System.out.print("Enter practice name: ");
            String practiceName = scanner.nextLine().trim();

            String normalizedInput = HelperMethods.normalizeProblemName(practiceName);
            String foundPractice = HelperMethods.findMatchingProblem(new ArrayList<>(practices.keySet()),
                    normalizedInput);

            if (foundPractice != null) {
                PracticeInfo practiceInfo = practices.get(foundPractice);
                regeneratePracticeClass(practiceInfo);
            } else {
                System.out.println("❌ Practice not found: " + practiceName);
                System.out.println("Available practices: " + new ArrayList<>(practices.keySet()));
            }

        } catch (Exception e) {
            System.err.println("Error resetting practice: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Option 3: Reset practice from list
    private static void resetPracticeFromList() {
        try {
            System.out.println("\n=== RESET PRACTICE FROM LIST ===");

            Map<String, PracticeInfo> practices = getAllPracticeInfo();
            if (practices.isEmpty()) {
                System.out.println("No practice classes found.");
                return;
            }

            List<String> practiceNames = new ArrayList<>(practices.keySet());
            Collections.sort(practiceNames);

            System.out.println("Available practices:");
            for (int i = 0; i < practiceNames.size(); i++) {
                System.out.printf("%2d. %s%n", i + 1, HelperMethods.formatProblemName(practiceNames.get(i)));
            }

            System.out.print("Enter practice number to reset: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= practiceNames.size()) {
                    String practiceName = practiceNames.get(choice - 1);
                    PracticeInfo practiceInfo = practices.get(practiceName);
                    regeneratePracticeClass(practiceInfo);
                } else {
                    System.out.println("Invalid number. Please choose between 1-" + practiceNames.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }

        } catch (Exception e) {
            System.err.println("Error resetting practice: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Option 4: Reset practices by category
    private static void resetPracticesByCategory() {
        try {
            System.out.println("\n=== RESET PRACTICES BY CATEGORY ===");

            Map<String, List<PracticeInfo>> practicesByCategory = getPracticeInfoByCategory();
            if (practicesByCategory.isEmpty()) {
                System.out.println("No practice classes found.");
                return;
            }

            // Show categories
            List<String> categories = new ArrayList<>(practicesByCategory.keySet());
            Collections.sort(categories);

            System.out.println("Available categories:");
            for (int i = 0; i < categories.size(); i++) {
                int practiceCount = practicesByCategory.get(categories.get(i)).size();
                System.out.printf("%2d. %s (%d practices)%n", i + 1, categories.get(i), practiceCount);
            }

            System.out.print("Enter category number to reset: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= categories.size()) {
                    String category = categories.get(choice - 1);
                    List<PracticeInfo> practiceInfos = practicesByCategory.get(category);

                    System.out.println("Resetting " + practiceInfos.size() + " practices in category: " + category);
                    for (PracticeInfo practiceInfo : practiceInfos) {
                        regeneratePracticeClass(practiceInfo);
                    }
                } else {
                    System.out.println("Invalid number. Please choose between 1-" + categories.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }

        } catch (Exception e) {
            System.err.println("Error resetting practices by category: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Option 5: Reset all practice classes
    private static void resetAllPracticeClasses() {
        try {
            System.out.println("\n=== RESET ALL PRACTICE CLASSES ===");

            Map<String, PracticeInfo> practices = getAllPracticeInfo();
            if (practices.isEmpty()) {
                System.out.println("No practice classes found.");
                return;
            }

            System.out.println("This will reset " + practices.size() + " practice classes.");
            System.out.print("Are you sure? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes") || confirmation.equals("y")) {
                for (PracticeInfo practiceInfo : practices.values()) {
                    regeneratePracticeClass(practiceInfo);
                }
                System.out.println("✅ All practice classes have been reset.");
            } else {
                System.out.println("Operation cancelled.");
            }

        } catch (Exception e) {
            System.err.println("Error resetting all practices: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========== HELPER METHODS ==========

    private static Map<String, Class<?>> getAllProblemClasses() {
        Map<String, Class<?>> problemClasses = new HashMap<>();
        try {
            File problemsDir = new File(PROBLEMS_SOURCE_PATH);
            if (!problemsDir.exists())
                return problemClasses;

            scanProblemDirectory(problemsDir, problemClasses, PROBLEMS_BASE_PACKAGE);

        } catch (Exception e) {
            System.err.println("Error scanning problem classes: " + e.getMessage());
        }
        return problemClasses;
    }

    private static void scanProblemDirectory(File dir, Map<String, Class<?>> problemClasses, String packageName) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanProblemDirectory(file, problemClasses, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".java")) {
                String className = file.getName().replace(".java", "");
                try {
                    Class<?> clazz = Class.forName(packageName + "." + className);
                    if (clazz.isAnnotationPresent(Problem.class)) {
                        Problem annotation = clazz.getAnnotation(Problem.class);
                        problemClasses.put(annotation.name(), clazz);
                    }
                } catch (ClassNotFoundException e) {
                    // Skip if class can't be loaded
                }
            }
        }
    }

    private static String selectProblemFromList(List<String> problemNames) {
        Collections.sort(problemNames);

        System.out.println("\nAvailable problems:");
        for (int i = 0; i < problemNames.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, HelperMethods.formatProblemName(problemNames.get(i)));
        }

        System.out.print("Enter problem number: ");
        String input = scanner.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= problemNames.size()) {
                return problemNames.get(choice - 1);
            } else {
                System.out.println("Invalid number. Please choose between 1-" + problemNames.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
        return null;
    }

    private static String selectProblemByCategory(Map<String, Class<?>> problems) {
        // Group problems by category
        Map<String, List<String>> problemsByCategory = new HashMap<>();
        for (Map.Entry<String, Class<?>> entry : problems.entrySet()) {
            Problem annotation = entry.getValue().getAnnotation(Problem.class);
            String category = annotation.category();
            problemsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(entry.getKey());
        }

        // Show categories
        List<String> categories = new ArrayList<>(problemsByCategory.keySet());
        Collections.sort(categories);

        System.out.println("\nAvailable categories:");
        for (int i = 0; i < categories.size(); i++) {
            int problemCount = problemsByCategory.get(categories.get(i)).size();
            System.out.printf("%2d. %s (%d problems)%n", i + 1, categories.get(i), problemCount);
        }

        System.out.print("Enter category number: ");
        String input = scanner.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= categories.size()) {
                String category = categories.get(choice - 1);
                return selectProblemFromList(problemsByCategory.get(category));
            } else {
                System.out.println("Invalid number. Please choose between 1-" + categories.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
        return null;
    }

    private static void createPracticeClassForProblem(String problemName, Class<?> problemClass) {
        try {
            Problem problemAnnotation = problemClass.getAnnotation(Problem.class);
            String category = problemAnnotation.category();

            // Get practice info
            PracticeInfo practiceInfo = createPracticeInfo(problemName, problemClass, category);

            // Generate and write practice class
            String classContent = generatePracticeClassContent(practiceInfo);
            writePracticeClassFile(practiceInfo, classContent);

            System.out.println("✅ Practice class created: " + practiceInfo.practiceClassName);
            System.out.println("📁 Location: " + practiceInfo.filePath);

        } catch (Exception e) {
            System.err.println("Error creating practice class: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static PracticeInfo createPracticeInfo(String problemName, Class<?> problemClass, String category) {
        // Problem problemAnnotation = problemClass.getAnnotation(Problem.class);
        String formattedCategory = HelperMethods.generateFormattedCategoryName(category);

        // Get return type from Base_Problem generic parameter
        Type genericSuperclass = problemClass.getGenericSuperclass();
        String returnType = "Object"; // default
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericSuperclass;
            Type[] typeArgs = paramType.getActualTypeArguments();
            if (typeArgs.length > 0) {
                returnType = typeArgs[0].getTypeName();
                // Convert to simple name if it's a full class name
                if (returnType.contains(".")) {
                    returnType = returnType.substring(returnType.lastIndexOf('.') + 1);
                }
            }
        }

        // Generate practice class info
        String practiceClassName = HelperMethods.generateClassName(problemName) + "_Practice";
        String packageName = PRACTICES_BASE_PACKAGE + "." + HelperMethods.generateCategoryFolderName(category);
        String problemClassName = problemClass.getSimpleName();
        String filePath = PRACTICES_SOURCE_PATH + "/" +
                HelperMethods.generateCategoryFolderName(category) + "/" + practiceClassName + ".java";

        return new PracticeInfo(
                problemName,
                formattedCategory,
                returnType,
                practiceClassName,
                packageName,
                problemClassName,
                filePath);
    }

    private static String generatePracticeClassContent(PracticeInfo info) {
        return String.format(
                "package %s;\n\n" +
                        "import DataStructureAndAlgorithms.Base_Practice;\n" +
                        "import DataStructureAndAlgorithms.Practice;\n" +
                        "import DataStructureAndAlgorithms.Problems.%s.%s;\n\n" +
                        "@Practice(\n" +
                        "    problemName = \"%s\",\n" +
                        "    category = \"%s\",\n" +
                        "    description = \"Practice implementation for %s\"\n" +
                        ")\n" +
                        "public class %s extends Base_Practice<%s, %s> {\n\n" +
                        "    public %s(%s problem) {\n" +
                        "        super(problem);\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    protected %s practice() {\n" +
                        "        // TODO: Implement your solution here\n" +
                        "        throw new UnsupportedOperationException(\"Unimplemented method 'practice'\");\n" +
                        "    }\n\n" +
                        "}",
                info.packageName,
                HelperMethods.generateCategoryFolderName(info.category), info.problemClassName,
                info.problemName, info.category, info.problemName,
                info.practiceClassName, info.returnType, info.problemClassName,
                info.practiceClassName, info.problemClassName,
                info.returnType);
    }

    private static void writePracticeClassFile(PracticeInfo info, String classContent) throws IOException {
        File file = new File(info.filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(classContent);
        }
    }

    private static Map<String, PracticeInfo> getAllPracticeInfo() {
        Map<String, PracticeInfo> practiceInfos = new HashMap<>();
        try {
            File practicesDir = new File(PRACTICES_SOURCE_PATH);
            if (!practicesDir.exists())
                return practiceInfos;

            scanPracticeDirectoryForInfo(practicesDir, practiceInfos, PRACTICES_BASE_PACKAGE);

        } catch (Exception e) {
            System.err.println("Error scanning practice info: " + e.getMessage());
        }
        return practiceInfos;
    }

    private static void scanPracticeDirectoryForInfo(File dir, Map<String, PracticeInfo> practiceInfos,
            String packageName) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPracticeDirectoryForInfo(file, practiceInfos, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".java") && file.getName().contains("_Practice")) {
                try {
                    String className = file.getName().replace(".java", "");
                    Class<?> practiceClass = Class.forName(packageName + "." + className);

                    if (practiceClass.isAnnotationPresent(Practice.class)) {
                        Practice annotation = practiceClass.getAnnotation(Practice.class);
                        String problemName = annotation.problemName();
                        String category = annotation.category();

                        // Find the corresponding problem class
                        Map<String, Class<?>> problemClasses = getAllProblemClasses();
                        Class<?> problemClass = problemClasses.get(problemName);

                        if (problemClass != null) {
                            PracticeInfo practiceInfo = createPracticeInfo(problemName, problemClass, category);
                            practiceInfos.put(problemName, practiceInfo);
                        }
                    }
                } catch (Exception e) {
                    // If we can't load the class, try to extract info from file path
                    extractPracticeInfoFromFile(file, practiceInfos);
                }
            }
        }
    }

    private static void extractPracticeInfoFromFile(File practiceFile, Map<String, PracticeInfo> practiceInfos) {
        try {
            // Extract info from file path and name
            String fileName = practiceFile.getName().replace("_Practice.java", "");
            String problemName = HelperMethods.formatProblemName(fileName.replace("_", " "));

            // Extract category from file path
            String[] pathParts = practiceFile.getPath().split("[\\\\/]");
            String category = "Unknown";
            for (int i = 0; i < pathParts.length; i++) {
                if (pathParts[i].equals("Practices") && i + 1 < pathParts.length) {
                    category = HelperMethods.formatProblemName(pathParts[i + 1].replace("_", " "));
                    break;
                }
            }

            // Find the corresponding problem class
            Map<String, Class<?>> problemClasses = getAllProblemClasses();
            Class<?> problemClass = problemClasses.get(problemName);

            if (problemClass != null) {
                PracticeInfo practiceInfo = createPracticeInfo(problemName, problemClass, category);
                practiceInfos.put(problemName, practiceInfo);
            }

        } catch (Exception e) {
            System.err.println("Could not extract practice info from file: " + practiceFile.getName());
        }
    }

    private static Map<String, List<PracticeInfo>> getPracticeInfoByCategory() {
        Map<String, List<PracticeInfo>> practicesByCategory = new HashMap<>();
        Map<String, PracticeInfo> allPractices = getAllPracticeInfo();

        for (PracticeInfo practiceInfo : allPractices.values()) {
            practicesByCategory.computeIfAbsent(practiceInfo.category, k -> new ArrayList<>()).add(practiceInfo);
        }

        return practicesByCategory;
    }

    private static void regeneratePracticeClass(PracticeInfo practiceInfo) {
        try {
            String classContent = generatePracticeClassContent(practiceInfo);
            writePracticeClassFile(practiceInfo, classContent);
            System.out.println("✅ Reset practice: " + practiceInfo.problemName);
        } catch (Exception e) {
            System.err.println("❌ Error resetting practice: " + practiceInfo.problemName);
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Helper class to store practice class information
    private static class PracticeInfo {
        String problemName;
        String category;
        String returnType;
        String practiceClassName;
        String packageName;
        String problemClassName;
        String filePath;

        PracticeInfo(String problemName, String category, String returnType,
                String practiceClassName, String packageName,
                String problemClassName, String filePath) {
            this.problemName = problemName;
            this.category = category;
            this.returnType = returnType;
            this.practiceClassName = practiceClassName;
            this.packageName = packageName;
            this.problemClassName = problemClassName;
            this.filePath = filePath;
        }
    }
}