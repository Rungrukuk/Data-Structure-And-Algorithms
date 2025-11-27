package DataStructureAndAlgorithms.services;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import DataStructureAndAlgorithms.exceptions.InvalidInputException;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class InputService {
    private Scanner scanner;

    public InputService() {
        scanner = new Scanner(System.in);
    }

    public String getProblemName() {
        String problemName = scanner.nextLine();
        problemName = NamingUtils.formatProblemName(problemName);
        if (problemName == null || problemName.isBlank()) {
            throw new InvalidInputException("Problem name cannot be empty.");
        }
        return problemName;
    }

    public String getCategory() {
        return null;
    }

    public String getReturnType() {
        return null;
    }

    public String selectFromList(List<String> options) {
        String line = scanner.nextLine();

        int optionNumber;
        try {
            optionNumber = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Input must be a number between 1 and " + options.size());
        }

        if (optionNumber < 1 || optionNumber > options.size()) {
            throw new InvalidInputException("Input must be a number between 1 and " + options.size());
        }

        return options.get(optionNumber - 1);
    }

    public String selectFromCategory(Map<String, List<String>> categorizedOptions) {
        return null;
    }

    public boolean getConfirmation(String message) {
        return false;
    }

    public void shutDownScanner() {
        this.scanner.close();
    }

    public void continueTheProgram() {
        scanner.nextLine();
    }
}
