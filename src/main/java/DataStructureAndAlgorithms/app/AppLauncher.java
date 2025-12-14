package DataStructureAndAlgorithms.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLauncher {
    private static final Logger logger = LogManager.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting Data Structures & Algorithms Application...");

            ApplicationController appController = DependencyContainer.createApplication();

            appController.start();

            logger.info("Application terminated successfully.");

        } catch (Exception e) {
            logger.error("Fatal error in application: {}", e.getMessage(), e);
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
