package DataStructureAndAlgorithms.infrastructure.input;

import DataStructureAndAlgorithms.utils.naming.NameFormatter;
import DataStructureAndAlgorithms.core.exceptions.ValidationException;
import DataStructureAndAlgorithms.utils.TypeResolver.TypeResolver;

import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine().trim();
    }

    public String readProblemName() {
        String name = readLine();
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }
        return NameFormatter.generateFormattedProblemName(name);
    }

    public String readCategory() {
        String category = readLine();
        if (category == null || category.isEmpty()) {
            throw new ValidationException("Category cannot be empty.");
        }
        return NameFormatter.generateFormattedCategoryName(category);
    }

    public String readReturnType() {
        String input = readLine();
        if (input == null || input.isEmpty()) {
            throw new ValidationException("Return type cannot be empty.");
        }

        if (!TypeResolver.isValidJavaType(input)) {
            throw new ValidationException("Invalid return type format.");
        }

        String convertedType = TypeResolver.convertToWrapperType(input);
        if (!TypeResolver.isValidJavaType(convertedType)) {
            throw new ValidationException(
                    "Invalid Java type after conversion: '" + convertedType + "'. Please enter a different type.");
        }
        return convertedType;
    }

    public String selectFromList(List<String> options) {
        String line = readLine();

        int optionNumber;
        try {
            optionNumber = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new ValidationException("Input must be a number between 1 and " + options.size());
        }

        if (optionNumber < 1 || optionNumber > options.size()) {
            throw new ValidationException("Input must be a number between 1 and " + options.size());
        }

        return options.get(optionNumber - 1);
    }

    public void waitForEnter() {
        scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}