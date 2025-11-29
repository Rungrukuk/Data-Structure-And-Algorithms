package DataStructureAndAlgorithms.creators;


import DataStructureAndAlgorithms.exceptions.ProblemCreationException;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;
import DataStructureAndAlgorithms.utils.Constants;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class ProblemCreator extends BaseClassCreator {

    public ProblemCreator(FileSystemService fileSystemService,
            ProblemPracticeService problemPracticeService) {
        super(fileSystemService, problemPracticeService);
    }



    public void createProblem(ProblemInfo problemInfo) {
        String name = problemInfo.getName();
        String category = problemInfo.getCategory();

        if (problemPracticeService.problemExists(name, category)) {
            throw new ProblemCreationException("Problem already exists: " + problemInfo.getUniqueId());
        }

        String filePath = fileSystemService.getProblemFilePath(problemInfo);
        String className = NamingUtils.generateClassName(name, category, Constants.PROBLEM_PACKAGE);
        String content = generateProblemTemplate(problemInfo);

        problemInfo.setFilePath(filePath);
        problemInfo.setClassName(className);

        try {
            executeAtomicCreate(
                    () -> problemPracticeService.addCreatedProblem(problemInfo),
                    () -> problemPracticeService.removeCreatedProblem(problemInfo),
                    filePath,
                    content,
                    problemInfo.getUniqueId());
        } catch (RuntimeException e) {
            throw new ProblemCreationException(e.getMessage());
        }
    }

    private String generateProblemTemplate(ProblemInfo info) {
        String simpleClassName = simpleName(info.getName());
        String packageName = buildPackage(Constants.PROBLEM_PACKAGE, info.getCategory());
        String returnType = info.getReturnType();

        String imports = Constants.BASE_PROBLEM_IMPORT +
                Constants.PROBLEM_IMPORT +
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
