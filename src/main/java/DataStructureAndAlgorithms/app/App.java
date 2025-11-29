package DataStructureAndAlgorithms.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DataStructureAndAlgorithms.creators.ProblemCreator;
import DataStructureAndAlgorithms.runner.ProblemRunner;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.InputService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        try {
            ClassDiscoveryService discoveryService = new ClassDiscoveryService();
            FileSystemService fileSystemService = new FileSystemService();
            InputService inputService = new InputService();

            ProblemPracticeService problemManager = new ProblemPracticeService(discoveryService);
            ProblemRunner problemRunner = new ProblemRunner(problemManager);
            ProblemCreator problemCreator = new ProblemCreator(fileSystemService, problemManager);
            ApplicationManager applicationManager = new ApplicationManager(problemRunner, inputService, problemCreator);
            applicationManager.start();

        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            log.error(e);
        }
    }
}
