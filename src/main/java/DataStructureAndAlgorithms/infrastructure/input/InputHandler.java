package DataStructureAndAlgorithms.infrastructure.input;

import DataStructureAndAlgorithms.utils.naming.NameFormatter;
import DataStructureAndAlgorithms.core.exceptions.ValidationException;
import DataStructureAndAlgorithms.utils.TypeResolver.TypeResolver;

import java.util.Scanner;
import java.util.function.Supplier;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    // ========================= CORE INPUT METHODS =========================

    private String readLine() {
        return scanner.nextLine().trim();
    }

    public String readValidatedInput(Supplier<String> validator) {
        String result = validator.get();
        if (result == null || result.trim().isEmpty()) {
            throw new ValidationException("Input cannot be empty");
        }
        return result;
    }

    public <T> T readValidatedInputWithRetry(Supplier<T> validator,
            Supplier<String> errorMessageSupplier) {
        while (true) {
            try {
                return validator.get();
            } catch (ValidationException e) {
                throw new ValidationException(errorMessageSupplier.get() + ": " + e.getMessage());
            } catch (Exception e) {
                throw new ValidationException(errorMessageSupplier.get());
            }
        }
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