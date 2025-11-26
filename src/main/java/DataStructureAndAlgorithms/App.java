package DataStructureAndAlgorithms;

import DataStructureAndAlgorithms.core.ProblemManager;
import DataStructureAndAlgorithms.runner.ProblemRunner;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.InputService;

public class App {

    public static void main(String[] args) {
        try {
            ClassDiscoveryService discoveryService = new ClassDiscoveryService();
            FileSystemService fileSystemService = new FileSystemService();
            InputService inputService = new InputService();

            ProblemManager problemManager = new ProblemManager(discoveryService,
                    fileSystemService);

            ProblemRunner runner = new ProblemRunner(problemManager, inputService);
            runner.start();

        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
