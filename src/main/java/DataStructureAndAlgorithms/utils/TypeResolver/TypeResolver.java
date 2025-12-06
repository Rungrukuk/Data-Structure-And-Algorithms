package DataStructureAndAlgorithms.utils.TypeResolver;

import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeResolver {
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

    private TypeResolver() {
    }

    public static boolean isValidJavaType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }

        String trimmedType = type.trim();

        if (trimmedType.matches(".*[^a-zA-Z0-9_<>\\[\\]?,\\s.].*")) {
            return false;
        }

        if (hasPrimitiveInGenerics(trimmedType)) {
            return false;
        }

        if (trimmedType.endsWith("[]")) {
            String baseWithoutArrays = trimmedType.replaceAll("\\[]", "");
            return isValidJavaType(baseWithoutArrays);
        }

        if (trimmedType.contains("<")) {
            return validateGenericType(trimmedType);
        }

        String typeName = extractSimpleName(trimmedType);
        return KNOWN_JAVA_TYPES.contains(typeName);
    }

    private static boolean validateGenericType(String type) {
        String baseType = type;
        while (baseType.endsWith("[]")) {
            baseType = baseType.substring(0, baseType.length() - 2);
        }

        Pattern pattern = Pattern.compile("^([a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)*)\\s*<(.+)>$");
        Matcher matcher = pattern.matcher(baseType);

        if (!matcher.matches()) {
            return false;
        }

        String typeName = matcher.group(1);
        String parameters = matcher.group(3);

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
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(type);

        while (matcher.find()) {
            String genericContent = matcher.group(1);
            String[] typeParams = splitGenericParameters(genericContent);
            for (String param : typeParams) {
                String trimmedParam = param.trim();
                if (trimmedParam.startsWith("?")) {
                    continue;
                }
                if (isPrimitive(trimmedParam)) {
                    return true;
                }
                String paramWithoutArrays = trimmedParam.replaceAll("\\[]", "");
                if (isPrimitive(paramWithoutArrays)) {
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
                if (trimmedParam.equals("?")) {
                    continue;
                }
                if (trimmedParam.matches("^\\?\\s+extends\\s+.+$")) {
                    String bound = trimmedParam.substring(trimmedParam.indexOf("extends") + 7).trim();
                    if (!isValidJavaType(bound)) {
                        return false;
                    }
                } else if (trimmedParam.matches("^\\?\\s+super\\s+.+$")) {
                    String bound = trimmedParam.substring(trimmedParam.indexOf("super") + 5).trim();
                    if (!isValidJavaType(bound)) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
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

        for (int i = 0; i < PRIMITIVE_TYPES.length; i++) {
            if (PRIMITIVE_TYPES[i].equals(trimmedType)) {
                return WRAPPER_TYPES[i];
            }
        }
        return trimmedType;
    }

    private static String convertGenericParameters(String genericPart) {
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(genericPart);

        if (matcher.find()) {
            String genericContent = matcher.group(1);
            String[] typeParams = splitGenericParameters(genericContent);

            StringBuilder convertedParams = new StringBuilder();
            for (int i = 0; i < typeParams.length; i++) {
                if (i > 0) {
                    convertedParams.append(", ");
                }
                String param = typeParams[i].trim();
                if (param.startsWith("?")) {
                    convertedParams.append(param);
                } else {
                    convertedParams.append(convertToWrapperType(param));
                }
            }

            return "<" + convertedParams + ">";
        }

        return genericPart;
    }

    public static String getSimpleTypeName(Type type) {
        if (type instanceof Class<?>) {
            return ((Class<?>) type).getSimpleName();
        }
        String name = type.getTypeName();
        int lastDot = name.lastIndexOf('.');
        return lastDot == -1 ? name : name.substring(lastDot + 1);
    }
}
