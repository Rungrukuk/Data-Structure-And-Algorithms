package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.exceptions.ProblemCreationException;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;
import DataStructureAndAlgorithms.utils.Constants;
import DataStructureAndAlgorithms.utils.NamingUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ProblemCreator extends BaseClassCreator {

    public ProblemCreator(FileSystemService fileSystemService, ProblemPracticeService problemPracticeService) {
        super(fileSystemService, problemPracticeService);
    }

    public void createProblem(ProblemInfo info) {
        // TODO Update the error messages
        String problemName = info.getName();
        String category = info.getCategory();
        if (problemPracticeService.problemExists(problemName, category)) {
            throw new ProblemCreationException("Problem already exists: " + info.getUniqueId());
        }

        String classContent = generateProblemTemplate(info);
        String filePath = fileSystemService.getProblemFilePath(info);
        String className = NamingUtils.generateClassName(problemName, category, Constants.PROBLEM_PACKAGE);
        info.setFilePath(filePath);
        info.setClassName(className);
        try {
            problemPracticeService.addCreatedProblem(info);
            try {
                fileSystemService.createFile(filePath, classContent);
                info.setFilePath(filePath);
                info.setClassName(className);
            } catch (Exception e) {
                problemPracticeService.removeCreatedProblem(info);
                throw new ProblemCreationException(
                        "Failed to create problem file for: " + info.getUniqueId());
            }

        } catch (Exception e) {
            throw new ProblemCreationException("Failed to create problem: " + info.getUniqueId());
        }
    }

    private String generateProblemTemplate(ProblemInfo info) {
        String simpleClassName = NamingUtils.generateSimpleClassName(info.getName());
        String packageName = Constants.PROBLEM_PACKAGE + Constants.DOT_SEPERATOR +
                NamingUtils.generateCategoryFolderName(info.getCategory());
        String returnType = info.getReturnType();

        String imports = generateImports(returnType);

        return String.format(
                "package %s;\n\n" +
                        "%s\n" +
                        "@Problem(name = \"%s\", category = \"%s\")\n" +
                        "public class %s extends BaseProblem<%s> {\n\n" +
                        "    @Override\n" +
                        "    public %s solve() {\n" +
                        "        // TODO: Implement solution\n" +
                        "        throw new UnsupportedOperationException(\"Unimplemented method 'solve'\");\n" +
                        "    }\n\n" +
                        "}",
                packageName, imports, info.getName(), info.getCategory(), simpleClassName, returnType, returnType);
    }

    private String generateImports(String returnType) {
        Set<String> imports = new TreeSet<>();
        for (Map.Entry<String, String> entry : Constants.IMPORT_MAPPINGS.entrySet()) {
            if (returnType.matches(".*\\b" + entry.getKey() + "\\b.*")) {
                imports.add(entry.getValue());
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append(Constants.BASE_PROBLEM_IMPORT);
        builder.append(Constants.PROBLEM_IMPORT);
        for (String imp : imports) {
            builder.append(Constants.IMPORT).append(imp).append(";\n");
        }

        return builder.toString();
    }
}
