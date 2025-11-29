package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;
import DataStructureAndAlgorithms.utils.Constants;
import DataStructureAndAlgorithms.utils.NamingUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseClassCreator {

    protected final FileSystemService fileSystemService;
    protected final ProblemPracticeService problemPracticeService;

    protected BaseClassCreator(FileSystemService fileSystemService,
            ProblemPracticeService problemPracticeService) {
        this.fileSystemService = fileSystemService;
        this.problemPracticeService = problemPracticeService;
    }

    protected void executeAtomicCreate(
            Runnable registryAdder,
            Runnable registryRemover,
            String filePath,
            String content,
            String uniqueId) {

        try {
            registryAdder.run();
            try {
                fileSystemService.createFile(filePath, content);
            } catch (Exception e) {
                registryRemover.run();
                throw new RuntimeException("Failed to create file for: " + uniqueId, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create: " + uniqueId, e);
        }
    }

    // ------------------------- IMPORT HELPERS -------------------------

    protected String generateCommonImports(String returnType) {
        Set<String> imports = new TreeSet<>();

        for (Map.Entry<String, String> entry : Constants.IMPORT_MAPPINGS.entrySet()) {
            if (returnType.matches(".*\\b" + entry.getKey() + "\\b.*")) {
                imports.add(entry.getValue());
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String imp : imports) {
            builder.append(Constants.IMPORT)
                    .append(imp)
                    .append(";\n");
        }

        return builder.toString();
    }

    // ---------------------- PACKAGE + CLASS NAME HELPERS ----------------------

    protected String buildPackage(String root, String category) {
        return root + Constants.DOT_SEPERATOR +
                NamingUtils.generateCategoryFolderName(category);
    }

    protected String simpleName(String problemName) {
        return NamingUtils.generateSimpleClassName(problemName);
    }
}
