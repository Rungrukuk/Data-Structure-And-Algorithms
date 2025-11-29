package DataStructureAndAlgorithms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReturnTypeUtils {
    private ReturnTypeUtils() {
    }

    public static boolean isValidJavaType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }

        String trimmedType = type.trim();

        if (trimmedType.matches(".*[^a-zA-Z0-9_<>\\[\\],\\s].*")) {
            return false;
        }

        if (hasPrimitiveInGenerics(trimmedType)) {
            return false;
        }

        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*$")) {
            return true;
        }

        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*\\[\\]$")) {
            return true;
        }

        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*<[a-zA-Z0-9_,\\s<>\\[\\]]+>$")) {
            return validateGenericParameters(trimmedType);
        }

        if (trimmedType.matches("^[A-Z][a-zA-Z0-9_]*((\\[\\])+)$")) {
            return true;
        }

        return false;
    }

    private static boolean hasPrimitiveInGenerics(String type) {
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(type);

        while (matcher.find()) {
            String genericContent = matcher.group(1);
            String[] typeParams = splitGenericParameters(genericContent);
            for (String param : typeParams) {
                String trimmedParam = param.trim();
                if (isPrimitiveType(trimmedParam)) {
                    return true;
                }
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

        if (trimmedType.endsWith("[]")) {
            String baseType = trimmedType.substring(0, trimmedType.length() - 2);
            String wrapperBaseType = convertToWrapperType(baseType);
            return wrapperBaseType + "[]";
        }

        if (trimmedType.contains("<")) {
            int angleIndex = trimmedType.indexOf('<');
            String baseType = trimmedType.substring(0, angleIndex);
            String genericPart = trimmedType.substring(angleIndex);

            String convertedGenericPart = convertGenericParameters(genericPart);
            return baseType + convertedGenericPart;
        }

        return convertSimpleType(trimmedType);
    }

    private static String convertGenericParameters(String genericPart) {
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
