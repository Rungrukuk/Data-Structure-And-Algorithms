package DataStructureAndAlgorithms;

import java.util.*;

import DataStructureAndAlgorithms.utils.HelperMethods;

public class Run_Problems {
    private static final Scanner scanner = new Scanner(System.in);
    private static Problem_Manager problemManager;

    public static void main(String[] args) {
        problemManager = new Problem_Manager();

        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("       PROBLEM & PRACTICE RUNNER");
        System.out.println("=========================================");

        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. List all problems");
            System.out.println("2. List problems by category");
            System.out.println("3. Run a specific problem");
            System.out.println("4. List all practices");
            System.out.println("5. List practices by category");
            System.out.println("6. Run a specific practice");
            System.out.println("7. Exit");
            System.out.print("\nChoose an option (1-7): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    listAllProblems();
                    break;
                case "2":
                    listProblemsByCategory();
                    break;
                case "3":
                    runSpecificProblem();
                    break;
                case "4":
                    listAllPractices();
                    break;
                case "5":
                    listPracticesByCategory();
                    break;
                case "6":
                    runSpecificPractice();
                    break;
                case "7":
                    System.out.println("Happy coding! Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-7.");
            }
        }
    }

    // Problem methods (similar to before)
    private static void listAllProblems() {
        List<String> problems = problemManager.getAvailableProblems();
        Collections.sort(problems);

        System.out.println("\n=== ALL PROBLEMS ===");
        if (problems.isEmpty()) {
            System.out.println("No problems found.");
            return;
        }

        for (int i = 0; i < problems.size(); i++) {
            String problemName = problems.get(i);
            String category = problemManager.getProblemCategories().get(problemName);
            System.out.printf("%2d. %-25s [%s]%n", i + 1,
                    HelperMethods.formatProblemName(problemName), category);
        }

        promptToRunProblem(problems);
    }

    private static void listProblemsByCategory() {
        Map<String, List<String>> categorized = problemManager.getProblemsByCategory();

        System.out.println("\n=== PROBLEMS BY CATEGORY ===");
        if (categorized.isEmpty()) {
            System.out.println("No problems found.");
            return;
        }

        List<String> allProblems = new ArrayList<>();
        int counter = 1;

        for (Map.Entry<String, List<String>> entry : categorized.entrySet()) {
            String category = entry.getKey();
            List<String> problems = entry.getValue();

            System.out.println("\n--- " + category + " ---");
            for (String problem : problems) {
                System.out.printf("%2d. %s%n", counter, HelperMethods.formatProblemName(problem));
                allProblems.add(problem);
                counter++;
            }
        }

        promptToRunProblem(allProblems);
    }

    private static void runSpecificProblem() {
        List<String> problems = problemManager.getAvailableProblems();
        Collections.sort(problems);

        System.out.println("\n=== RUN SPECIFIC PROBLEM ===");
        System.out.print("Enter problem name: ");
        String userInput = scanner.nextLine().trim();

        String normalizedInput = HelperMethods.normalizeProblemName(userInput);
        String foundProblem = HelperMethods.findMatchingProblem(problems, normalizedInput);

        if (foundProblem != null) {
            problemManager.runProblem(foundProblem);
        } else {
            System.out.println("\n❌ Problem not found: " + userInput);
            showAvailableItems("Problems", problems);
        }

        waitForEnter();
    }

    // Practice methods
    private static void listAllPractices() {
        List<String> practices = problemManager.getAvailablePractices();
        Collections.sort(practices);

        System.out.println("\n=== ALL PRACTICES ===");
        if (practices.isEmpty()) {
            System.out.println("No practices found.");
            return;
        }

        for (int i = 0; i < practices.size(); i++) {
            String practiceName = practices.get(i);
            String category = problemManager.getPracticeCategories().get(practiceName);
            System.out.printf("%2d. %-25s [%s]%n", i + 1,
                    HelperMethods.formatProblemName(practiceName), category);
        }

        promptToRunPractice(practices);
    }

    private static void listPracticesByCategory() {
        Map<String, List<String>> categorized = problemManager.getPracticesByCategory();

        System.out.println("\n=== PRACTICES BY CATEGORY ===");
        if (categorized.isEmpty()) {
            System.out.println("No practices found.");
            return;
        }

        List<String> allPractices = new ArrayList<>();
        int counter = 1;

        for (Map.Entry<String, List<String>> entry : categorized.entrySet()) {
            String category = entry.getKey();
            List<String> practices = entry.getValue();

            System.out.println("\n--- " + category + " ---");
            for (String practice : practices) {
                System.out.printf("%2d. %s%n", counter, HelperMethods.formatProblemName(practice));
                allPractices.add(practice);
                counter++;
            }
        }

        promptToRunPractice(allPractices);
    }

    private static void runSpecificPractice() {
        List<String> practices = problemManager.getAvailablePractices();
        Collections.sort(practices);

        System.out.println("\n=== RUN SPECIFIC PRACTICE ===");
        System.out.print("Enter practice name: ");
        String userInput = scanner.nextLine().trim();

        String normalizedInput = HelperMethods.normalizeProblemName(userInput);
        String foundPractice = HelperMethods.findMatchingProblem(practices, normalizedInput);

        if (foundPractice != null) {
            problemManager.runPractice(foundPractice);
        } else {
            System.out.println("\n❌ Practice not found: " + userInput);
            showAvailableItems("Practices", practices);
        }

        waitForEnter();
    }

    // Helper methods
    private static void promptToRunProblem(List<String> problems) {
        promptToRunItem(problems, "problem");
    }

    private static void promptToRunPractice(List<String> practices) {
        promptToRunItem(practices, "practice");
    }

    private static void promptToRunItem(List<String> items, String type) {
        if (items.isEmpty()) {
            return;
        }

        System.out.print("\nEnter " + type + " number to run (0 to go back): ");
        String input = scanner.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);
            if (choice == 0) {
                return;
            }
            if (choice >= 1 && choice <= items.size()) {
                String itemName = items.get(choice - 1);
                if (type.equals("problem")) {
                    problemManager.runProblem(itemName);
                } else {
                    problemManager.runPractice(itemName);
                }
            } else {
                System.out.println("Invalid number. Please choose between 1-" + items.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }

        waitForEnter();
    }

    private static void showAvailableItems(String type, List<String> items) {
        System.out.println("\n📋 Available " + type.toLowerCase() + ":");

        List<String> formattedItems = HelperMethods.formatProblemNames(items);
        for (int i = 0; i < formattedItems.size(); i++) {
            System.out.printf("  %2d. %s%n", i + 1, formattedItems.get(i));
        }

        System.out.println("\n🔍 Quick reference: " + String.join(", ", formattedItems));
    }

    private static void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}