package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class BaseClassCreator {
    protected final InputService inputService;
    protected final FileSystemService fileSystemService;
    protected final ClassDiscoveryService discoveryService;
    protected final NamingUtils namingUtils;

    public BaseClassCreator(InputService inputService, FileSystemService fileSystemService,
            ClassDiscoveryService discoveryService, NamingUtils namingUtils) {
        this.inputService = inputService;
        this.fileSystemService = fileSystemService;
        this.discoveryService = discoveryService;
        this.namingUtils = namingUtils;
    }

    protected String generateClassContent(ProblemInfo problemInfo, String template) {
        return null;
    }

    protected void showSuccessMessage(String className, String filePath) {
    }

    protected void showErrorMessage(String message) {
    }
}
