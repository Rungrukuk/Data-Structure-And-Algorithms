package DataStructureAndAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import DataStructureAndAlgorithms.utils.HelperMethods;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static Problem_Manager problemManager;

    public static void main(String[] args) {
        problemManager = new Problem_Manager();

        System.out.println("=========================================");
        System.out.println("    DATA STRUCTURES & ALGORITHMS");
        System.out.println("          SOLUTION RUNNER");
        System.out.println("=========================================");

        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. List all problems");
            System.out.println("2. List problems by category");
            System.out.println("3. Run a specific problem");
            System.out.println("4. Exit");
            System.out.print("\nChoose an option (1-4): ");

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
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1-4.");
            }
        }
    }

    private static void listAllProblems() {
        List<String> problems = problemManager.getAvailableProblems();
        Collections.sort(problems);

        System.out.println("\n=== ALL SOLUTIONS ===");
        if (problems.isEmpty()) {
            System.out.println("No problems found.");
            return;
        }

        for (int i = 0; i < problems.size(); i++) {
            String problemName = problems.get(i);
            String category = problemManager.getProblemCategories().get(problemName);
            System.out.printf("%2d. %-25s [%s]%n", i + 1, problemName, category);
        }

        promptToRunProblem(problems);
    }

    private static void listProblemsByCategory() {
        Map<String, List<String>> categorized = problemManager.getProblemsByCategory();

        System.out.println("\n=== SOLUTIONS BY CATEGORY ===");
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
                System.out.printf("%2d. %s%n", counter, problem);
                allProblems.add(problem);
                counter++;
            }
        }

        promptToRunProblem(allProblems);
    }

    private static void runSpecificProblem() {
        List<String> problems = problemManager.getAvailableProblems();
        Collections.sort(problems);

        System.out.println("\n=== RUN SPECIFIC SOLUTION ===");
        System.out.print("Enter problem name: ");
        String userInput = scanner.nextLine().trim();

        String normalizedInput = HelperMethods.normalizeProblemName(userInput);

        String foundProblem = HelperMethods.findMatchingProblem(problems, normalizedInput);

        if (foundProblem != null) {
            problemManager.runProblem(foundProblem);
        } else {
            System.out.println("\n❌ Problem not found: " + userInput);
            System.out.println("\n📋 Available problems:");

            List<String> formattedProblems = HelperMethods.formatProblemNames(problems);
            for (int i = 0; i < formattedProblems.size(); i++) {
                System.out.printf("  %2d. %s%n", i + 1, formattedProblems.get(i));
            }

            System.out.println("\n🔍 Quick reference: " + String.join(", ", formattedProblems));
        }

        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void promptToRunProblem(List<String> problems) {
        if (problems.isEmpty()) {
            return;
        }

        System.out.print("\nEnter problem number to run (0 to go back): ");
        String input = scanner.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);
            if (choice == 0) {
                return;
            }
            if (choice >= 1 && choice <= problems.size()) {
                String problemName = problems.get(choice - 1);
                problemManager.runProblem(problemName);
            } else {
                System.out.println("Invalid number. Please choose between 1-" + problems.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }

        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
