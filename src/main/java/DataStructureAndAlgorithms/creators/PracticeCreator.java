package DataStructureAndAlgorithms.creators;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import DataStructureAndAlgorithms.exceptions.PracticeCreationException;
import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;
import DataStructureAndAlgorithms.utils.Constants;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class PracticeCreator extends BaseClassCreator {

    public PracticeCreator(FileSystemService fileSystemService,
            ProblemPracticeService practiceService) {
        super(fileSystemService, practiceService);
    }

    public Optional<ProblemInfo> getProblemIfExists(String unqueId) {
        return problemPracticeService.findProblemByUniqueId(unqueId);
    }

    public void createPractice(PracticeInfo practiceInfo) {
        ProblemInfo problemInfo = practiceInfo.getProblemInfo();

        if (problemPracticeService.practiceExists(problemInfo.getName(), problemInfo.getCategory())) {
            throw new PracticeCreationException("Practice already exists: " + practiceInfo.getUniqueId());
        }

        String filePath = fileSystemService.getPracticeFilePath(practiceInfo);
        String className = NamingUtils.generateClassName(problemInfo.getName(), problemInfo.getCategory(),
                Constants.PRACTICE_PACKAGE);
        String content = generatePracticeTemplate(problemInfo);

        practiceInfo.setFilePath(filePath);
        practiceInfo.setPracticeClassName(className);

        try {
            executeAtomicCreate(
                    () -> problemPracticeService.addCreatedPractice(practiceInfo),
                    () -> problemPracticeService.removeCreatedPractice(practiceInfo),
                    filePath,
                    content,
                    practiceInfo.getUniqueId());
        } catch (RuntimeException e) {
            throw new PracticeCreationException(e.getMessage());
        }
    }

    private String generatePracticeTemplate(ProblemInfo info) {
        String simpleClassName = simpleName(info.getName()) + Constants.PRACTICE_CLASS_SUFFIX;
        String packageName = buildPackage(Constants.PRACTICE_PACKAGE, info.getCategory());

        String returnType = info.getReturnType();
        String problemClass = info.getClassName();
        String problemSimpleClassName = NamingUtils.generateSimpleClassName(info.getName());

        String imports = Constants.BASE_PRACTICE_IMPORT +
                Constants.PRACTICE_IMPORT +
                Constants.IMPORT + problemClass + ";\n" +
                generateCommonImports(returnType);

        return String.format(
                "package %s;\n\n" +
                        "%s\n" +
                        "@Practice(problemName = \"%s\", category = \"%s\")\n" +
                        "public class %s extends BasePractice<%s, %s> {\n\n" +
                        "    public %s(%s problem) {\n" +
                        "        super(problem);\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public %s practice() {\n" +
                        "        // TODO: Implement practice logic\n" +
                        "        throw new UnsupportedOperationException(\"Unimplemented method 'practice'\");\n" +
                        "    }\n" +
                        "}",
                packageName,
                imports,
                info.getName(),
                info.getCategory(),
                simpleClassName,
                returnType,
                problemSimpleClassName,
                simpleClassName,
                problemSimpleClassName,
                returnType);
    }

    public List<ProblemInfo> findProblemInfoByName(String problemName) {
        return problemPracticeService.findProblemsByName(problemName);
    }

    public Map<String, List<ProblemInfo>> getProblemInfoByCategory() {
        return problemPracticeService.groupProblemsByCategory();
    }

    public List<ProblemInfo> getProblemInfoList() {
        return problemPracticeService.getProblemInfoList();
    }

}
