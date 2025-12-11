package DataStructureAndAlgorithms.domain.creators;

import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.exceptions.domain.CreationException;
import DataStructureAndAlgorithms.infrastructure.file.FileManager;
import DataStructureAndAlgorithms.utils.ApplicationConstants;
import DataStructureAndAlgorithms.utils.NameFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProblemGenerator {
    private final FileManager fileManager;

    public ProblemGenerator(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void createProblem(ProblemInfo problemInfo) {
        String filePath = fileManager.getProblemFilePath(problemInfo);
        if (fileManager.exists(filePath)) {
            throw new CreationException("Problem already exists at: " + filePath);
        }

        String className = NameFormatter.generateProblemSimpleClassName(problemInfo.getName());
        String packageName = ApplicationConstants.PROBLEM_PACKAGE + "." +
                NameFormatter.generateCategoryFolderName(problemInfo.getCategory()) + "."
                + problemInfo.getDifficulty();
        String content = generateProblemTemplate(problemInfo, className, packageName);

        try {
            fileManager.createDirectories(filePath);

            Files.writeString(Path.of(filePath), content);

            problemInfo.setClassName(packageName + "." + className);
            problemInfo.setFilePath(filePath);

        } catch (IOException e) {
            throw new CreationException("Failed to create problem file: " + e.getMessage(), e);
        }
    }

    private String generateProblemTemplate(ProblemInfo info, String className, String packageName) {
        String returnType = info.getReturnType();

        String imports = ApplicationConstants.BASE_PROBLEM_IMPORT +
                ApplicationConstants.PROBLEM_IMPORT +
                generateCommonImports(returnType);

        return String.format(
                """
                         package %s;

                         %s
                         @Problem(name = "%s", category = "%s", difficulty = "%s")
                         public class %s extends BaseProblem<%s> {
                        
                             @Override
                             public %s solve() {
                                 // TODO: Implement solution
                                 throw new UnsupportedOperationException("Unimplemented method 'solve'");
                             }
                         }
                        """,
                packageName,
                imports,
                info.getName(),
                info.getCategory(),
                info.getDifficulty(),
                className,
                returnType,
                returnType);
    }

    private String generateCommonImports(String returnType) {
        if (returnType.contains("List")) {
            return "import java.util.List;\n";
        } else if (returnType.contains("Map")) {
            return "import java.util.Map;\n";
        } else if (returnType.contains("Set")) {
            return "import java.util.Set;\n";
        }
        return "";
    }
}
