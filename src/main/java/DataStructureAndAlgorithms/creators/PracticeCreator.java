package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class PracticeCreator extends BaseClassCreator {
    public PracticeCreator(InputService inputService, FileSystemService fileSystemService,
            ClassDiscoveryService discoveryService, NamingUtils namingUtils) {
        super(inputService, fileSystemService, discoveryService, namingUtils);
    }

    public void createNewPractice() {
    }

    public void resetPractice(String practiceName) {
    }

    public void resetPracticesByCategory(String category) {
    }

    public void resetAllPractices() {
    }

    private String generatePracticeTemplate(PracticeInfo info) {
        return null;
    }
}
