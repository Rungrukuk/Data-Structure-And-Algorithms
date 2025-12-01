package DataStructureAndAlgorithms.domain.creators;

import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.exceptions.ProblemCreationException;
import DataStructureAndAlgorithms.infrastructure.file.FileSystemService_OLD;
import DataStructureAndAlgorithms.services.InfoDisplayService;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

public class ProblemCreator_OLD extends BaseCreator_OLD {

    public ProblemCreator_OLD(FileSystemService_OLD fileSystemService,
            InfoDisplayService infoDisplayService) {
        super(fileSystemService, infoDisplayService);
    }

    public void createProblem(ProblemInfo problemInfo) {
        String name = problemInfo.getName();
        String category = problemInfo.getCategory();

        if (infoDisplayService.problemExists(name, category)) {
            throw new ProblemCreationException("Problem already exists: " + problemInfo.getUniqueId());
        }

        String filePath = fileSystemService.getProblemFilePath(problemInfo);
        String className = NameFormatter.generateClassName(name, category, ApplicationConstants.PROBLEM_PACKAGE);
        String content = generateProblemTemplate(problemInfo);

        problemInfo.setFilePath(filePath);
        problemInfo.setClassName(className);

        try {
            executeAtomicCreate(
                    () -> infoDisplayService.addCreatedProblem(problemInfo),
                    () -> infoDisplayService.removeCreatedProblem(problemInfo),
                    filePath,
                    content,
                    problemInfo.getUniqueId());
        } catch (RuntimeException e) {
            throw new ProblemCreationException(e.getMessage());
        }
    }

    private String generateProblemTemplate(ProblemInfo info) {
        String simpleClassName = simpleName(info.getName());
        String packageName = buildPackage(ApplicationConstants.PROBLEM_PACKAGE, info.getCategory());
        String returnType = info.getReturnType();

        String imports = ApplicationConstants.BASE_PROBLEM_IMPORT +
                ApplicationConstants.PROBLEM_IMPORT +
                generateCommonImports(returnType);

        return String.format(
                "package %s;\n\n" +
                        "%s\n" +
                        "@Problem(name = \"%s\", category = \"%s\")\n" +
                        "public class %s extends BaseProblem<%s> {\n\n" +
                        "    @Override\n" +
                        "    public %s solve() {\n" +
                        "        // TODO: Implement solution\n" +
                        "        throw new UnsupportedOperationException(\"Unimplemented method 'solve'\");\n" +
                        "    }\n" +
                        "}",
                packageName,
                imports,
                info.getName(),
                info.getCategory(),
                simpleClassName,
                returnType,
                returnType);
    }
}