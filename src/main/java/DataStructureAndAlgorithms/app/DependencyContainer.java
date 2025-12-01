package DataStructureAndAlgorithms.app;

import DataStructureAndAlgorithms.domain.creators.PracticeGenerator;
import DataStructureAndAlgorithms.domain.creators.ProblemGenerator;
import DataStructureAndAlgorithms.domain.practices.*;
import DataStructureAndAlgorithms.domain.problems.*;
import DataStructureAndAlgorithms.infrastructure.discovery.ClassScanner;
import DataStructureAndAlgorithms.infrastructure.file.FileManager;
import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.infrastructure.runner.CodeRunner;
import DataStructureAndAlgorithms.ui.UIFactory;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.MenuNavigator;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.prompts.ProblemPrompter;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

public class DependencyContainer {

    /**
     * Create the complete application with all dependencies wired together
     */
    public static ApplicationController createApplication() {
        // ========================= INFRASTRUCTURE LAYER =========================
        InputHandler inputHandler = UIFactory.createInputHandler();
        FileManager fileManager = new FileManager(ApplicationConstants.BASE_PACKAGE);
        ClassScanner classScanner = new ClassScanner();
        CodeRunner codeRunner = new CodeRunner();

        // ========================= UI LAYER =========================
        UIManager uiManager = UIFactory.createUIManager();
        MenuNavigator menuNavigator = UIFactory.createMenuNavigator(uiManager);
        SelectionHandler selectionHandler = UIFactory.createSelectionHandler(uiManager);
        ProblemPrompter problemPrompter = UIFactory.createProblemPrompter(inputHandler, uiManager);

        // ========================= DOMAIN LAYER - REPOSITORIES
        // =========================
        ProblemRepository problemRepository = new ProblemRepositoryImpl(classScanner);
        PracticeRepository practiceRepository = new PracticeRepositoryImpl(classScanner, problemRepository);

        // ========================= DOMAIN LAYER - SELECTORS & EXECUTORS
        // =========================
        ProblemSelector problemSelector = new ProblemSelector(problemRepository);
        ProblemExecutor problemExecutor = new ProblemExecutor(codeRunner);
        PracticeSelector practiceSelector = new PracticeSelector(practiceRepository);
        PracticeExecutor practiceExecutor = new PracticeExecutor(codeRunner);

        // ========================= DOMAIN LAYER - GENERATORS =========================
        ProblemGenerator problemGenerator = new ProblemGenerator(fileManager);
        PracticeGenerator practiceGenerator = new PracticeGenerator(fileManager);

        // ========================= DOMAIN LAYER - ORCHESTRATORS
        // =========================
        ProblemOrchestrator problemOrchestrator = new ProblemOrchestrator(
                problemRepository,
                problemSelector,
                problemExecutor,
                problemGenerator);

        PracticeOrchestrator practiceOrchestrator = new PracticeOrchestrator(
                practiceRepository,
                practiceSelector,
                practiceExecutor,
                practiceGenerator,
                problemRepository);

        // ========================= APPLICATION CONTROLLER =========================
        return new ApplicationController(
                uiManager,
                menuNavigator,
                selectionHandler,
                problemPrompter,
                problemOrchestrator,
                practiceOrchestrator);
    }

    /**
     * Create a simplified application for testing
     */
    public static ApplicationController createTestApplication() {
        // Use the same method for now - we can create mocks/stubs later if needed
        return createApplication();
    }
}