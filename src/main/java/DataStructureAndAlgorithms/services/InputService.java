package DataStructureAndAlgorithms.services;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import DataStructureAndAlgorithms.exceptions.InvalidInputException;
import DataStructureAndAlgorithms.utils.NamingUtils;
import DataStructureAndAlgorithms.utils.ReturnTypeUtils;

public class InputService {
    private Scanner scanner;

    public InputService() {
        scanner = new Scanner(System.in);
    }

    public String getProblemOrPracticeName() {
        String problemName = scanner.nextLine().trim();
        if (problemName == null || problemName.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
        problemName = NamingUtils.generateFormattedProblemName(problemName);
        return problemName;
    }

    public String getCategory() {
        String category = scanner.nextLine().trim();
        if (category == null || category.isEmpty()) {
            throw new InvalidInputException("Category cannot be empty.");
        }
        category = NamingUtils.generateFormattedCategoryName(category);
        return category;
    }

    public String getReturnType() {
        String input = scanner.nextLine().trim();

        if (input == null || input.isEmpty()) {
            throw new InvalidInputException("Return type cannot be empty.");
        }

        if (!ReturnTypeUtils.isValidJavaType(input)) {
            throw new InvalidInputException("Invalid return type format.");
        }
        String convertedType = ReturnTypeUtils.convertToWrapperType(input);
        if (!ReturnTypeUtils.isValidJavaType(convertedType)) {
            throw new InvalidInputException("Invalid Java type after conversion: '" + convertedType
                    + "'. Please enter a different type.");
        }
        return convertedType;
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
