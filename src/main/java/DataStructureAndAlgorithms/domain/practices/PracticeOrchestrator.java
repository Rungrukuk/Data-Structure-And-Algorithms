package DataStructureAndAlgorithms.domain.practices;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.creators.PracticeGenerator;
import DataStructureAndAlgorithms.domain.problems.ProblemRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PracticeOrchestrator {
    private final PracticeRepository practiceRepository;
    private final PracticeSelector practiceSelector;
    private final PracticeExecutor practiceExecutor;
    private final PracticeGenerator practiceGenerator;
    private final ProblemRepository problemRepository;

    public PracticeOrchestrator(PracticeRepository practiceRepository,
            PracticeSelector practiceSelector,
            PracticeExecutor practiceExecutor,
            PracticeGenerator practiceGenerator,
            ProblemRepository problemRepository) {
        this.practiceRepository = practiceRepository;
        this.practiceSelector = practiceSelector;
        this.practiceExecutor = practiceExecutor;
        this.practiceGenerator = practiceGenerator;
        this.problemRepository = problemRepository;
    }

    // ========================= LISTING OPERATIONS =========================

    public List<String> listAllPractices() {
        return practiceSelector.getAllPracticeDisplays();
    }

    public Map<String, List<String>> listPracticesByCategory() {
        return practiceSelector.getPracticesByCategoryDisplays();
    }

    public List<String> listPracticeVariants(String name) {
        return practiceSelector.findPracticeDisplaysByName(name);
    }

    // ========================= SELECTION OPERATIONS =========================

    public Optional<ProblemInfo> selectProblemForPractice() {
        List<ProblemInfo> allProblems = problemRepository.findAll();
        if (allProblems.isEmpty()) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    // ========================= EXECUTION OPERATIONS =========================

    public Optional<String> runPractice(PracticeInfo practiceInfo) {
        return practiceExecutor.runPractice(practiceInfo)
                .map(practiceExecutor::formatResult);
    }

    public Optional<String> runPracticeByDisplay(String displayString) {
        return practiceSelector.findPracticeByDisplay(displayString)
                .flatMap(this::runPractice);
    }

    // ========================= CREATION OPERATIONS =========================

    public void createPracticeForProblem(ProblemInfo problemInfo) {
        PracticeInfo practiceInfo = new PracticeInfo(problemInfo, null, null);

        try {
            practiceGenerator.createPractice(practiceInfo);

            practiceRepository.add(practiceInfo);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create practice: " + e.getMessage(), e);
        }
    }

    // ========================= UTILITY METHODS =========================

    public boolean practiceExists(String name, String category) {
        return practiceRepository.exists(name, category);
    }

    public List<String> getAllCategories() {
        return practiceSelector.getAllCategories();
    }

    public List<ProblemInfo> getAllProblems() {
        return problemRepository.findAll();
    }

    public void refresh() {
        practiceRepository.refresh();
    }
}