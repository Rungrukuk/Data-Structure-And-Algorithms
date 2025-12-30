package DataStructureAndAlgorithms.utils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeValidator {
    private static final String[] PRIMITIVE_TYPES = {
            "int", "long", "double", "float", "boolean", "char", "byte", "short", "void"
    };
    private static final String[] WRAPPER_TYPES = {
            "Integer", "Long", "Double", "Float", "Boolean", "Character", "Byte", "Short", "Void"
    };
    private static final Set<String> KNOWN_JAVA_TYPES = new HashSet<>(Arrays.asList(
            "int", "long", "double", "float", "boolean", "char", "byte", "short", "void",
            "Integer", "Long", "Double", "Float", "Boolean", "Character", "Byte", "Short", "Void",
            "String", "Object", "Class", "Number", "Comparable", "Serializable",
            "List", "Set", "Map", "Collection", "Iterable", "Iterator",
            "ArrayList", "LinkedList", "Vector", "Stack",
            "HashSet", "TreeSet", "LinkedHashSet",
            "HashMap", "TreeMap", "LinkedHashMap", "Hashtable", "ConcurrentHashMap",
            "Queue", "Deque", "ArrayDeque", "PriorityQueue",
            "Optional", "Stream", "IntStream", "LongStream", "DoubleStream",
            "BigInteger", "BigDecimal",
            "LocalDate", "LocalDateTime", "LocalTime", "ZonedDateTime", "Instant", "Duration", "Period",
            "UUID"));

    private TypeValidator() {
    }

    public static boolean isValidJavaType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }

        String trimmedType = type.trim();

        // Check for invalid characters
        if (trimmedType.matches(".*[^a-zA-Z0-9_<>\\[\\]?,\\s.].*")) {
            return false;
        }

        // Check for primitives in generics
        if (hasPrimitiveInGenerics(trimmedType)) {
            return false;
        }

        // Handle arrays
        if (trimmedType.endsWith("[]")) {
            String baseWithoutArrays = trimmedType.replaceAll("\\[]", "");
            return isValidJavaType(baseWithoutArrays);
        }

        // Handle generic types
        if (trimmedType.contains("<")) {
            return validateGenericType(trimmedType);
        }

        // Handle simple types
        String typeName = extractSimpleName(trimmedType);
        return KNOWN_JAVA_TYPES.contains(typeName);
    }

    private static boolean validateGenericType(String type) {
        String baseType = type;
        while (baseType.endsWith("[]")) {
            baseType = baseType.substring(0, baseType.length() - 2);
        }

        // Find the first '<' and its matching '>'
        int firstAngle = baseType.indexOf('<');
        if (firstAngle == -1) {
            return false;
        }

        // Extract the base type name (before the first '<')
        String typeName = baseType.substring(0, firstAngle).trim();

        // Extract the generic part (everything between the first '<' and last '>')
        // We need to find the matching closing '>' by counting angle brackets
        int angleCount = 0;
        int lastAngle = -1;
        for (int i = firstAngle; i < baseType.length(); i++) {
            char c = baseType.charAt(i);
            if (c == '<') {
                angleCount++;
            } else if (c == '>') {
                angleCount--;
                if (angleCount == 0) {
                    lastAngle = i;
                    break;
                }
            }
        }

        if (lastAngle == -1 || lastAngle != baseType.length() - 1) {
            return false; // Unbalanced angle brackets or extra characters after last '>'
        }

        String parameters = baseType.substring(firstAngle + 1, lastAngle);

        if (!isKnownType(typeName)) {
            return false;
        }

        return validateGenericParameters(parameters);
    }

    private static boolean isKnownType(String type) {
        String simpleName = extractSimpleName(type);
        return KNOWN_JAVA_TYPES.contains(simpleName);
    }

    private static String extractSimpleName(String type) {
        int lastDot = type.lastIndexOf('.');
        if (lastDot > 0) {
            return type.substring(lastDot + 1);
        }
        return type;
    }

    private static boolean hasPrimitiveInGenerics(String type) {
        // Find all generic sections
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(type);

        while (matcher.find()) {
            String genericContent = matcher.group(1);
            String[] typeParams = splitGenericParameters(genericContent);
            for (String param : typeParams) {
                String trimmedParam = param.trim();
                if (trimmedParam.startsWith("?")) {
                    // Skip wildcards for primitive check
                    continue;
                }

                // For primitive arrays, they are valid in generics
                // Check if it's a primitive type (not array)
                if (isPrimitive(trimmedParam) && !trimmedParam.contains("[]")) {
                    return true;
                }

                // Check if the type without arrays is primitive
                String paramWithoutArrays = trimmedParam.replaceAll("\\[]", "");
                if (isPrimitive(paramWithoutArrays)) {
                    // If it's a primitive array (like int[]), it's allowed
                    // If it's just a primitive (like int), it's not allowed
                    // Since we already checked for non-array primitives above,
                    // this case handles nested checks
                    continue;
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

    private static boolean isPrimitive(String type) {
        String typeWithoutArrays = type.replaceAll("\\[]", "");
        for (String primitive : PRIMITIVE_TYPES) {
            if (primitive.equals(typeWithoutArrays)) {
                return true;
            }
        }
        return false;
    }

    private static String[] splitGenericParameters(String genericContent) {
        List<String> params = new ArrayList<>();
        int depth = 0;
        int start = 0;

        for (int i = 0; i < genericContent.length(); i++) {
            char c = genericContent.charAt(i);
            if (c == '<') {
                depth++;
            } else if (c == '>') {
                depth--;
            } else if (c == ',' && depth == 0) {
                params.add(genericContent.substring(start, i).trim());
                start = i + 1;
            }
        }

        if (start < genericContent.length()) {
            params.add(genericContent.substring(start).trim());
        }

        return params.toArray(new String[0]);
    }

    private static boolean validateGenericParameters(String parameters) {
        String[] typeParams = splitGenericParameters(parameters);

        for (String param : typeParams) {
            String trimmedParam = param.trim();

            if (trimmedParam.startsWith("?")) {
                // Handle wildcards
                if (trimmedParam.equals("?")) {
                    continue; // Unbounded wildcard is valid
                }

                // Check for wildcard with bounds
                if (trimmedParam.matches("^\\?\\s+(extends|super)\\s+.+$")) {
                    // Extract the bound type
                    int extendsIndex = trimmedParam.indexOf("extends");
                    int superIndex = trimmedParam.indexOf("super");
                    int boundStart = Math.max(extendsIndex, superIndex);

                    if (boundStart > 0) {
                        String boundType = trimmedParam.substring(boundStart +
                                (extendsIndex > 0 ? "extends".length() : "super".length())).trim();

                        // Validate the bound type
                        if (!isValidJavaType(boundType)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false; // Invalid wildcard syntax
                }
            } else {
                // Regular type parameter
                if (!isValidJavaType(trimmedParam)) {
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

        // Handle arrays - don't convert int[] to Integer[]
        if (trimmedType.endsWith("[]")) {
            String baseType = trimmedType.substring(0, trimmedType.length() - 2);
            // Check if base type is a primitive
            if (isPrimitive(baseType)) {
                // For primitive arrays, keep them as is (int[] stays int[])
                return trimmedType;
            } else {
                // For non-primitive arrays, convert the base type
                String convertedBase = convertToWrapperType(baseType);
                return convertedBase + "[]";
            }
        }

        // Handle generic types
        if (trimmedType.contains("<")) {
            int firstAngle = trimmedType.indexOf('<');
            String baseType = trimmedType.substring(0, firstAngle);
            String genericPart = trimmedType.substring(firstAngle);
            String convertedBase = convertToWrapperType(baseType);
            String convertedGenericPart = convertGenericParameters(genericPart);
            return convertedBase + convertedGenericPart;
        }

        // Handle primitive types (non-array, non-generic)
        for (int i = 0; i < PRIMITIVE_TYPES.length; i++) {
            if (PRIMITIVE_TYPES[i].equals(trimmedType)) {
                return WRAPPER_TYPES[i];
            }
        }

        // Already a wrapper or other type
        return trimmedType;
    }

    private static String convertGenericParameters(String genericPart) {
        // Extract the content inside the angle brackets
        int firstAngle = genericPart.indexOf('<');
        if (firstAngle == -1) {
            return genericPart;
        }

        // Find matching closing bracket
        int angleCount = 0;
        int lastAngle = -1;
        for (int i = firstAngle; i < genericPart.length(); i++) {
            char c = genericPart.charAt(i);
            if (c == '<') {
                angleCount++;
            } else if (c == '>') {
                angleCount--;
                if (angleCount == 0) {
                    lastAngle = i;
                    break;
                }
            }
        }

        if (lastAngle == -1) {
            return genericPart; // Should not happen for valid input
        }

        String parameters = genericPart.substring(firstAngle + 1, lastAngle);
        String[] typeParams = splitGenericParameters(parameters);

        StringBuilder convertedParams = new StringBuilder();
        for (int i = 0; i < typeParams.length; i++) {
            if (i > 0) {
                convertedParams.append(", ");
            }
            String param = typeParams[i].trim();
            if (param.startsWith("?")) {
                // Preserve wildcards
                convertedParams.append(param);
            } else {
                convertedParams.append(convertToWrapperType(param));
            }
        }

        // Handle nested generics in the rest of the string
        String afterGenerics = genericPart.substring(lastAngle + 1);
        return "<" + convertedParams + ">" + convertGenericParameters(afterGenerics);
    }

    public static String getSimpleTypeName(Type type) {
        switch (type) {
            case Class<?> clazz -> {
                if (clazz.isArray()) {
                    return getSimpleTypeName(clazz.getComponentType()) + "[]";
                }
                return clazz.getSimpleName();
            }
            case ParameterizedType pType -> {
                Type raw = pType.getRawType();
                Type[] args = pType.getActualTypeArguments();

                StringBuilder sb = new StringBuilder();
                sb.append(getSimpleTypeName(raw));
                sb.append("<");
                for (int i = 0; i < args.length; i++) {
                    sb.append(getSimpleTypeName(args[i]));
                    if (i < args.length - 1)
                        sb.append(", ");
                }
                sb.append(">");
                return sb.toString();
            }
            case GenericArrayType gArray -> {
                return getSimpleTypeName(gArray.getGenericComponentType()) + "[]";
            }
            default -> {
                String name = type.getTypeName();
                int lastDot = name.lastIndexOf('.');
                return lastDot == -1 ? name : name.substring(lastDot + 1);
            }
        }
    }
}
