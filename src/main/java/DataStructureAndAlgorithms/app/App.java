package DataStructureAndAlgorithms.app;

import DataStructureAndAlgorithms.runner.ProblemRunner;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
// import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;

public class App {

    public static void main(String[] args) {
        try {
            ClassDiscoveryService discoveryService = new ClassDiscoveryService();
            // FileSystemService fileSystemService = new FileSystemService();
            InputService inputService = new InputService();

            ProblemPracticeService problemManager = new ProblemPracticeService(discoveryService);
            ProblemRunner problemRunner = new ProblemRunner(problemManager);
            ApplicationManager applicationManager = new ApplicationManager(problemRunner, inputService);
            applicationManager.start();

        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            // e.printStackTrace();
        }
    }
}
