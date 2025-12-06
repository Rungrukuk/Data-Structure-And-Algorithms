package DataStructureAndAlgorithms.domain.creators;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.exceptions.CreationException;
import DataStructureAndAlgorithms.infrastructure.file.FileManager;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PracticeGenerator {
    private final FileManager fileManager;

    public PracticeGenerator(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void createPractice(PracticeInfo practiceInfo) {
        ProblemInfo problemInfo = practiceInfo.getProblemInfo();

        String filePath = fileManager.getPracticeFilePath(practiceInfo);
        if (fileManager.exists(filePath)) {
            throw new CreationException("Practice already exists at: " + filePath);
        }

        String className = NameFormatter.generatePracticeSimpleClassName(problemInfo.getName());
        String packageName = ApplicationConstants.PRACTICE_PACKAGE + "." +
                NameFormatter.generateCategoryFolderName(problemInfo.getCategory());
        String content = generatePracticeTemplate(problemInfo, className, packageName);

        try {
            fileManager.createDirectories(filePath);

            Files.writeString(Path.of(filePath), content);

            practiceInfo.setPracticeClassName(packageName + "." + className);
            practiceInfo.setFilePath(filePath);

        } catch (IOException e) {
            throw new CreationException("Failed to create practice: " + e.getMessage(), e);
        }
    }

    public void resetPractice(PracticeInfo practiceInfo) {
        ProblemInfo problemInfo = practiceInfo.getProblemInfo();
        String content = generatePracticeTemplate(problemInfo,
                NameFormatter.generatePracticeSimpleClassName(problemInfo.getName()),
                ApplicationConstants.PRACTICE_PACKAGE + "." +
                        NameFormatter.generateCategoryFolderName(problemInfo.getCategory()));
        try {
            Files.writeString(Path.of(practiceInfo.getFilePath()), content);

        } catch (IOException e) {
            throw new CreationException("Failed to create practice: " + e.getMessage(), e);
        }
    }

    private String generatePracticeTemplate(ProblemInfo info, String className, String packageName) {
        String returnType = info.getReturnType();
        String problemSimpleClassName = NameFormatter.generateProblemSimpleClassName(info.getName());
        String problemClass = info.getClassName();

        String imports = ApplicationConstants.BASE_PRACTICE_IMPORT +
                ApplicationConstants.PRACTICE_IMPORT +
                "import " + problemClass + ";\n" +
                generateCommonImports(returnType);

        return String.format(
                """
                        package %s;
                        
                        %s
                        @Practice(problemName = "%s", category = "%s")
                        public class %s extends BasePractice<%s, %s> {
                        
                            public %s(%s problem) {
                                super(problem);
                            }
                        
                            @Override
                            public %s practice() {
                                // TODO: Implement practice logic
                                throw new UnsupportedOperationException("Unimplemented method 'practice'");
                            }
                        }
                        """,
                packageName,
                imports,
                info.getName(),
                info.getCategory(),
                className,
                returnType,
                problemSimpleClassName,
                className,
                problemSimpleClassName,
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
