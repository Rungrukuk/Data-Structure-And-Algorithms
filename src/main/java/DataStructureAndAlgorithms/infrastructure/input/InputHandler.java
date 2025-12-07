package DataStructureAndAlgorithms.infrastructure.input;

import DataStructureAndAlgorithms.core.exceptions.ValidationException;
import DataStructureAndAlgorithms.utils.ApplicationConstants;
import DataStructureAndAlgorithms.utils.NameFormatter;
import DataStructureAndAlgorithms.utils.TypeValidator;

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
        if (name.isBlank()) {
            throw new ValidationException("Input cannot be empty.");
        }
        return NameFormatter.formatInput(name);
    }

    public String readReturnType() {
        String input = readLine();
        if (input.isBlank()) {
            throw new ValidationException("Return type cannot be empty.");
        }
        if (input.equals(ApplicationConstants.RETURN_BACK)) {
            return input;
        }

        if (!TypeValidator.isValidJavaType(input)) {
            throw new ValidationException("Invalid return type format.");
        }

        String convertedType = TypeValidator.convertToWrapperType(input);

        if (!TypeValidator.isValidJavaType(convertedType)) {
            throw new ValidationException("Invalid Java type after conversion");
        }

        return convertedType;
    }

    public Boolean readConfirmation() {
        String input = readLine().toUpperCase();
        if (input.isBlank()) {
            throw new ValidationException("Input cannot be empty.");
        }
        if (input.equals("Y") || input.equals("YES")) {
            return true;
        } else if (input.equals("N") || input.equals("NO")) {
            return false;
        } else {
            throw new ValidationException("Invalid input. Please enter Y or N.");
        }
    }

    public int readInt() {
        String line = readLine();
        if (line.isBlank()) {
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
