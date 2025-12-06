package DataStructureAndAlgorithms.app;

import DataStructureAndAlgorithms.domain.creators.PracticeGenerator;
import DataStructureAndAlgorithms.domain.creators.ProblemGenerator;
import DataStructureAndAlgorithms.domain.flows.PracticeFlowHandler;
import DataStructureAndAlgorithms.domain.flows.ProblemFlowHandler;
import DataStructureAndAlgorithms.domain.practices.PracticeExecutor;
import DataStructureAndAlgorithms.domain.practices.PracticeOrchestrator;
import DataStructureAndAlgorithms.domain.practices.PracticeRepository;
import DataStructureAndAlgorithms.domain.practices.PracticeRepositoryImpl;
import DataStructureAndAlgorithms.domain.problems.ProblemExecutor;
import DataStructureAndAlgorithms.domain.problems.ProblemOrchestrator;
import DataStructureAndAlgorithms.domain.problems.ProblemRepository;
import DataStructureAndAlgorithms.domain.problems.ProblemRepositoryImpl;
import DataStructureAndAlgorithms.infrastructure.discovery.ClassScanner;
import DataStructureAndAlgorithms.infrastructure.file.FileManager;
import DataStructureAndAlgorithms.infrastructure.input.InputHandler;
import DataStructureAndAlgorithms.infrastructure.runner.CodeRunner;
import DataStructureAndAlgorithms.ui.UIFactory;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.MenuNavigator;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.prompts.Prompter;

public class DependencyContainer {

    public static ApplicationController createApplication() {
        // ========================= INFRASTRUCTURE LAYER =========================
        InputHandler inputHandler = UIFactory.createInputHandler();
        FileManager fileManager = new FileManager();
        ClassScanner classScanner = new ClassScanner();
        CodeRunner codeRunner = new CodeRunner();

        // ========================= UI LAYER =========================
        UIManager uiManager = UIFactory.createUIManager(inputHandler);
        Prompter prompter = UIFactory.createPrompter(inputHandler, uiManager);
        SelectionHandler selectionHandler = UIFactory.createSelectionHandler(prompter, uiManager);
        MenuNavigator menuNavigator = UIFactory.createMenuNavigator(selectionHandler);

        // ================ DOMAIN LAYER - REPOSITORIES ================
        ProblemRepository problemRepository = new ProblemRepositoryImpl(classScanner);
        PracticeRepository practiceRepository = new PracticeRepositoryImpl(classScanner, problemRepository);

        // ================ DOMAIN LAYER - SELECTORS & EXECUTORS ================
        ProblemExecutor problemExecutor = new ProblemExecutor(codeRunner);
        PracticeExecutor practiceExecutor = new PracticeExecutor(codeRunner);

        // ========================= DOMAIN LAYER - GENERATORS =========================
        ProblemGenerator problemGenerator = new ProblemGenerator(fileManager);
        PracticeGenerator practiceGenerator = new PracticeGenerator(fileManager);

        // ================ DOMAIN LAYER - ORCHESTRATORS ================
        ProblemOrchestrator problemOrchestrator = new ProblemOrchestrator(
                problemRepository,
                problemExecutor,
                problemGenerator);

        PracticeOrchestrator practiceOrchestrator = new PracticeOrchestrator(
                practiceRepository,
                practiceExecutor,
                practiceGenerator);

        ProblemFlowHandler problemFlowHandler = new ProblemFlowHandler(problemOrchestrator, uiManager, selectionHandler,
                prompter);
        PracticeFlowHandler practiceFlowHandler = new PracticeFlowHandler(practiceOrchestrator, uiManager,
                selectionHandler, prompter);

        // ========================= APPLICATION CONTROLLER =========================
        return new ApplicationController(
                uiManager,
                menuNavigator, problemFlowHandler, practiceFlowHandler);
    }

/*
    public static ApplicationController createTestApplication() {
        return createApplication();
    }
*/
}
