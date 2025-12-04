package DataStructureAndAlgorithms.infrastructure.input;

import DataStructureAndAlgorithms.utils.naming.NameFormatter;
import DataStructureAndAlgorithms.core.exceptions.ValidationException;
import DataStructureAndAlgorithms.utils.TypeResolver.TypeResolver;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    // ========================= CORE INPUT METHODS =========================

    private String readLine() {
        return scanner.nextLine().trim();
    }
    // ========================= SPECIFIC VALIDATORS =========================

    public String readName() {
        String name = readLine();
        if (name == null || name.isBlank()) {
            throw new ValidationException("Input cannot be empty.");
        }
        return NameFormatter.formatInput(name);
    }

    public String readReturnType() {
        String input = readLine();
        if (input == null || input.isEmpty()) {
            throw new ValidationException("Return type cannot be empty.");
        }
        if (input.equals(ApplicationConstants.RETURN_BACK)) {
            return input;
        }

        if (!TypeResolver.isValidJavaType(input)) {
            throw new ValidationException("Invalid return type format.");
        }

        String convertedType = TypeResolver.convertToWrapperType(input);
        if (!TypeResolver.isValidJavaType(convertedType)) {
            throw new ValidationException("Invalid Java type after conversion");
        }
        return convertedType;
    }

    public int readInt() {
        String line = readLine();
        if (line == null || line.isBlank()) {
            throw new ValidationException("Input cannot be empty.");
        }
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new ValidationException("Input must be a valid number");
        }
    }

    public void waitForEnter() {
        scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}