package DataStructureAndAlgorithms.domain.creators;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.exceptions.PracticeCreationException;
import DataStructureAndAlgorithms.infrastructure.file.FileSystemService_OLD;
import DataStructureAndAlgorithms.services.InfoDisplayService;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

public class PracticeCreator_OLD extends BaseCreator_OLD {

    public PracticeCreator_OLD(FileSystemService_OLD fileSystemService,
            InfoDisplayService infoDisplayService) {
        super(fileSystemService, infoDisplayService);
    }

    public Optional<ProblemInfo> getProblemIfExists(String uniqueId) {
        return infoDisplayService.findProblemByUniqueId(uniqueId);
    }

    public void createPractice(PracticeInfo practiceInfo) {
        ProblemInfo problemInfo = practiceInfo.getProblemInfo();

        if (infoDisplayService.practiceExists(problemInfo.getName(), problemInfo.getCategory())) {
            throw new PracticeCreationException("Practice already exists: " + practiceInfo.getUniqueId());
        }

        String filePath = fileSystemService.getPracticeFilePath(practiceInfo);
        String className = NameFormatter.generateClassName(problemInfo.getName(), problemInfo.getCategory(),
                ApplicationConstants.PRACTICE_PACKAGE);
        String content = generatePracticeTemplate(problemInfo);

        practiceInfo.setFilePath(filePath);
        practiceInfo.setPracticeClassName(className);

        try {
            executeAtomicCreate(
                    () -> infoDisplayService.addCreatedPractice(practiceInfo),
                    () -> infoDisplayService.removeCreatedPractice(practiceInfo),
                    filePath,
                    content,
                    practiceInfo.getUniqueId());
        } catch (RuntimeException e) {
            throw new PracticeCreationException(e.getMessage());
        }
    }

    private String generatePracticeTemplate(ProblemInfo info) {
        String simpleClassName = simpleName(info.getName()) + ApplicationConstants.PRACTICE_CLASS_SUFFIX;
        String packageName = buildPackage(ApplicationConstants.PRACTICE_PACKAGE, info.getCategory());

        String returnType = info.getReturnType();
        String problemClass = info.getClassName();
        String problemSimpleClassName = NameFormatter.generateSimpleClassName(info.getName());

        String imports = ApplicationConstants.BASE_PRACTICE_IMPORT +
                ApplicationConstants.PRACTICE_IMPORT +
                ApplicationConstants.IMPORT + problemClass + ";\n" +
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
        return infoDisplayService.findProblemInfosByName(problemName);
    }

    public Map<String, List<ProblemInfo>> getProblemInfoByCategory() {
        return infoDisplayService.getProblemsByCategory();
    }

    public List<ProblemInfo> getProblemInfoList() {
        return infoDisplayService.getAllProblemInfos();
    }
}