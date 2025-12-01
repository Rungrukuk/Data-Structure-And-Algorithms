package DataStructureAndAlgorithms.infrastructure.input;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import DataStructureAndAlgorithms.exceptions.InvalidInputException;
import DataStructureAndAlgorithms.utils.TypeResolver.TypeResolver;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

public class InputService_OLD {
    private Scanner scanner;

    public InputService_OLD() {
        scanner = new Scanner(System.in);
    }

    public String getNameInput() {
        String name = scanner.nextLine().trim();
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Input cannot be empty.");
        }
        name = NameFormatter.formatInput(name);
        return name;
    }

    public String getReturnType() {
        String input = scanner.nextLine().trim();

        if (input == null || input.isEmpty()) {
            throw new InvalidInputException("Return type cannot be empty.");
        }

        if (!TypeResolver.isValidJavaType(input)) {
            throw new InvalidInputException("Invalid return type format.");
        }
        String convertedType = TypeResolver.convertToWrapperType(input);
        if (!TypeResolver.isValidJavaType(convertedType)) {
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
