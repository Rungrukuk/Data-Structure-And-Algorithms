package DataStructureAndAlgorithms.utils;

public final class NamingUtils {
    private NamingUtils() {
    }

    private static String formatInput(String input) {

        String withSpaces = input.replaceAll("[_-]", " ");

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

    public static String generateFormattedCategoryName(String category) {
        return formatInput(category);
    }

    public static String generateFormattedProblemName(String problemName) {
        return formatInput(problemName);
    }

    public static String convertId(String id) {
        return id.replace(" ", "");
    }

    public static String convertClassNameToPath(String className) {
        return className.replace(".", "/");
    }

    public static String generateSimpleClassName(String problemName) {
        String formattedName = formatInput(problemName);
        return formattedName.replace(" ", "");
    }

    public static String generateCategoryFolderName(String category) {
        String formattedCategory = formatInput(category);
        return formattedCategory.replace(" ", "");
    }

    public static String generateClassName(String problemOrPracticeName, String category,
            String problemOrPracticePackage) {
        return problemOrPracticePackage + Constants.DOT_SEPERATOR + generateCategoryFolderName(category)
                + Constants.DOT_SEPERATOR + generateSimpleClassName(problemOrPracticeName);
    }
}
