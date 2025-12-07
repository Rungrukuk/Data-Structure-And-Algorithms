package DataStructureAndAlgorithms.utils;

public final class NameFormatter {
    private NameFormatter() {
    }

    public static String formatInput(String input) {
        String withSpaces = input.replaceAll("[_-]", " ");

        String[] rawWords = withSpaces.split("\\s+");
        StringBuilder formatted = new StringBuilder();

        for (String rawWord : rawWords) {
            if (rawWord.isEmpty())
                continue;

            String camelSplit = rawWord.replaceAll("([a-z])([A-Z])", "$1 $2");

            for (String word : camelSplit.split(" ")) {
                if (word.isEmpty())
                    continue;

                if (!formatted.isEmpty())
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

    public static String convertId(String id) {
        return id.replace(" ", "");
    }

    public static String convertClassNameToPath(String className) {
        return className.replace(".", "/");
    }

    public static String generateProblemSimpleClassName(String problemName) {
        String formattedName = formatInput(problemName);
        return formattedName.replace(" ", "");
    }

    public static String generatePracticeSimpleClassName(String problemName) {
        return generateProblemSimpleClassName(problemName) + ApplicationConstants.PRACTICE_CLASS_SUFFIX;
    }

    public static String generateCategoryFolderName(String category) {
        String formattedCategory = formatInput(category);
        return formattedCategory.replace(" ", "");
    }
}
