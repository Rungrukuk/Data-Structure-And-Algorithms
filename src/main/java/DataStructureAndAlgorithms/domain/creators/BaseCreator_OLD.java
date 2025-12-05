package DataStructureAndAlgorithms.domain.creators;

import DataStructureAndAlgorithms.infrastructure.file.FileSystemService_OLD;
import DataStructureAndAlgorithms.services.InfoDisplayService;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseCreator_OLD {
    protected final FileSystemService_OLD fileSystemService;
    protected final InfoDisplayService infoDisplayService;

    public BaseCreator_OLD(FileSystemService_OLD fileSystemService,
            InfoDisplayService infoDisplayService) {
        this.fileSystemService = fileSystemService;
        this.infoDisplayService = infoDisplayService;
    }

    protected String simpleName(String name) {
        return NameFormatter.generateSimpleClassName(name);
    }

    protected String buildPackage(String basePackage, String category) {
        return basePackage + "." + NameFormatter.generateCategoryFolderName(category);
    }

    protected String generateCommonImports(String returnType) {
        if (returnType.contains("List")) {
            return "import java.util.List;\n";
        } else if (returnType.contains("Map")) {
            return "import java.util.Map;\n";
        } else if (returnType.contains("Set")) {
            return "import java.util.Set;\n";
        }
        return "";
    }

    protected void executeAtomicCreate(
            Runnable onSuccess,
            Runnable onRollback,
            String filePath,
            String content,
            String identifier) {

        boolean created = false;
        try {
            // Create the file
            Path path = Path.of(filePath);
            Files.createDirectories(path.getParent());
            Files.writeString(path, content);
            created = true;

            // Notify service
            onSuccess.run();

        } catch (IOException e) {
            if (created) {
                try {
                    Files.deleteIfExists(Path.of(filePath));
                } catch (IOException deleteEx) {
                    // Log or handle, but continue with rollback
                }

                onRollback.run();
            }
            throw new RuntimeException("Failed to create " + identifier + ": " + e.getMessage(), e);
        }
    }
}