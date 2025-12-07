package DataStructureAndAlgorithms.utils;

import java.util.Arrays;

public class ResultFormatter {
    public static String format(Object value) {
        if (value == null)
            return "null";

        Class<?> clazz = value.getClass();

        // Primitive arrays
        if (clazz.isArray()) {
            if (clazz == int[].class)
                return Arrays.toString((int[]) value);
            if (clazz == long[].class)
                return Arrays.toString((long[]) value);
            if (clazz == double[].class)
                return Arrays.toString((double[]) value);
            if (clazz == byte[].class)
                return Arrays.toString((byte[]) value);
            if (clazz == char[].class)
                return Arrays.toString((char[]) value);
            if (clazz == float[].class)
                return Arrays.toString((float[]) value);
            if (clazz == short[].class)
                return Arrays.toString((short[]) value);
            if (clazz == boolean[].class)
                return Arrays.toString((boolean[]) value);

            return Arrays.deepToString((Object[]) value);
        }

        return value.toString();
    }

}
