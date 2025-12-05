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
    private final PracticeExecutor practiceExecutor;
    private final PracticeGenerator practiceGenerator;
    private final ProblemRepository problemRepository;

    public PracticeOrchestrator(PracticeRepository practiceRepository,
            PracticeExecutor practiceExecutor,
            PracticeGenerator practiceGenerator,
            ProblemRepository problemRepository) {
        this.practiceRepository = practiceRepository;
        this.practiceExecutor = practiceExecutor;
        this.practiceGenerator = practiceGenerator;
        this.problemRepository = problemRepository;
    }

    // ========================= LISTING OPERATIONS =========================

    public List<PracticeInfo> listAllPractices() {
        return practiceRepository.findAll();
    }

    public Map<String, List<PracticeInfo>> listPracticesByCategory() {
        return practiceRepository.findAllGroupedByCategory();
    }

    public List<PracticeInfo> listPracticeVariants(String name) {
        return practiceRepository.findByName(name);
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

    // ========================= CREATION OPERATIONS =========================

    public void createPracticeForProblem(ProblemInfo problemInfo) {
        PracticeInfo practiceInfo = new PracticeInfo(problemInfo, null, null);

        try {
            practiceGenerator.createPractice(practiceInfo);

            practiceRepository.add(practiceInfo);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // ========================= UTILITY METHODS =========================

    public boolean practiceExists(String name, String category) {
        return practiceRepository.exists(name, category);
    }

    public List<String> getAllCategories() {
        return practiceRepository.findAllGroupedByCategory().keySet().stream()
                .sorted()
                .toList();
    }

    public List<ProblemInfo> getAllProblems() {
        return problemRepository.findAll();
    }

    public void refresh() {
        practiceRepository.refresh();
    }
}