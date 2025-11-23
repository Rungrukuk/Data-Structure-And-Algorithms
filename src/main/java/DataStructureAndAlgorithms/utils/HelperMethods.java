package DataStructureAndAlgorithms.utils;

import java.util.ArrayList;
import java.util.List;

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
}
