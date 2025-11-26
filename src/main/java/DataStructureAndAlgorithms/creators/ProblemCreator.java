package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class ProblemCreator extends BaseClassCreator {

    public ProblemCreator(InputService inputService, FileSystemService fileSystemService,
            ClassDiscoveryService discoveryService, NamingUtils namingUtils) {
        super(inputService, fileSystemService, discoveryService, namingUtils);
    }

    public void createNewProblem() {
    }

    private String generateProblemTemplate(ProblemInfo info) {
        return null;
    }
}
