package DataStructureAndAlgorithms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class HelperMethods {
    public static String normalizeProblemName(String input) {
        return input.replaceAll("[\\s\\-_]", "").toLowerCase();
    }

    public static String findMatchingProblem(List<String> problems, String normalizedInput) {
        for (String problem : problems) {
            String normalizedProblem = normalizeProblemName(problem);

            if (normalizedProblem.equals(normalizedInput)) {
                return problem;
            }

            String formattedProblem = formatProblemName(problem);
            String normalizedFormatted = normalizeProblemName(formattedProblem);
            if (normalizedFormatted.equals(normalizedInput)) {
                return problem;
            }
        }
        return null;
    }

    public static String formatProblemName(String problemName) {
        if (problemName == null || problemName.isEmpty()) {
            return problemName;
        }

        String withSpaces = problemName.replaceAll("[_-]", " ");

        String[] words = withSpaces.split("\\s+");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty())
                continue;

            if (!word.contains(" ") && word.matches(".*[a-z][A-Z].*")) {
                String camelCaseSplit = word.replaceAll("([a-z])([A-Z])", "$1 $2");
                String[] camelWords = camelCaseSplit.split(" ");

                for (String camelWord : camelWords) {
                    if (formatted.length() > 0)
                        formatted.append(" ");
                    formatted.append(capitalizeFirstLetter(camelWord));
                }
            } else {
                if (formatted.length() > 0)
                    formatted.append(" ");
                formatted.append(capitalizeFirstLetter(word));
            }
        }

        return formatted.toString();
    }

    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static List<String> formatProblemNames(List<String> problemNames) {
        List<String> formatted = new ArrayList<>();
        for (String name : problemNames) {
            formatted.add(formatProblemName(name));
        }
        return formatted;
    }

    // Helper methods for Problem Class Creator
    public static String generateClassName(String problemName) {
        String formattedName = formatProblemName(problemName);
        return formattedName.replace(" ", "_");
    }

    public static String generateFormattedProblemName(String problemName) {
        return formatProblemName(problemName);
    }

    public static String generateFormattedCategoryName(String category) {
        return formatProblemName(category);
    }

    public static String generateCategoryFolderName(String category) {
        String formattedCategory = formatProblemName(category);
        return formattedCategory.replace(" ", "_");
    }

    public static boolean isValidJavaType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }

        String trimmedType = type.trim();

        // Check for obvious invalid characters
        if (trimmedType.matches(".*[^a-zA-Z0-9_<>\\[\\],\\s].*")) {
            return false;
        }

        // Check if the type contains any primitive types in generics
        if (hasPrimitiveInGenerics(trimmedType)) {
            return false;
        }

        // Check for common valid patterns
        // Simple types: Integer, String, Boolean, etc.
        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*$")) {
            return true;
        }

        // Array types: Integer[], String[], etc.
        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*\\[\\]$")) {
            return true;
        }

        // Generic types: List<String>, Map<Integer, String>, etc.
        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*<[a-zA-Z0-9_,\\s<>\\[\\]]+>$")) {
            // Recursively validate generic type parameters
            return validateGenericParameters(trimmedType);
        }

        // Multi-dimensional arrays: Integer[][], String[][], etc.
        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*((\\[\\])+)$")) {
            return true;
        }

        return false;
    }

    private static boolean hasPrimitiveInGenerics(String type) {
        // Check if there are any primitive types inside angle brackets
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(type);

        while (matcher.find()) {
            String genericContent = matcher.group(1);
            // Split by commas but handle nested generics
            String[] typeParams = splitGenericParameters(genericContent);
            for (String param : typeParams) {
                String trimmedParam = param.trim();
                if (isPrimitiveType(trimmedParam)) {
                    return true;
                }
                // Recursively check nested generics
                if (trimmedParam.contains("<")) {
                    if (hasPrimitiveInGenerics(trimmedParam)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isPrimitiveType(String type) {
        return type.equals("int") || type.equals("long") || type.equals("double") ||
                type.equals("float") || type.equals("boolean") || type.equals("char") ||
                type.equals("byte") || type.equals("short") || type.equals("void");
    }

    private static String[] splitGenericParameters(String genericContent) {
        List<String> params = new ArrayList<>();
        int depth = 0;
        StringBuilder current = new StringBuilder();

        for (char c : genericContent.toCharArray()) {
            if (c == '<')
                depth++;
            if (c == '>')
                depth--;

            if (c == ',' && depth == 0) {
                params.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            params.add(current.toString().trim());
        }

        return params.toArray(new String[0]);
    }

    private static boolean validateGenericParameters(String type) {
        // Extract content between angle brackets
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(type);

        if (matcher.find()) {
            String genericContent = matcher.group(1);
            String[] typeParams = splitGenericParameters(genericContent);

            for (String param : typeParams) {
                if (!isValidJavaType(param)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static String convertToWrapperType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return type;
        }

        String trimmedType = type.trim();

        // Handle arrays
        if (trimmedType.endsWith("[]")) {
            String baseType = trimmedType.substring(0, trimmedType.length() - 2);
            String wrapperBaseType = convertToWrapperType(baseType);
            return wrapperBaseType + "[]";
        }

        // Handle generic types - recursively convert type parameters
        if (trimmedType.contains("<")) {
            int angleIndex = trimmedType.indexOf('<');
            String baseType = trimmedType.substring(0, angleIndex);
            String genericPart = trimmedType.substring(angleIndex);

            // Convert the generic parameters recursively
            String convertedGenericPart = convertGenericParameters(genericPart);
            return baseType + convertedGenericPart;
        }

        // Handle simple types
        return convertSimpleType(trimmedType);
    }

    private static String convertGenericParameters(String genericPart) {
        // Extract content between angle brackets
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(genericPart);

        if (matcher.find()) {
            String genericContent = matcher.group(1);
            String[] typeParams = splitGenericParameters(genericContent);

            StringBuilder convertedParams = new StringBuilder();
            for (int i = 0; i < typeParams.length; i++) {
                if (i > 0)
                    convertedParams.append(", ");
                convertedParams.append(convertToWrapperType(typeParams[i]));
            }

            return "<" + convertedParams.toString() + ">";
        }

        return genericPart;
    }

    private static String convertSimpleType(String type) {
        switch (type) {
            case "int":
                return "Integer";
            case "long":
                return "Long";
            case "double":
                return "Double";
            case "float":
                return "Float";
            case "boolean":
                return "Boolean";
            case "char":
                return "Character";
            case "byte":
                return "Byte";
            case "short":
                return "Short";
            case "void":
                return "Void";
            default:
                return type;
        }
    }
}