package DataStructureAndAlgorithms.utils;

public final class NamingUtils {
    private NamingUtils() {
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

    public static String generateCategoryFolderName(String category) {
        String formattedCategory = formatProblemName(category); // TODO check if there are any need to formatProblemName
        return formattedCategory.replace(" ", "");
    }

    public static String convertId(String id) {
        return id.replace(" ", "");
    }

    public static String convertClassNameToPath(String className) {
        return className.replace(".", "/");
    }
}
